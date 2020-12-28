package com.aae.medminder.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;


@Entity (nameInDb = "Treatment")
public class Treatment {
    @Id (autoincrement = true)
    private Long treatmentID;

    private String treatmentTypeID;

    @Property (nameInDb = "createdDate")
    private String createdDate;

    @ToOne (joinProperty = "treatmentTypeID")
    private TreatmentType treatmentType;



    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 315845799)
    private transient TreatmentDao myDao;

    @Generated(hash = 1688436796)
    public Treatment(Long treatmentID, String treatmentTypeID, String createdDate) {
        this.treatmentID = treatmentID;
        this.treatmentTypeID = treatmentTypeID;
        this.createdDate = createdDate;
    }

    @Generated(hash = 852361623)
    public Treatment() {
    }

    public Long getTreatmentID() {
        return this.treatmentID;
    }

    public void setTreatmentID(Long treatmentID) {
        this.treatmentID = treatmentID;
    }

    public String getTreatmentTypeID() {
        return this.treatmentTypeID;
    }

    public void setTreatmentTypeID(String treatmentTypeID) {
        this.treatmentTypeID = treatmentTypeID;
    }

    @Generated(hash = 218811619)
    private transient String treatmentType__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1059791641)
    public TreatmentType getTreatmentType() {
        String __key = this.treatmentTypeID;
        if (treatmentType__resolvedKey == null
                || treatmentType__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreatmentTypeDao targetDao = daoSession.getTreatmentTypeDao();
            TreatmentType treatmentTypeNew = targetDao.load(__key);
            synchronized (this) {
                treatmentType = treatmentTypeNew;
                treatmentType__resolvedKey = __key;
            }
        }
        return treatmentType;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 839868019)
    public void setTreatmentType(TreatmentType treatmentType) {
        synchronized (this) {
            this.treatmentType = treatmentType;
            treatmentTypeID = treatmentType == null ? null
                    : treatmentType.getTreatmentTypeID();
            treatmentType__resolvedKey = treatmentTypeID;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 527306392)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTreatmentDao() : null;
    }

    
}
