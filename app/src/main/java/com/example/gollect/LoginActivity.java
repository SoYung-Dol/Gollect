package com.example.gollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;

import com.example.gollect.utility.BackPressCloseHandler;
import com.example.gollect.utility.NetworkManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private static final int GOOGLE_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = "LoginActivity";
    SharedPreferences loginInfoPreferences;
    public boolean first_google_login;
    SignInButton Google_Login;
    String google_name, google_id, google_email;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        backPressCloseHandler = new BackPressCloseHandler(this);
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
            editor.putString("google_email",acct.getEmail());
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

    //회원가입 확인
    public void googleLoginStep1(){
        google_name = loginInfoPreferences.getString("google_name","");
        google_id = loginInfoPreferences.getString("google_id","");
        google_email = loginInfoPreferences.getString("google_email","");

        getUserData().setGoogleName(google_name);
        getUserData().setGoogleID(google_id+"");
        getUserData().setGoogleEmail(google_email);

        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);

/*
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_hash",google_id);

            new NetworkManager("/users",jsonObject) {
                @Override
                public void errorCallback(int status) {
                    super.errorCallback(status);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogInterface.OnClickListener exitListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finishAffinity();
                                    System.runFinalization();
                                    System.exit(0);
                                    dialog.dismiss();
                                }
                            };

                            new android.app.AlertDialog.Builder(LoginActivity.this)
                                    .setTitle(getString(R.string.network_err_msg))
                                    .setPositiveButton(getString(R.string.ok), exitListener)
                                    .setCancelable(false)
                                    .show();
                        }
                    });
                }

                @Override
                public void responseCallback(JSONObject responseJson) {

                    try {
                        if (responseJson.getBoolean("result")) {
                            googleLoginStep3();
                        }else{
                            googleLoginStep2();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }catch (JSONException e){
            e.printStackTrace();
        }
*/
    }

    //회원가입
    public void googleLoginStep2(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_hash",google_id);
            jsonObject.accumulate("user_name",google_name);
            jsonObject.accumulate("user_email",google_email);

            new NetworkManager("/users/signup",jsonObject) {
                @Override
                public void errorCallback(int status) {
                    super.errorCallback(status);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogInterface.OnClickListener exitListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finishAffinity();
                                    System.runFinalization();
                                    System.exit(0);
                                    dialog.dismiss();
                                }
                            };

                            new android.app.AlertDialog.Builder(LoginActivity.this)
                                    .setTitle(getString(R.string.network_err_msg))
                                    .setPositiveButton(getString(R.string.ok), exitListener)
                                    .setCancelable(false)
                                    .show();
                        }
                    });
                }

                @Override
                public void responseCallback(JSONObject responseJson) {

                    try {
                        if (responseJson.getBoolean("result")) {
                            googleLoginStep3();
                        }else{
                            Log.d(TAG,"회원가입실패");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    //로그인
    public void googleLoginStep3() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_hash",google_id);

            new NetworkManager("/users/login",jsonObject) {
                @Override
                public void errorCallback(int status) {
                    super.errorCallback(status);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogInterface.OnClickListener exitListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finishAffinity();
                                    System.runFinalization();
                                    System.exit(0);
                                    dialog.dismiss();
                                }
                            };

                            new android.app.AlertDialog.Builder(LoginActivity.this)
                                    .setTitle(getString(R.string.network_err_msg))
                                    .setPositiveButton(getString(R.string.ok), exitListener)
                                    .setCancelable(false)
                                    .show();
                        }
                    });
                }

                @Override
                public void responseCallback(JSONObject responseJson) {

                    try {
                        if (responseJson.getBoolean("result")) {
                            Log.d(TAG,"로그인성공");
                            loginSuccess();
                        }else{
                            Log.d(TAG,"로그인실패");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void loginSuccess(){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
