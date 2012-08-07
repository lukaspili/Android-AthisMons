package com.siu.android.athismons;

import android.preference.PreferenceManager;
import com.siu.android.athismons.dao.DatabaseHelper;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class Application extends com.siu.android.andutils.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (AppConstants.DEBUG) {
            DatabaseHelper.getInstance().getDaoSession().getNewsDao().deleteAll();
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .remove(AppConstants.PREF_NEWS_MD5_KEY)
                    .commit();
        }
    }
}
