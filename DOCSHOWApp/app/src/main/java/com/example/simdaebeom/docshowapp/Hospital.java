package com.example.simdaebeom.docshowapp;

public class Hospital {

    String hospitalID;
    String hospitalName;
    String Telephone;
    String hospitalAddress;

    public Hospital(String hospitalID, String hospitalName, String telephone,String hospitalAddress) {
        this.hospitalID = hospitalID;
        this.hospitalName = hospitalName;
        this.Telephone = telephone;
        this.hospitalAddress = hospitalAddress;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public Hospital( String hospitalName, String hospitalAddress,String telephone) {
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.Telephone = telephone;

    }
}
