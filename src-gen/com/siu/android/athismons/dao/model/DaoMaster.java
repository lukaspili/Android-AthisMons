package com.siu.android.athismons.dao.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.IdentityScopeType;

import com.siu.android.athismons.dao.model.NewsDao;
import com.siu.android.athismons.dao.model.AgendaDao;
import com.siu.android.athismons.dao.model.DirectoryDao;
import com.siu.android.athismons.dao.model.CardDao;
import com.siu.android.athismons.dao.model.MenuDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 3): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 3;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        NewsDao.createTable(db, ifNotExists);
        AgendaDao.createTable(db, ifNotExists);
        DirectoryDao.createTable(db, ifNotExists);
        CardDao.createTable(db, ifNotExists);
        MenuDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        NewsDao.dropTable(db, ifExists);
        AgendaDao.dropTable(db, ifExists);
        DirectoryDao.dropTable(db, ifExists);
        CardDao.dropTable(db, ifExists);
        MenuDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(NewsDao.class);
        registerDaoClass(AgendaDao.class);
        registerDaoClass(DirectoryDao.class);
        registerDaoClass(CardDao.class);
        registerDaoClass(MenuDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
