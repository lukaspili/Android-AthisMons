package com.siu.android.athismons;

import android.app.AlarmManager;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AppConstants {

    public static final String NEWS_URL = "http://www.mairie-athis-mons.fr/actualite/feed-appli.php";
    public static final String AGENDA_URL = "http://www.mairie-athis-mons.fr/agenda/feed-agenda-prochainement-appli.php";
    public static final String DIRECTORY_URL = "http://www.mairie-athis-mons.fr/annuaire/annuaire-appli.php";
    public static final String MENU_URL = "http://www.mairie-athis-mons.fr/scolaire/menus-restauration/feed-appli.php";
    public static final String INCIDENT_SUBMIT_URL = "http://www.google.com";

    public static final String PREF_NEWS_MD5_KEY = "news_md5";
    public static final String PREF_AGENDA_MD5_KEY = "agenda_md5";
    public static final String PREF_MENU_MD5_KEY = "menu_md5";
    public static final String PREF_DIRECTORY_MD5_KEY = "directory_md5";

    /* Location */
    public static final int MAX_DISTANCE = 75;
    public static final long MAX_TIME = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

    public static final boolean DEBUG = false;
}
