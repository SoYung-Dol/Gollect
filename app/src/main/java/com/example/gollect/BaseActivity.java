package com.example.gollect;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener  {

    private static UserData     userData;

    public final static boolean DEBUG_LOG                          = true;
    public static final String Preferences_LOGIN						= "login";
    @Override
    public void onClick(View v) {

    }

    public UserData getUserData() {
        return userData;
    }
    public static void createUserData() {
        userData = new UserData();
    }
}
