package com.example.gollect;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.gollect.item.TextContentsItem;
import com.example.gollect.utility.BackPressCloseHandler;
import com.example.gollect.utility.GetNetworkManager;
import com.example.gollect.utility.PostNetworkManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    private static final int GOOGLE_SIGN_IN = 9001;
    private String TAG = "LoginActivity";
    SharedPreferences loginInfoPreferences;
    public boolean first_google_login;
    SignInButton Google_Login;
    String user_name, user_id, user_email, user_hash;
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
            editor.putString("user_name", acct.getDisplayName());
            editor.putString("user_hash", acct.getId()+"");
            editor.putString("user_profile",acct.getPhotoUrl()+"");
            editor.putString("user_email",acct.getEmail());
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
    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
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

    //회원가입 및 회원가입확인
    public void googleLoginStep1(){
        user_name = loginInfoPreferences.getString("user_name","");
        user_hash = loginInfoPreferences.getString("user_hash","");
        user_email = loginInfoPreferences.getString("user_email","");

        getUserData().setUserName(user_name);
        getUserData().setUserHash(user_hash+"");
        getUserData().setUserEmail(user_email);

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("userHash",user_hash);
            jsonObject.accumulate("userEmail",user_email);
            jsonObject.accumulate("userName",user_name);

            new PostNetworkManager("/users",jsonObject) {
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
                        if (responseJson.getString("result").contains("success")) {
                            googleLoginStep3();
                        }else{
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
        new GetNetworkManager("/users/"+user_hash) {
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
                    if (responseJson.getString("result").contains("success")) {
                        Log.d(TAG,"로그인성공");
                        JSONArray jsonArray = new JSONArray(responseJson.getJSONArray("user").toString());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        int userId = jsonObject.getInt("id");
                        loginSuccess(userId);
                    }else{
                        Log.d(TAG,"로그인실패");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    public void loginSuccess(int userid){
        getUserData().setUserID(userid);
        Intent subscribeActivity = new Intent(this, SubscibeActivity.class);
        startActivity(subscribeActivity);
    }
}
