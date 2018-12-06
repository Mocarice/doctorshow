package com.example.simdaebeom.docshowapp;

public class MedicalRecord {
    String medicalRecordID;
    String disease;
    String medicalContents;
    String patientID;
    String doctorID;
    String date;


    public MedicalRecord(String medicalRecordID, String disease, String medicalContents, String patientID, String doctorID, String date) {
        this.medicalRecordID = medicalRecordID;
        this.disease = disease;
        this.medicalContents = medicalContents;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.date = date;
    }

    public String getMedicalRecordID() {
        return medicalRecordID;
    }

    public void setMedicalRecordID(String medicalRecordID) {
        this.medicalRecordID = medicalRecordID;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getMedicalContents() {
        return medicalContents;
    }

    public void setMedicalContents(String medicalContents) {
        this.medicalContents = medicalContents;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

