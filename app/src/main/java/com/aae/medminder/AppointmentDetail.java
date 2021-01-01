package com.aae.medminder;

public class AppointmentDetail {

    private String doctor;
    private String specialty;
    private String hospital;
    private String date;
    private String time;


    public AppointmentDetail(String doctor, String specialty, String hospital, String date, String time) {
        this.doctor = doctor;
        this.hospital = hospital;
        this.date = date;
        this.specialty = specialty;
        this.time = time;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AppointmentDetail{" +
                "doctor='" + doctor + '\'' +
                ", specialty='" + specialty + '\'' +
                ", hospital='" + hospital + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
