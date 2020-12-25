package com.aae.medminder.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.text.SimpleDateFormat;
import org.greenrobot.greendao.annotation.Generated;

@Entity (nameInDb = "Profile")
public class Profile {
    @Id
    @Property (nameInDb = "firstName")
    private String firstName;
    @Property (nameInDb = "lastName")
    private String lastName;
    @Property (nameInDb = "birthDate")
    private String birthDate;
    @Property (nameInDb = "address")
    private String address;
    @Property (nameInDb = "zipCode")
    private String zipCode;





    @Generated(hash = 277576540)
    public Profile(String firstName, String lastName, String birthDate,
            String address, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.zipCode = zipCode;
    }

    @Generated(hash = 782787822)
    public Profile() {
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

    public void setBirthDate(String birthDate) {
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

    public String getBirthDate() {
        return this.birthDate;
    }
}
