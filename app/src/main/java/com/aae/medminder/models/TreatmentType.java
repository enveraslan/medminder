package com.aae.medminder.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity( nameInDb = "TreatmentType")
public class TreatmentType {
    @Id
    private String treatmentTypeID;

    @Property (nameInDb = "title")
    private String title;

    @Generated(hash = 574688682)
    public TreatmentType(String treatmentTypeID, String title) {
        this.treatmentTypeID = treatmentTypeID;
        this.title = title;
    }
    @Generated(hash = 1468493929)
    public TreatmentType() {
    }
    public String getTreatmentTypeID() {
        return this.treatmentTypeID;
    }
    public void setTreatmentTypeID(String treatmentTypeID) {
        this.treatmentTypeID = treatmentTypeID;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
