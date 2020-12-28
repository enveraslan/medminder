package com.aae.medminder.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "MedicineUnit")
public class MedicineUnit {
    @Id
    private String medicineUnitID;
    @Property (nameInDb = "title")
    private String title;
    @Property (nameInDb = "description")
    private String description;


    @Generated(hash = 622368689)
    public MedicineUnit(String medicineUnitID, String title, String description) {
        this.medicineUnitID = medicineUnitID;
        this.title = title;
        this.description = description;
    }
    @Generated(hash = 1371655924)
    public MedicineUnit() {
    }
    public String getMedicineUnitID() {
        return this.medicineUnitID;
    }
    public void setMedicineUnitID(String medicineUnitID) {
        this.medicineUnitID = medicineUnitID;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return title;
    }
}
