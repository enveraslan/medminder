package com.aae.medminder.models;

public class FrequencyType {
    private String frequencyType;
    private String title;

    public FrequencyType(String frequencyType, String title) {
        this.frequencyType = frequencyType;
        this.title = title;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    public String getTitle() {
        return title;
    }


}
