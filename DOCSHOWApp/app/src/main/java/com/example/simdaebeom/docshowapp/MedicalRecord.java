package com.example.simdaebeom.docshowapp;

public class MedicalRecord {
    String recordNumber;
    String doctorID;
    String specialNote;
    String disease;
    String prescription;
    String smoking;
    String patientID;
    String dateOfExamination;



    public MedicalRecord(String recordNumber, String doctorID, String specialNote, String disease, String prescription, String smoking, String patientID, String dateOfExamination) {
        this.recordNumber = recordNumber;
        this.doctorID = doctorID;
        this.specialNote = specialNote;
        this.disease = disease;
        this.prescription = prescription;
        this.smoking = smoking;
        this.patientID = patientID;
        this.dateOfExamination = dateOfExamination;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDateOfExamination() {
        return dateOfExamination;
    }

    public void setDateOfExamination(String dateOfExamination) {
        this.dateOfExamination = dateOfExamination;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }
}

