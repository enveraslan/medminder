package com.aae.medminder.models;

public class DurationType {
    private String durationType;
    private String title;

    public DurationType(String durationType, String title) {
        this.durationType = durationType;
        this.title = title;
    }

    public String getDurationType() {
        return durationType;
    }

    public String getTitle() {
        return title;
    }

}
