package com.aae.medminder.models;

public class MeasurementType {
    private String measurementTypeID;
    private String title;

    public MeasurementType(String measurementTypeID, String title) {
        this.measurementTypeID = measurementTypeID;
        this.title = title;
    }

    public String getMeasurementTypeID() {
        return measurementTypeID;
    }

    public void setMeasurementTypeID(String measurementTypeID) {
        this.measurementTypeID = measurementTypeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
