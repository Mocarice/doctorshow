package com.example.simdaebeom.docshowapp;

public class User {
    String userID;
    String userPassword;
    String userName;
    String userBirth;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public User(String userID, String userPassword, String userName, String userBirth) {

        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userBirth = userBirth;
    }
    public User(String userName){
        this.userName = userName;
    }
}
