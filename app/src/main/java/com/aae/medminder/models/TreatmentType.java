package com.aae.medminder.models;

public class TreatmentType {
    private String treatmentType;
    private String title;

    public TreatmentType(String treatmentType, String title) {
        this.treatmentType = treatmentType;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTreatmentType() {
        return treatmentType;
    }
}
