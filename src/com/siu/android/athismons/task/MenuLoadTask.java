package com.siu.android.athismons.task;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import com.siu.android.andutils.http.SimpleHttpRequest;
import com.siu.android.andutils.util.CryptoUtils;
import com.siu.android.andutils.util.NetworkUtils;
import com.siu.android.athismons.AppConstants;
import com.siu.android.athismons.Application;
import com.siu.android.athismons.dao.DatabaseHelper;
import com.siu.android.athismons.dao.model.Agenda;
import com.siu.android.athismons.dao.model.Menu;
import com.siu.android.athismons.dao.model.MenuDao;
import com.siu.android.athismons.fragment.AgendaFragment;
import com.siu.android.athismons.fragment.MenuFragment;
import com.siu.android.athismons.sax.SaxParser;

import java.util.Iterator;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class MenuLoadTask extends AsyncTask<Void, List<Menu>, List<Menu>> {

    private MenuFragment fragment;
    private SharedPreferences preferences;

    public MenuLoadTask(MenuFragment fragment) {
        this.fragment = fragment;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(Application.getContext());
    }

    @Override
    protected List<Menu> doInBackground(Void... voids) {
        Log.d(getClass().getName(), "MenuLoadTask");

        final MenuDao dao = DatabaseHelper.getInstance().getDaoSession().getMenuDao();
        String existMd5 = preferences.getString(AppConstants.PREF_MENU_MD5_KEY, null);

        if (null != existMd5) {
            publishProgress(dao.loadAll());
        }

        if (!NetworkUtils.isOnline() || isCancelled()) {
            return null;
        }

        String content = new SimpleHttpRequest.Builder()
                .url(AppConstants.MENU_URL)
                .requestMethod(SimpleHttpRequest.Method.GET)
                .responseCharset("ISO-8859-1")
                .build().requestAsString();

        if (null == content || isCancelled()) {
            return null;
        }

        final String currentMd5 = CryptoUtils.md5Hex(content);
        if (null != existMd5 && existMd5.equals(currentMd5)) {
            Log.d(getClass().getName(), "Same md5, cancel");
            return null;
        }

        final List<Menu> list = new SaxParser().parseMenu(content);
        if (null == list || isCancelled()) {
            return null;
        }

        DatabaseHelper.getInstance().getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                preferences.edit().putString(AppConstants.PREF_MENU_MD5_KEY, currentMd5).commit();

                dao.deleteAll();
                for (Iterator<Menu> it = list.iterator(); it.hasNext(); ) {
                    dao.insert(it.next());
                }
            }
        });

        return list;
    }

    @Override
    protected void onProgressUpdate(List<Menu>... loaded) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onLoadTaskProgress(loaded[0]);
    }

    @Override
    protected void onPostExecute(List<Menu> loaded) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onLoadTaskFinished(loaded);
    }
}
