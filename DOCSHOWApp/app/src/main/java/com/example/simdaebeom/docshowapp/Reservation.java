package com.example.simdaebeom.docshowapp;

public class Reservation {
    String reservationNumber;
    String doctorID;
    String userID;
    String date;
    String time;

    public Reservation(String reservationNumber, String doctorID, String userID, String date, String time) {
        this.reservationNumber = reservationNumber;
        this.doctorID = doctorID;
        this.userID = userID;
        this.date = date;
        this.time = time;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
}
