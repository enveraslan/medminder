package com.aae.medminder.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity (nameInDb = "MeasurementTreatment")
public class MeasurementTreatment {
    @Id
    private Long measurementTreatmentID;

    private Long treatmentID;

    @ToOne(joinProperty = "treatmentID")
    private Treatment treatment;

    @Property(nameInDb = "date")
    private String date;

    @Property (nameInDb = "value")
    private Long value;

    private String measurementTypeID;

    @ToOne(joinProperty = "measurementTypeID")
    private MeasurementType measurementType;

    @Property (nameInDb = "measured")
    private String measured;
    
    @Property (nameInDb = "time")
    private String time;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1554646681)
    private transient MeasurementTreatmentDao myDao;

    @Generated(hash = 1283761343)
    public MeasurementTreatment(Long measurementTreatmentID, Long treatmentID,
            String date, Long value, String measurementTypeID, String measured,
            String time) {
        this.measurementTreatmentID = measurementTreatmentID;
        this.treatmentID = treatmentID;
        this.date = date;
        this.value = value;
        this.measurementTypeID = measurementTypeID;
        this.measured = measured;
        this.time = time;
    }

    @Generated(hash = 69334444)
    public MeasurementTreatment() {
    }

    public Long getMeasurementTreatmentID() {
        return this.measurementTreatmentID;
    }

    public void setMeasurementTreatmentID(Long measurementTreatmentID) {
        this.measurementTreatmentID = measurementTreatmentID;
    }

    public Long getTreatmentID() {
        return this.treatmentID;
    }

    public void setTreatmentID(Long treatmentID) {
        this.treatmentID = treatmentID;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getValue() {
        return this.value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getMeasurementTypeID() {
        return this.measurementTypeID;
    }

    public void setMeasurementTypeID(String measurementTypeID) {
        this.measurementTypeID = measurementTypeID;
    }

    public String getMeasured() {
        return this.measured;
    }

    public void setMeasured(String measured) {
        this.measured = measured;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Generated(hash = 427483173)
    private transient Long treatment__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 104875332)
    public Treatment getTreatment() {
        Long __key = this.treatmentID;
        if (treatment__resolvedKey == null
                || !treatment__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreatmentDao targetDao = daoSession.getTreatmentDao();
            Treatment treatmentNew = targetDao.load(__key);
            synchronized (this) {
                treatment = treatmentNew;
                treatment__resolvedKey = __key;
            }
        }
        return treatment;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1980563665)
    public void setTreatment(Treatment treatment) {
        synchronized (this) {
            this.treatment = treatment;
            treatmentID = treatment == null ? null : treatment.getTreatmentID();
            treatment__resolvedKey = treatmentID;
        }
    }

    @Generated(hash = 1224394782)
    private transient String measurementType__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 31255922)
    public MeasurementType getMeasurementType() {
        String __key = this.measurementTypeID;
        if (measurementType__resolvedKey == null
                || measurementType__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MeasurementTypeDao targetDao = daoSession.getMeasurementTypeDao();
            MeasurementType measurementTypeNew = targetDao.load(__key);
            synchronized (this) {
                measurementType = measurementTypeNew;
                measurementType__resolvedKey = __key;
            }
        }
        return measurementType;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1039260195)
    public void setMeasurementType(MeasurementType measurementType) {
        synchronized (this) {
            this.measurementType = measurementType;
            measurementTypeID = measurementType == null ? null
                    : measurementType.getMeasurementTypeID();
            measurementType__resolvedKey = measurementTypeID;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 33943822)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMeasurementTreatmentDao() : null;
    }



}
