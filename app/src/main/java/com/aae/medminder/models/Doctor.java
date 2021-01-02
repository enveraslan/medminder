package com.aae.medminder.models;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "Doctor")
public class Doctor {

    @Id(autoincrement = true)
    private Long doctorID;
    @Property(nameInDb = "firstName")
    private String firstName;
    @Property(nameInDb = "lastName")
    private String lastName;
    @Property(nameInDb = "phoneNumber")
    private String phoneNumber;
    @Property(nameInDb = "email")
    private String email;
    @Property(nameInDb = "location")
    private String location;

    @Generated(hash = 610710223)
    public Doctor(Long doctorID, String firstName, String lastName, String phoneNumber, String email,
            String location) {
        this.doctorID = doctorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.location = location;
    }

    @Generated(hash = 73626718)
    public Doctor() {
    }

    public Long getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(Long doctorID) {
        this.doctorID = doctorID;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
