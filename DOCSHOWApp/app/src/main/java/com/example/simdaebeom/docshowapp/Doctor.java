package com.example.simdaebeom.docshowapp;

public class Doctor {
    String doctorID;
    String doctorName;
    String hospitalID;
    String department;
    String telephone;
    String dayOfWork;



    public Doctor(String doctorID, String doctorName, String hospitalID, String department, String telephone, String dayOfWork) {
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.hospitalID = hospitalID;
        this.department = department;
        this.telephone = telephone;
        this.dayOfWork = dayOfWork;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getDayOfWork() {
        return dayOfWork;
    }

    public void setDayOfWork(String dayOfWork) {
        this.dayOfWork = dayOfWork;
    }
}

