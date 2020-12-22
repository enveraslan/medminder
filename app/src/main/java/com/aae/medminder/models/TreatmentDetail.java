package com.aae.medminder.models;

import java.util.Date;

public class TreatmentDetail {
    private int treatmentDetailID;
    private int treatmentID;
    private Date time;
    private boolean snooze;
    private Date confirmOn;

    public TreatmentDetail(int treatmentID, Date time, boolean snooze, Date confirmOn) {
        this.treatmentID = treatmentID;
        this.time = time;
        this.snooze = snooze;
        this.confirmOn = confirmOn;
    }

    public int getTreatmentDetailID() {
        return treatmentDetailID;
    }

    public void setTreatmentDetailID(int treatmentDetailID) {
        this.treatmentDetailID = treatmentDetailID;
    }

    public int getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(int treatmentID) {
        this.treatmentID = treatmentID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isSnooze() {
        return snooze;
    }

    public void setSnooze(boolean snooze) {
        this.snooze = snooze;
    }

    public Date getConfirmOn() {
        return confirmOn;
    }

    public void setConfirmOn(Date confirmOn) {
        this.confirmOn = confirmOn;
    }
}
