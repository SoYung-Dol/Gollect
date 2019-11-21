package com.example.gollect;

import android.graphics.drawable.Icon;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AlarmData extends RealmObject {
    @PrimaryKey
    int id;
    String appName;
    String sender;
    String contents;
    Date writeAt;

    public AlarmData() {
    }

    public AlarmData(int id, String appName) {
        this.id = id;
        this.contents = contents;
    }

    public AlarmData(int id, String appName, String contents){
        this.id = id;
        this.appName = appName;
        this.contents = contents;
    }
    public AlarmData(int id, String appName, String contents, Date writeAt) {
        this.id = id;
        this.appName = appName;
        this.contents = contents;
        this.writeAt = writeAt;
    }

    public AlarmData(int id, String appName, String sender, String contents, Date writeAt) {
        this.id = id;
        this.appName = appName;
        this.sender = sender;
        this.contents = contents;
        this.writeAt = writeAt;
    }

//    public AlarmData(int id, byte[] smallIcon, String appName, String sender, String contents, Date writeAt) {
//        this.id = id;
//        this.smallIcon = smallIcon;
//        this.appName = appName;
//        this.sender = sender;
//        this.contents = contents;
//        this.writeAt = writeAt;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getWriteAt() {
        return writeAt;
    }

    public void setWriteAt(Date writeAt) {
        this.writeAt = writeAt;
    }
}