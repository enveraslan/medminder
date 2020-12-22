package com.aae.medminder.models;

public class MedicineUnit {
    private String medicineUnitID;
    private String title;
    private String description;

    public MedicineUnit(String medicineUnitID, String title, String description) {
        this.medicineUnitID = medicineUnitID;
        this.title = title;
        this.description = description;
    }

    public String getMedicineUnitID() {
        return medicineUnitID;
    }

    public void setMedicineUnitID(String medicineUnitID) {
        this.medicineUnitID = medicineUnitID;
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
}
