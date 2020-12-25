package com.aae.medminder.models;

import java.util.Date;

public class Treatment {
    private int treatmentID;
    private int doctorID;
    private String treatmentTypeID;
    private Date startDate;
    private String durationTypeID;
    private Date endDate;
    private int duration;
    private String frequencyTypeID;
    private int everyXHour;
    private int everyXDay;
    private Date firstIntakeTime;
    private boolean isActive;

    public Treatment(int doctorID, String treatmentTypeID, Date startDate, String durationTypeID,
                     Date endDate, int duration, String frequencyTypeID, int everyXHour,
                     int everyXDay, Date firstIntakeTime, boolean isActive) {
        this.doctorID = doctorID;
        this.treatmentTypeID = treatmentTypeID;
        this.startDate = startDate;
        this.durationTypeID = durationTypeID;
        this.endDate = endDate;
        this.duration = duration;
        this.frequencyTypeID = frequencyTypeID;
        this.everyXHour = everyXHour;
        this.everyXDay = everyXDay;
        this.firstIntakeTime = firstIntakeTime;
        this.isActive = isActive;
    }

    public int getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(int treatmentID) {
        this.treatmentID = treatmentID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getTreatmentTypeID() {
        return treatmentTypeID;
    }

    public void setTreatmentTypeID(String treatmentTypeID) {
        this.treatmentTypeID = treatmentTypeID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDurationTypeID() {
        return durationTypeID;
    }

    public void setDurationTypeID(String durationTypeID) {
        this.durationTypeID = durationTypeID;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFrequencyTypeID() {
        return frequencyTypeID;
    }

    public void setFrequencyTypeID(String frequencyTypeID) {
        this.frequencyTypeID = frequencyTypeID;
    }

    public int getEveryXHour() {
        return everyXHour;
    }

    public void setEveryXHour(int everyXHour) {
        this.everyXHour = everyXHour;
    }

    public int getEveryXDay() {
        return everyXDay;
    }

    public void setEveryXDay(int everyXDay) {
        this.everyXDay = everyXDay;
    }

    public Date getFirstIntakeTime() {
        return firstIntakeTime;
    }

    public void setFirstIntakeTime(Date firstIntakeTime) {
        this.firstIntakeTime = firstIntakeTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
