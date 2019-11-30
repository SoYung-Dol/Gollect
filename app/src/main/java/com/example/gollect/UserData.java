package com.example.gollect;

public class UserData {
    private String userName, userEmail, userHash;
    private Integer userID;

    public void setUserID(Integer userID){
        this.userID = userID;
    }
    public Integer getUserID(){
        return this.userID;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return this.userName;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }
    public String getUserEmail(){
        return this.userEmail;
    }

    public void setUserHash(String userHash){
        this.userHash = userHash;
    }
    public String getUserHash(){
        return this.userHash;
    }
}
