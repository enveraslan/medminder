package com.aae.medminder.models;

import java.util.Date;

public class MedicineDetail extends TreatmentDetail {
    private int dosage;

    public MedicineDetail(int treatmentID, Date time, boolean snooze, Date confirmOn, int dosage) {
        super(treatmentID, time, snooze, confirmOn);
        this.dosage = dosage;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }
}
