package com.aae.medminder.models;

public class MeasurementSubtype {
    private String measurementSubtypeID;
    private String measurementTypeID;
    private String title;
    private String unit;
    private int minLevel;
    private int maxLevel;

    public MeasurementSubtype(String measurementSubtypeID, String measurementTypeID, String title, String unit, int minLevel, int maxLevel) {
        this.measurementSubtypeID = measurementSubtypeID;
        this.measurementTypeID = measurementTypeID;
        this.title = title;
        this.unit = unit;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public String getMeasurementSubtypeID() {
        return measurementSubtypeID;
    }

    public void setMeasurementSubtypeID(String measurementSubtypeID) {
        this.measurementSubtypeID = measurementSubtypeID;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }
}
