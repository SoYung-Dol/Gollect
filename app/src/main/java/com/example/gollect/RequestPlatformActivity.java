package com.example.gollect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.gollect.utility.BackPressCloseHandler;

public class RequestPlatformActivity extends BaseActivity {

    private BackPressCloseHandler backPressCloseHandler;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_platform);

        backPressCloseHandler = new BackPressCloseHandler(this);
        btn = (Button) findViewById(R.id.submit_btn);
        btn.setOnClickListener(this);

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
        Log.d("jaejin","hi");
    }
}
