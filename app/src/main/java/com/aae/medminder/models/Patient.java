package com.aae.medminder.models;

import java.util.Date;

public class Patient extends Person {
    private Date birthdate;
    private String bloodType;
    private int height;
    private String address;
    private String zipCode;

    public Patient(String firstName, String lastName, Date birthdate, String bloodType, int height, String address, String zipCode) {
        super(firstName, lastName);
        this.birthdate = birthdate;
        this.bloodType = bloodType;
        this.height = height;
        this.address = address;
        this.zipCode = zipCode;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
