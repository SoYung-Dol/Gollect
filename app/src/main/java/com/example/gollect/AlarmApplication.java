package com.example.gollect;

import android.app.Application;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AlarmApplication extends Application {

    static AlarmActivity alarmActivity = new AlarmActivity();
    public static ArrayList<String> keywordList = alarmActivity.getKeyword();
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("Gollect_Alarm.realm") //생성할 realm파일 이름 지정
                .schemaVersion(0)
                .build();

        RealmConfiguration keywordConfig = new RealmConfiguration.Builder()
                .name("Gollect_Alarm_Keyword.realm")
                .schemaVersion(5)
                .build();



        //Realm에 셋팅한 정보 값을 지정
        Realm keywordDB = Realm.getInstance(keywordConfig);
        Realm.setDefaultConfiguration(config);
    }

    public static ArrayList<String> getKeywordList() {
        return keywordList;
    }

    public static void setKeywordList(ArrayList<String> keywordList) {
        AlarmApplication.keywordList = keywordList;
    }
}