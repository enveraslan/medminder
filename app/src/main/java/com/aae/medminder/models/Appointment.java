package com.aae.medminder.models;

import java.util.Date;

public class Appointment extends TreatmentDetail{
    private String title;
    private String description;
    private String location;
    private boolean addToCalendar;

    public Appointment(int treatmentID, Date time, boolean snooze, Date confirmOn, String title, String description, String location, boolean addToCalendar) {
        super(treatmentID, time, snooze, confirmOn);
        this.title = title;
        this.description = description;
        this.location = location;
        this.addToCalendar = addToCalendar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAddToCalendar() {
        return addToCalendar;
    }

    public void setAddToCalendar(boolean addToCalendar) {
        this.addToCalendar = addToCalendar;
    }
}
