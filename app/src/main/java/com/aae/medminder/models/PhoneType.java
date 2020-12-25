package com.aae.medminder.models;

public class PhoneType {
    private String phoneType;
    private String name;


    public PhoneType(String phoneType, String name) {
        this.phoneType = phoneType;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhoneType() {
        return phoneType;
    }
}
