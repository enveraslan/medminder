package com.aae.medminder.models;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "MeasurementType")
public class MeasurementType {
    @Id
    private String measurementTypeID;

    @Property (nameInDb = "title")
    private String title;

    @Property (nameInDb = "unit")
    private String unit;

    @Generated(hash = 676128944)
    public MeasurementType(String measurementTypeID, String title, String unit) {
        this.measurementTypeID = measurementTypeID;
        this.title = title;
        this.unit = unit;
    }
    @Generated(hash = 252817933)
    public MeasurementType() {
    }
    public String getMeasurementTypeID() {
        return this.measurementTypeID;
    }
    public void setMeasurementTypeID(String measurementTypeID) {
        this.measurementTypeID = measurementTypeID;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return title;
    }
}
