package com.aae.medminder.models;

import java.util.Date;

public class Measurement extends Treatment {
    private String measurementType;

    public Measurement(int doctorID, String treatmentTypeID, Date startDate, String durationTypeID, Date endDate, int duration, String frequencyTypeID,
                       int everyXHour, int everyXDay, Date firstIntakeTime,
                       boolean isActive, String measurementType) {
        super(doctorID, treatmentTypeID, startDate, durationTypeID, endDate, duration,
                frequencyTypeID, everyXHour, everyXDay, firstIntakeTime, isActive);
        this.measurementType = measurementType;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }
}
