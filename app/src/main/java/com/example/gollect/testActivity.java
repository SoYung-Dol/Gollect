package com.example.gollect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gollect.utility.NetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

public class testActivity extends BaseActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.btn:
                test();
                break;
        }
    }
    public void test(){
        try {
            JSONObject json = new JSONObject();
            json.put("mode", "se_id");
            json.put("userid", "jj");

            new NetworkManager("/", json) {
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

                            new android.app.AlertDialog.Builder(testActivity.this)
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
                        Log.d("jaejin",responseJson.toString());
                        if (responseJson.getInt("result") == 1) {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.sendJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
