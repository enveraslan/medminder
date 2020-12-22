package com.aae.medminder.models;

import java.util.Date;

public class MedicineTreatment extends Treatment {
    private int medicineID;

    public MedicineTreatment(int doctorID, String treatmentTypeID, Date startDate,
                             String durationTypeID, Date endDate, int duration,
                             String frequencyTypeID, int everyXHour, int everyXDay,
                             Date firstIntakeTime, boolean isActive, int medicineID) {
        super(doctorID, treatmentTypeID, startDate, durationTypeID,
                endDate, duration, frequencyTypeID, everyXHour,
                everyXDay, firstIntakeTime, isActive);
        this.medicineID = medicineID;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }
}
