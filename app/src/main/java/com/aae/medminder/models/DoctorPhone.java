package com.aae.medminder.models;

public class DoctorPhone {
    private int doctorID;
    private String phone;
    private String phoneType;

    public DoctorPhone(int doctorID, String phone, String phoneType) {
        this.doctorID = doctorID;
        this.phone = phone;
        this.phoneType = phoneType;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }
}
