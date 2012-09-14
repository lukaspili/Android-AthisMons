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
import com.siu.android.athismons.dao.model.Card;
import com.siu.android.athismons.dao.model.CardDao;
import com.siu.android.athismons.dao.model.Directory;
import com.siu.android.athismons.dao.model.DirectoryDao;
import com.siu.android.athismons.fragment.DirectoryFragment;
import com.siu.android.athismons.sax.SaxParser;

import java.util.Iterator;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class DirectoryLoadTask extends AsyncTask<Void, List<Directory>, List<Directory>> {

    private DirectoryFragment fragment;
    private SharedPreferences preferences;

    public DirectoryLoadTask(DirectoryFragment fragment) {
        this.fragment = fragment;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(Application.getContext());
    }

    @Override
    protected List<Directory> doInBackground(Void... voids) {
        Log.d(getClass().getName(), "DirectoryLoadTask");

        final DirectoryDao directoryDao = DatabaseHelper.getInstance().getDaoSession().getDirectoryDao();
        String existMd5 = preferences.getString(AppConstants.PREF_DIRECTORY_MD5_KEY, null);

        if (null != existMd5) {
            publishProgress(directoryDao.loadAll());
        }

        if (!NetworkUtils.isOnline() || isCancelled()) {
            return null;
        }

        String content = new SimpleHttpRequest.Builder()
                .url(AppConstants.DIRECTORY_URL)
                .requestMethod(SimpleHttpRequest.Method.GET)
                .build().requestAsString();

        if (null == content || isCancelled()) {
            return null;
        }

        final String currentMd5 = CryptoUtils.md5Hex(content);
        if (null != existMd5 && existMd5.equals(currentMd5)) {
            Log.d(getClass().getName(), "Same md5, cancel");
            return null;
        }

        final List<Directory> Directoryes = new SaxParser().parseDirectory(content);
        if (null == Directoryes || isCancelled()) {
            return null;
        }

        final CardDao cardDao = DatabaseHelper.getInstance().getDaoSession().getCardDao();
        DatabaseHelper.getInstance().getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                preferences.edit().putString(AppConstants.PREF_DIRECTORY_MD5_KEY, currentMd5).commit();

                cardDao.deleteAll();
                directoryDao.deleteAll();

                for (Iterator<Directory> it = Directoryes.iterator(); it.hasNext(); ) {
                    Directory d = it.next();
                    directoryDao.insert(d);

                    for (Iterator<Card> it2 = d.getTransientCards().iterator(); it2.hasNext(); ) {
                        Card c = it2.next();
                        c.setDirectoryId(d.getId());
                        cardDao.insert(c);
                    }
                }
            }
        });

        return Directoryes;
    }

    @Override
    protected void onProgressUpdate(List<Directory>... res) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onLoadTaskProgress(res[0]);
    }

    @Override
    protected void onPostExecute(List<Directory> res) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onLoadTaskFinished(res);
    }
}
