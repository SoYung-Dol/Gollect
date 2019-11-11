package com.example.gollect.utility;

import android.app.Notification;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

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

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Notification notification = sbn.getNotification();
        Bundle extras = sbn.getNotification().extras;
        String title = extras.getString(Notification.EXTRA_TITLE);
        CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
        CharSequence subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);
        Icon smallIcon = notification.getSmallIcon();
        Icon largeIcon = notification.getLargeIcon();

        Log.d(TAG, "onNotificationPosted ~ " +
                " packageName: " + sbn.getPackageName() +
                " id: " + sbn.getId() +
                " postTime: " + sbn.getPostTime() +
                " title: " + title +
                " text : " + text +
                " subText: " + subText);
    }

}