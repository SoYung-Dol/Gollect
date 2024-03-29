package com.example.gollect;

import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener  {

    private static UserData     userData;
    private  static PlatformData platformData;
    public static GoogleApiClient mGoogleApiClient;
    public final static boolean DEBUG_LOG                          = true;
    public static final String Preferences_LOGIN				    = "login";

    @Override
    public void onClick(View v) {

    }

    public void googleSignout(){

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                System.runFinalization();
                System.exit(0);

            }
        });
    }
    public UserData getUserData() {
        return userData;
    }
    public static void createUserData() {
        userData = new UserData();
    }
}
