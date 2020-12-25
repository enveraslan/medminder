package com.aae.medminder.models;

public class BloodType {
    private String bloodType;
    private String title;

    public BloodType(String bloodType, String title) {
        this.bloodType = bloodType;
        this.title = title;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getTitle() {
        return title;
    }

}
