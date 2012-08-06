package com.siu.android.athismons.task;

import android.os.AsyncTask;
import android.util.Log;
import ch.boye.httpclientandroidlib.HttpResponse;
import com.siu.android.andutils.util.HttpUtils;
import com.siu.android.andutils.util.NetworkUtils;
import com.siu.android.athismons.AppConstants;
import com.siu.android.athismons.dao.DatabaseHelper;
import com.siu.android.athismons.dao.model.News;
import com.siu.android.athismons.dao.model.NewsDao;
import com.siu.android.athismons.fragment.NewsFragment;
import com.siu.android.athismons.sax.SaxParser;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class NewsLoadTask extends AsyncTask<Void, List<News>, List<News>> {

    private NewsFragment fragment;

    public NewsLoadTask(NewsFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected List<News> doInBackground(Void... voids) {
        if (!NetworkUtils.isOnline() || isCancelled()) {
            return null;
        }

        HttpResponse response = HttpUtils.request(AppConstants.NEWS_URL, HttpUtils.HttpMethod.GET);
        if (null == response || isCancelled()) {
            return null;
        }

        final List<News> newses;
        InputStream is = null;
        try {
            is = response.getEntity().getContent();
            newses = new SaxParser().parseNews(is);
        } catch (Exception e) {
            Log.e(getClass().getName(), "Error downloading or parsing newses", e);
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }

        if (null == newses || isCancelled()) {
            return null;
        }

        final NewsDao dao = DatabaseHelper.getInstance().getDaoSession().getNewsDao();
        DatabaseHelper.getInstance().getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
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
        if (null == fragment) {
            return;
        }

        fragment.onNewsLoadTaskProgress(newses[0]);
    }

    @Override
    protected void onPostExecute(List<News> newses) {
        if (null == fragment) {
            return;
        }

        fragment.onNewsLoadTaskFinished(newses);
    }

    public void setFragment(NewsFragment fragment) {
        this.fragment = fragment;
    }
}
