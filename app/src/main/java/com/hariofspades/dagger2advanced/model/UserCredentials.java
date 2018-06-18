package com.hariofspades.dagger2advanced.model;

public class UserCredentials {
    private String UserName;
    private String Password;
    private String Timestamp;

    public UserCredentials(String userName, String password, String timeStamp) {
        this.UserName = userName;
        this.Password = password;
        this.Timestamp = timeStamp;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getTimeStamp() {
        return Timestamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.Timestamp = timeStamp;
    }
}
