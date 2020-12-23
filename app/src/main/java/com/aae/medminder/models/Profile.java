package com.aae.medminder.models;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Profile {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String zipCode;

    public Profile() {
        this(null,null,null,null,null);
    }
    public Profile(String firstName, String lastName, String birthDate, String address, String zipCode) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        this.firstName = firstName;
        this.lastName = lastName;
        try {
            if (!TextUtils.isEmpty(birthDate)) {
                this.birthDate = dateFormat.parse(birthDate);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.address = address;
        this.zipCode = zipCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDateString() {
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/M/yyyy");
        if(birthDate != null)
            return dateformat.format(birthDate);
        return null;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    @Override
    public String toString() {
        return "Profile{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
