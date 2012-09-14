package com.siu.android.athismons.dao.model;

import java.util.List;
import com.siu.android.athismons.dao.model.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
import java.util.ArrayList;
// KEEP INCLUDES END
/**
 * Entity mapped to table DIRECTORY.
 */
public class Directory implements java.io.Serializable {

    private Long id;
    private String title;
    private String listPicture;
    private String url;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient DirectoryDao myDao;

    private List<Card> cardList;

    // KEEP FIELDS - put your custom fields here
    private List<Card> transientCards;
    // KEEP FIELDS END

    public Directory() {
    }

    public Directory(Long id) {
        this.id = id;
    }

    public Directory(Long id, String title, String listPicture, String url) {
        this.id = id;
        this.title = title;
        this.listPicture = listPicture;
        this.url = url;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDirectoryDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListPicture() {
        return listPicture;
    }

    public void setListPicture(String listPicture) {
        this.listPicture = listPicture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public synchronized List<Card> getCardList() {
        if (cardList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CardDao targetDao = daoSession.getCardDao();
            cardList = targetDao._queryDirectory_CardList(id);
        }
        return cardList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetCardList() {
        cardList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here

    public List<Card> getTransientCards() {
        if(null == transientCards) {
            transientCards = new ArrayList<Card>();
        }
        return transientCards;
    }
    // KEEP METHODS END

}
