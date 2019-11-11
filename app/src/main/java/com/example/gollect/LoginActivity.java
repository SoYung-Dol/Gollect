package com.example.gollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends BaseActivity {

    private static final int GOOGLE_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    String TAG = "LoginActivity";
    SharedPreferences loginInfoPreferences;
    public boolean first_google_login;
    SignInButton Google_Login;
    String google_name, google_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createUserData();
        first_google_login = true;

        Google_Login = findViewById(R.id.Google_Login);
        Google_Login.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        loginInfoPreferences = getSharedPreferences(Preferences_LOGIN, MODE_PRIVATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data); //로그인 결과 객체를 result에 저장
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result){
        if(DEBUG_LOG) Log.d(TAG,"handleSignInResult:"+result.isSuccess());
        if(result.isSuccess()) {
            if(DEBUG_LOG) Log.d(TAG,"Google Login Successed");
            GoogleSignInAccount acct = result.getSignInAccount();


            SharedPreferences.Editor editor = loginInfoPreferences.edit();
            editor.putString("google_name", acct.getDisplayName());
            editor.putString("google_id", acct.getId()+"");
            editor.putString("google_profile",acct.getPhotoUrl()+"");
            editor.commit();

//            if(first_google_login) {
//                first_google_login = false;
//                googleLoginStep1();
//            }
            googleLoginStep1();
        }else {
            if(DEBUG_LOG) Log.d(TAG,"Google Login Failed");
        }

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.Google_Login:
                googleLogin();
                break;
        }
    }


    public void googleLogin(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN); // go to onActivityResult()
    }

    public void googleLoginStep1(){
        google_name = loginInfoPreferences.getString("google_name","");
        google_id = loginInfoPreferences.getString("google_id","");

        getUserData().setGoogleName(google_name);
        getUserData().setGoogleID(google_id+"");

//        Intent mainActivity = new Intent(this, MainActivity.class);
//        startActivity(mainActivity);
        Intent testActivity = new Intent(this, testActivity.class);
        startActivity(testActivity);
    }
}
