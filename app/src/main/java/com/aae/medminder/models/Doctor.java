package com.aae.medminder.models;

public class Doctor extends  Person {
    private String location;
    private String email;

    public Doctor(String firstName, String lastName, String location, String email) {
        super(firstName, lastName);
        this.location = location;
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
