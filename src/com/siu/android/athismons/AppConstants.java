package com.siu.android.athismons;

import android.app.AlarmManager;

/**
 * @author Lukasz Piliszczuk <lukasz.pili AT gmail.com>
 */
public class AppConstants {

    public static final String NEWS_URL = "http://www.mairie-athis-mons.fr/actualite/feed-appli.php";
    public static final String AGENDA_URL = "http://www.mairie-athis-mons.fr/agenda/feed-agenda-prochainement-appli.php";
    public static final String DIRECTORY_URL = "http://www.mairie-athis-mons.fr/annuaire/annuaire-appli.php";
    public static final String MENU_URL = "http://www.mairie-athis-mons.fr/scolaire/menus-restauration/feed-appli.php?d=f";

    public static final String PERSONAL_SPACE_URL = "https://athis-mons.espace-citoyens.net/Pages/ChoixPrincipal.aspx";

    public static final String INCIDENT_URL = "http://www.mairie-athis-mons.fr/application/iphoneXml/incident.php";

    public static final String[] INCIDENT_EMAIL_GENERAL = {"pgoupil@mairie-athis-mons.fr", "klozes@mairie-athis-mons.fr"};
    public static final String[] INCIDENT_EMAIL_CAT1 = {"voirie@portesessonne.fr"};
    public static final String[] INCIDENT_EMAIL_CAT2 = {"domainepublic@portesessonne.fr"};
    public static final String[] INCIDENT_EMAIL_CAT3 = {"domainepublic@portesessonne.fr"};
    public static final String[] INCIDENT_EMAIL_CAT4 = {"domainepublic@portesessonne.fr"};
    public static final String[] INCIDENT_EMAIL_CAT5 = {"environ@mairie-athis-mons.fr", "dgst@mairie-athis-mons.fr"};
    public static final String[] INCIDENT_EMAIL_CAT6 = {"proprete@portesessonne.fr"};
    public static final String[] INCIDENT_EMAIL_CAT7 = {"police@mairie-athis-mons.fr", "saujard@portesessonne.fr"};
    public static final String[] INCIDENT_EMAIL_CAT8 = {"police@mairie-athis-mons.fr", "dgst@mairie-athis-mons.fr", "saujard@portesessonne.fr"};
    public static final String[] INCIDENT_EMAIL_CAT9 = {"environ@mairie-athis-mons.fr", "dgst@mairie-athis-mons.fr"};
    public static final String[] INCIDENT_EMAIL_CAT10 = {"voirie@portesessonne.fr"};
    public static final String[] INCIDENT_EMAIL_CAT11 = {"environ@mairie-athis-mons.fr", "dgst@mairie-athis-mons.fr"};

    public static final String PREF_NEWS_MD5_KEY = "news_md5";
    public static final String PREF_AGENDA_MD5_KEY = "agenda_md5";
    public static final String PREF_MENU_MD5_KEY = "menu_md5";
    public static final String PREF_DIRECTORY_MD5_KEY = "directory_md5";

    public static final String INCIDENT_MAP_URL = "http://maps.google.com/maps?q=%1$s,+%2$s+(Incident)";

    /* Location */
    public static final int MAX_DISTANCE = 5;
    public static final long MAX_TIME = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

    public static final boolean DEBUG = true;
}
