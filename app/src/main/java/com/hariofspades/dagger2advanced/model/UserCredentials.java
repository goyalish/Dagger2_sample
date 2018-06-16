package com.hariofspades.dagger2advanced.model;

public class UserCredentials {
    private String userName;
    private String password;
    private String timeStamp;

    public UserCredentials(String userName, String password, String timeStamp) {
        this.userName = userName;
        this.password = password;
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
