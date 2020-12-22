package com.aae.medminder.models;

import java.util.Date;

public class MeasurementDetail extends TreatmentDetail {
    private int value1;
    private int value2;

    public MeasurementDetail(int treatmentID, Date time, boolean snooze, Date confirmOn, int value1, int value2) {
        super(treatmentID, time, snooze, confirmOn);
        this.value1 = value1;
        this.value2 = value2;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }
}
