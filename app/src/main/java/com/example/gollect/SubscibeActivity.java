package com.example.gollect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gollect.adapter.SubscribeRecyclerviewAdapter;
import com.example.gollect.utility.GetNetworkManager;
import com.example.gollect.utility.PostNetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubscibeActivity extends AppCompatActivity {

    String user_id, platform_id, keyword;

    private String TAG = "SubscibeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        //전체 Diary 목록을 Realm에 요청해서 받아오는 코드입니다

        getPlatforms();
    }

    public void getPlatforms() {
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

                        new android.app.AlertDialog.Builder(SubscibeActivity.this)
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
                        ArrayList<Integer> idList = new ArrayList<>();
                        ArrayList<String> nameList = new ArrayList<>();
                        ArrayList<String> urlList = new ArrayList<>();
                        for (int i=0; i<responseJson.getJSONArray("platforms").length(); i++) {
                            nameList.add(responseJson.getJSONArray("platforms").getJSONObject(i).getString("name")) ;
                        }
                        Log.d(TAG,responseJson.getJSONArray("platforms").getJSONObject(1).getString("name"));
                        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
                        RecyclerView recyclerView = findViewById(R.id.rv_platform) ;
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        recyclerView.setLayoutManager(new LinearLayoutManager(SubscibeActivity.this)) ;

                        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
                        SubscribeRecyclerviewAdapter adapter = new SubscribeRecyclerviewAdapter(SubscibeActivity.this, nameList) ;
                        recyclerView.setAdapter(adapter) ;
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

            new PostNetworkManager(" /subscriptions/users/:user_id",jsonObject) {
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

                            new android.app.AlertDialog.Builder(SubscibeActivity.this)
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

    public void subscribeSuccess(){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}
