package com.example.gollect;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gollect.adapter.DifferentRowAdapter;
import com.example.gollect.adapter.SubscribeRecyclerviewAdapter;
import com.example.gollect.utility.DeleteNetworkManager;
import com.example.gollect.utility.GetNetworkManager;
import com.example.gollect.utility.PostNetworkManager;
import com.wefika.flowlayout.FlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubscribeActivity extends AppCompatActivity {

    String user_id, platform_id, keyword;

    private String TAG = "SubscribeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        Button completeBt = findViewById(R.id.completeBt);
        Button allKeywordBt = findViewById(R.id.allkeywordBt);
        allKeywordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SubscribeActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_dialog,null);

                builder.setView(dialogView);

                Button one = (Button) dialogView.findViewById(R.id.button1);
                Button three = (Button) dialogView.findViewById(R.id.button3);
                final EditText enterKeyword = (EditText)dialogView.findViewById(R.id.enterKeyword);

                final AlertDialog dialog = builder.create();


                one.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });

                three.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        keyword = enterKeyword.getText().toString();
                        enterKeyword.setText("");
                        getPlatfromList(2);
                    }
                });
                dialog.setCanceledOnTouchOutside(false);

                // Display the custom alert dialog on interface
                dialog.show();
            }
        });

        completeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeSuccess();
            }
        });

        getPlatfromList(1);
    }

    public void getPlatforms(final ArrayList<PlatformData> subsList) {
        JSONObject jsonObject = new JSONObject();
        new GetNetworkManager("/platforms") {
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

                        new android.app.AlertDialog.Builder(SubscribeActivity.this)
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
                        int current = 0;
                        ArrayList<PlatformData> nameList = new ArrayList<>();
                        ArrayList<String> groupList = getGroupLIst();
                        String PlatformName, ImageUrl;
                        int PlatformId;
                        boolean subscribe;
                        for(int j =0;j<groupList.size();j++) {
                            for (int i = 0; i < responseJson.getJSONArray("platforms").length(); i++) {
                                if (i==0) {
                                    nameList.add(new PlatformData(groupList.get(current),null,-1,PlatformData.HEADER_TYPE));
                                    current += 1;
                                }
                                if (current == responseJson.getJSONArray("platforms").getJSONObject(i).getInt("domain_id")) {
                                    PlatformName = responseJson.getJSONArray("platforms").getJSONObject(i).getString("name");
                                    ImageUrl = responseJson.getJSONArray("platforms").getJSONObject(i).getString("logo_url");
                                    PlatformId = responseJson.getJSONArray("platforms").getJSONObject(i).getInt("id");
                                    nameList.add(new PlatformData(PlatformName, ImageUrl, PlatformId,PlatformData.CHILD_TYPE));
                                    //nameList.add(responseJson.getJSONArray("platforms").getJSONObject(i).getString("name"));
                                }
                            }
                        }
                        Log.d(TAG,responseJson.getJSONArray("platforms").getJSONObject(1).getString("name"));

                        SubscribeRecyclerviewAdapter adapter = new SubscribeRecyclerviewAdapter(SubscribeActivity.this, nameList, subsList);

                        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SubscribeActivity.this, OrientationHelper.VERTICAL, false);
                        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_platform);
                        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setAdapter(adapter);
                    }else{
                        Log.d(TAG,responseJson.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public void postPlatforms(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("userId",user_id);
            jsonObject.accumulate("platformId",platform_id);
            jsonObject.accumulate("keyword",keyword);

            new PostNetworkManager(" /subscriptions/users/",jsonObject) {
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

                            new android.app.AlertDialog.Builder(SubscribeActivity.this)
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
                            subscribeSuccess();

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

    public void deletePlatform(int user_id, int platform_id){


        JSONObject jsonObject = new JSONObject();
        new DeleteNetworkManager("/platforms/" + platform_id +"/users/"+ 23, jsonObject) {
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

                        new android.app.AlertDialog.Builder(SubscribeActivity.this)
                                .setTitle(Resources.getSystem().getString(R.string.network_err_msg))
                                .setPositiveButton(Resources.getSystem().getString(R.string.ok), exitListener)
                                .setCancelable(false)
                                .show();
                    }
                });
            }

            @Override
            public void responseCallback(JSONObject responseJson) {
                try {
                    if (responseJson.getString("result").contains("success")) {
                        Log.d(TAG, "플랫폼 구독 취소 성공");
                    }else{
                        Log.d(TAG, "플랫폼 구독 취소 실패");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public ArrayList<String> getKeyword(int platform_number) {
        BaseActivity baseActivity = new BaseActivity();
        int user_id = 23;
        JSONObject jsonObject = new JSONObject();
        final ArrayList<String> idList = new ArrayList<>();

        //플랫폼 아이디 추가해야함
        Log.d("Dawoon","/subscriptions/users/"+user_id +"/platforms/"+platform_number);
        new GetNetworkManager("/subscriptions/users/"+user_id +"/platforms/"+platform_number) {
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

                        new android.app.AlertDialog.Builder(SubscribeActivity.this)
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
                        Log.d(TAG,responseJson.toString());

//                        ArrayList<String> nameList = new ArrayList<>();
//                        ArrayList<String> urlList = new ArrayList<>();
                        for (int i=0; i<responseJson.getJSONArray("keywords").length(); i++) {
                            idList.add(responseJson.getJSONArray("keywords").getJSONObject(i).getString("keyword")) ;
                        }
                    }else{
                        Log.d(TAG,responseJson.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
        return idList;
    }

    public void postAddKeyword(int user_id, int platform_id, String keyword){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("userId",user_id);
            jsonObject.accumulate("platformId",platform_id);
            jsonObject.accumulate("keyword",keyword);
            Log.d(TAG,jsonObject.toString());
            new PostNetworkManager("/subscriptions/users/23",jsonObject) {
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

                            new android.app.AlertDialog.Builder(SubscribeActivity.this)
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
                            Log.d(TAG,"키워드 추가 성공");
                        }else{
                            Log.d(TAG,"키워드 추가 실패");
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

    public void deleteKeyword(int user_id, int platform_id, String keyword){


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("keyword",keyword);
        new DeleteNetworkManager("/subscriptions/users/" + 23 + "/platforms/" + platform_id + "/",jsonObject) {
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

                            new android.app.AlertDialog.Builder(SubscribeActivity.this)
                                    .setTitle(Resources.getSystem().getString(R.string.network_err_msg))
                                    .setPositiveButton(Resources.getSystem().getString(R.string.ok), exitListener)
                                    .setCancelable(false)
                                    .show();
                        }
                    });
                }

                @Override
                public void responseCallback(JSONObject responseJson) {
                    try {
                        if (responseJson.getString("result").contains("success")) {
                            Log.d(TAG, "삭제 성공");
                        }else{
                            Log.d(TAG, "삭제 실패");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getPlatfromList(final int type){
        new GetNetworkManager("/subscriptions/users/" + 23) {
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

                        new android.app.AlertDialog.Builder(SubscribeActivity.this)
                                .setTitle(getString(R.string.network_err_msg))
                                .setPositiveButton(getString(R.string.ok), exitListener)
                                .setCancelable(false)
                                .show();
                    }
                });
            }

            @Override
            public void responseCallback(JSONObject responseJson) {
                ArrayList<PlatformData> subsList = new ArrayList<>();
                try {
                    if (responseJson.getString("result").contains("success")) {
                        int current = 0;
                        for (int i=0; i<responseJson.getJSONArray("subscriptions").length(); i++) {
                            if(current != responseJson.getJSONArray("subscriptions").getJSONObject(i).getInt("platform_id")) {
                                current = responseJson.getJSONArray("subscriptions").getJSONObject(i).getInt("platform_id");
                                PlatformData platform = new PlatformData(responseJson.getJSONArray("subscriptions").getJSONObject(i).getInt("platform_id"), true);
                                subsList.add(platform);
                            }
                        }
                        switch(type){
                            case 1:
                                getPlatforms(subsList);
                            case 2:
                                for(int i =0;i<subsList.size();i++) {
                                    postAddKeyword(23, subsList.get(i).getPlatformId(),keyword);
                                }
                        }
                        Log.d(TAG, subsList.toString());
                    }else{
                        Log.d(TAG,responseJson.getString("result"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }


    public ArrayList<String> getGroupLIst(){
        ArrayList<String> groupList = new ArrayList<String>();
        groupList.add("Youtube");
        groupList.add("네이버티비");
        groupList.add("카카오티비");
        groupList.add("dcinside");
        groupList.add("inven");
        groupList.add("아주대");
        groupList.add("중앙일보");
        groupList.add("연합뉴스");
        return  groupList;
    }
    public void subscribeSuccess(){
        Intent mainActivity = new Intent(this, MainActivity.class);
        getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(mainActivity);
    }
}
