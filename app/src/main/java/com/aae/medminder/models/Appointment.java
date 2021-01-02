package com.aae.medminder.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "Appointment")
public class Appointment{

    @Id (autoincrement = true)
    private Long appointmentID;

    @Property(nameInDb = "title")
    private String title;

    private Long doctorID;

    @ToOne(joinProperty = "doctorID")
    private  Doctor doctor;

    @Property (nameInDb = "date")
    private String date;

    @Property (nameInDb = "time")
    private String time;

    @Property (nameInDb = "location")
    private String location;

    @Property (nameInDb = "Notes")
    private String notes;

    @Property (nameInDb = "reminder")
    private Long reminder;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1077317242)
    private transient AppointmentDao myDao;

    @Generated(hash = 1082360251)
    public Appointment(Long appointmentID, String title, Long doctorID, String date,
            String time, String location, String notes, Long reminder) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.doctorID = doctorID;
        this.date = date;
        this.time = time;
        this.location = location;
        this.notes = notes;
        this.reminder = reminder;
    }

    @Generated(hash = 1660940633)
    public Appointment() {
    }

    public Long getAppointmentID() {
        return this.appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDoctorID() {
        return this.doctorID;
    }

    public void setDoctorID(Long doctorID) {
        this.doctorID = doctorID;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getReminder() {
        return this.reminder;
    }

    public void setReminder(Long reminder) {
        this.reminder = reminder;
    }

    @Generated(hash = 1650391412)
    private transient Long doctor__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1211852376)
    public Doctor getDoctor() {
        Long __key = this.doctorID;
        if (doctor__resolvedKey == null || !doctor__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DoctorDao targetDao = daoSession.getDoctorDao();
            Doctor doctorNew = targetDao.load(__key);
            synchronized (this) {
                doctor = doctorNew;
                doctor__resolvedKey = __key;
            }
        }
        return doctor;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1853493926)
    public void setDoctor(Doctor doctor) {
        synchronized (this) {
            this.doctor = doctor;
            doctorID = doctor == null ? null : doctor.getDoctorID();
            doctor__resolvedKey = doctorID;
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

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 850012473)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAppointmentDao() : null;
    }


}
