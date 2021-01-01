package com.aae.medminder.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Appointment")
public class Appointment{

    @Id (autoincrement = true)
    private Long AppointmentID;

    @Generated(hash = 1092669253)
    public Appointment(Long AppointmentID) {
        this.AppointmentID = AppointmentID;
    }

    @Generated(hash = 1660940633)
    public Appointment() {
    }

    public Long getAppointmentID() {
        return this.AppointmentID;
    }

    public void setAppointmentID(Long AppointmentID) {
        this.AppointmentID = AppointmentID;
    }



}
