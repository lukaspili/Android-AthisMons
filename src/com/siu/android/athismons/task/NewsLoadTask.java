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
import com.siu.android.athismons.dao.model.News;
import com.siu.android.athismons.dao.model.NewsDao;
import com.siu.android.athismons.fragment.NewsFragment;
import com.siu.android.athismons.sax.SaxParser;

import java.util.Iterator;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class NewsLoadTask extends AsyncTask<Void, List<News>, List<News>> {

    private NewsFragment fragment;
    private SharedPreferences preferences;

    public NewsLoadTask(NewsFragment fragment) {
        this.fragment = fragment;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(Application.getContext());
    }

    @Override
    protected List<News> doInBackground(Void... voids) {
        Log.d(getClass().getName(), "NewsLoadTask");

        final NewsDao dao = DatabaseHelper.getInstance().getDaoSession().getNewsDao();
        String existMd5 = preferences.getString(AppConstants.PREF_NEWS_MD5_KEY, null);

        if (null != existMd5) {
            publishProgress(dao.loadAll());
        }

        if (!NetworkUtils.isOnline() || isCancelled()) {
            return null;
        }

        String content = new SimpleHttpRequest.Builder()
                .url(AppConstants.NEWS_URL)
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

        final List<News> newses = new SaxParser().parseNews(content);
        if (null == newses || isCancelled()) {
            return null;
        }

        DatabaseHelper.getInstance().getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                preferences.edit().putString(AppConstants.PREF_NEWS_MD5_KEY, currentMd5).commit();

                dao.deleteAll();
                for (Iterator<News> it = newses.iterator(); it.hasNext(); ) {
                    dao.insert(it.next());
                }
            }
        });

        return newses;
    }

    @Override
    protected void onProgressUpdate(List<News>... newses) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onNewsLoadTaskProgress(newses[0]);
    }

    @Override
    protected void onPostExecute(List<News> newses) {
        if (null == fragment.getActivity()) {
            return;
        }

        fragment.onNewsLoadTaskFinished(newses);
    }
}
