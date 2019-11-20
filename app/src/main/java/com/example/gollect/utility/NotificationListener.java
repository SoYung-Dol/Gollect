package com.example.gollect.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.gollect.AlarmData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class NotificationListener extends NotificationListenerService {
    public final static String TAG = "NotificationListener";


    //앱에 설치된 Realm파일을 찾아서 가져오는 코드
    Realm realm = Realm.getDefaultInstance();

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);

        Log.d(TAG, "onNotificationRemoved ~ " +
                " packageName: " + sbn.getPackageName() +
                " id: " + sbn.getId());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Notification notification = sbn.getNotification();

        Bundle extras = sbn.getNotification().extras;
        int notificationIcon = extras.getInt(Notification.EXTRA_SMALL_ICON);
        String title = extras.getString(Notification.EXTRA_TITLE);
        CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
        CharSequence subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
        int id = sbn.getId();
        Icon smallIcon = notification.getSmallIcon();
        Icon largeIcon = notification.getLargeIcon();

        Log.d(TAG, "onNotificationPosted ~ " +
                " icon: " + notificationIcon +
                " channel id: " + notification.getChannelId() +
                " packageName: " + sbn.getPackageName() +
                " notification: " + sbn.getNotification() +
                " id: " + id +
                " postTime: " + sbn.getPostTime() +
                " title: " + title +
                " text : " + text +
                " subText: " + subText);
        Log.i(TAG, "@@@@@@@@@@@@@@@@@@@@@" + id);

        DateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH=24h, hh=12h
        Date date = new Date(sbn.getPostTime());

        //Realm에 객체(데이터) 저장

        if(text.toString().contains("광고"))
            NotificationListener.this.cancelNotification(sbn.getKey());

        if(text != null && !sbn.getNotification().toString().contains("quiet_new_message") && !sbn.getPackageName().contains("android"))
            addAlarm(sbn.getPackageName(), smallIcon,title, text.toString(), date);

    }


    private void addAlarm(String appName, Icon smallIcon, String sender, String contents, Date date){
        date.setTime(System.currentTimeMillis());
        final AlarmData AlarmData = new AlarmData(getAlarmDataId(), appName, sender, contents, date);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //Realm에 생성한 다이어리를 저장하는 코드
                realm.copyToRealm(AlarmData);
                // 저장한 다이어리를 다이어리 리스트에 담아주는코드
                //AlarmDataList.add(AlarmData);
                //AlarmDataAdapter.notifyDataSetChanged();
            }
        });
    }

    //Realm에 저장할 Diary의 ID를 자동으로 증가시켜서 가져오는 메소드
    private int getAlarmDataId() {
        //자동으로 Id를 증가시켜야 되기 때문에
        //이 메소드가 불린 시점에서 Realm에 저장되어있는 Diary의 ID의 최대값을 구해옵니다.
        Number currentId = realm.where(AlarmData.class).max("id");

        //새로 저장할 Diary의 ID값
        int nextId;

        //Realm에 Diary가 저장되어있지 않아 저장된 ID도 없는 경우에는
        if (currentId == null) {
            //처음 생성되는 ID이기 때문에 1을 지정합니다
            nextId = 1;
            //Realm에 Diary가 저장되어있는 경우에는 저장되어 있는 Diary의 최대 ID를 찾아와서
        } else {
            //찾아온 ID에 +1을 해서 돌려줍니다.
            nextId = currentId.intValue() + 1;
        }
        return nextId;
    }
}