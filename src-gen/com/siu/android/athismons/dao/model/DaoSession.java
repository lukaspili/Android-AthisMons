package com.siu.android.athismons.dao.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.IdentityScopeType;

import com.siu.android.athismons.dao.model.News;
import com.siu.android.athismons.dao.model.Agenda;
import com.siu.android.athismons.dao.model.Directory;
import com.siu.android.athismons.dao.model.Card;

import com.siu.android.athismons.dao.model.NewsDao;
import com.siu.android.athismons.dao.model.AgendaDao;
import com.siu.android.athismons.dao.model.DirectoryDao;
import com.siu.android.athismons.dao.model.CardDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig newsDaoConfig;
    private final DaoConfig agendaDaoConfig;
    private final DaoConfig directoryDaoConfig;
    private final DaoConfig cardDaoConfig;

    private final NewsDao newsDao;
    private final AgendaDao agendaDao;
    private final DirectoryDao directoryDao;
    private final CardDao cardDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        newsDaoConfig = daoConfigMap.get(NewsDao.class).clone();
        newsDaoConfig.initIdentityScope(type);

        agendaDaoConfig = daoConfigMap.get(AgendaDao.class).clone();
        agendaDaoConfig.initIdentityScope(type);

        directoryDaoConfig = daoConfigMap.get(DirectoryDao.class).clone();
        directoryDaoConfig.initIdentityScope(type);

        cardDaoConfig = daoConfigMap.get(CardDao.class).clone();
        cardDaoConfig.initIdentityScope(type);

        newsDao = new NewsDao(newsDaoConfig, this);
        agendaDao = new AgendaDao(agendaDaoConfig, this);
        directoryDao = new DirectoryDao(directoryDaoConfig, this);
        cardDao = new CardDao(cardDaoConfig, this);

        registerDao(News.class, newsDao);
        registerDao(Agenda.class, agendaDao);
        registerDao(Directory.class, directoryDao);
        registerDao(Card.class, cardDao);
    }
    
    public void clear() {
        newsDaoConfig.getIdentityScope().clear();
        agendaDaoConfig.getIdentityScope().clear();
        directoryDaoConfig.getIdentityScope().clear();
        cardDaoConfig.getIdentityScope().clear();
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }

    public AgendaDao getAgendaDao() {
        return agendaDao;
    }

    public DirectoryDao getDirectoryDao() {
        return directoryDao;
    }

    public CardDao getCardDao() {
        return cardDao;
    }

}
