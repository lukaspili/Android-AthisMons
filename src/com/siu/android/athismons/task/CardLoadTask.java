package com.siu.android.athismons.task;

import android.os.AsyncTask;
import com.siu.android.athismons.activity.DirectoryDetailActivity;
import com.siu.android.athismons.dao.DatabaseHelper;
import com.siu.android.athismons.dao.model.Card;
import com.siu.android.athismons.dao.model.CardDao;
import com.siu.android.athismons.dao.model.Directory;

import java.util.List;

/**
 * Load cards from database by providing existing directory
 *
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class CardLoadTask extends AsyncTask<Void, Void, List<Card>> {

    private DirectoryDetailActivity activity;
    private Directory directory;

    public CardLoadTask(DirectoryDetailActivity activity, Directory directory) {
        this.activity = activity;
        this.directory = directory;
    }

    @Override
    protected List<Card> doInBackground(Void... voids) {
        return DatabaseHelper.getInstance().getDaoSession().getCardDao().queryBuilder()
                .where(CardDao.Properties.DirectoryId.eq(directory.getId()))
                .list();
    }

    @Override
    protected void onPostExecute(List<Card> cards) {
        if (null == activity) {
            return;
        }

        activity.onCardLoadTaskFinished(cards);
    }

    public void setActivity(DirectoryDetailActivity activity) {
        this.activity = activity;
    }
}
