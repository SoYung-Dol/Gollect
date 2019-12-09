package com.example.gollect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gollect.utility.BackPressCloseHandler;
import com.example.gollect.utility.PostNetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestPlatformActivity extends BaseActivity {

    private BackPressCloseHandler backPressCloseHandler;
    private Button btn;
    EditText edit_title;
    EditText edit_content;
    String title;
    String content;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_platform);

        backPressCloseHandler = new BackPressCloseHandler(this);
        btn = (Button) findViewById(R.id.submit_btn);
        btn.setOnClickListener(this);
        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_content = (EditText) findViewById(R.id.edit_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_gollect_light_24dp);
        getSupportActionBar().setTitle("플랫폼 신청");
        toolbar.setTitleTextColor(Color.BLACK);

    }

    @Override
    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.submit_btn:
                submit_request();
                break;
        }
    }

    public void submit_request(){
        if(edit_title.getText().toString().replace(" ","").equals("") || edit_content.getText().toString().replace(" ","").equals("")){
            toast = Toast.makeText(this, "빈 칸을 채워주세요",Toast.LENGTH_SHORT);
            toast.show();
        }else{
            title = edit_title.getText().toString();
            content = edit_content.getText().toString();
            request_platform();
        }
    }
    public void request_platform(){
        BaseActivity baseActivity = new BaseActivity();
        int userID = baseActivity.getUserData().getUserID();

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id",userID);
            jsonObject.accumulate("title",title);
            jsonObject.accumulate("content",content);

            new PostNetworkManager("/requests",jsonObject) {
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

                            new android.app.AlertDialog.Builder(RequestPlatformActivity.this)
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
                            request_success();
                        }else{
                            request_fail();
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
    public void request_success(){
        edit_content.setText("");
        edit_title.setText("");
        toast = Toast.makeText(this, "요청 완료",Toast.LENGTH_SHORT);
        toast.show();
    }
    public void request_fail(){
        edit_content.setText("");
        edit_title.setText("");
        toast = Toast.makeText(this, "요청 실패",Toast.LENGTH_SHORT);
        toast.show();
    }
}
