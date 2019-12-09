package com.example.gollect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.gollect.adapter.NoticeViewAdapter;
import com.example.gollect.adapter.TcViewAdapter;
import com.example.gollect.item.NoticeItem;
import com.example.gollect.item.TextContentsItem;
import com.example.gollect.utility.BackPressCloseHandler;
import com.example.gollect.utility.GetNetworkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends BaseActivity {

    private BackPressCloseHandler backPressCloseHandler;
    private NoticeViewAdapter adapter;
    private List<NoticeItem> items;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        backPressCloseHandler = new BackPressCloseHandler(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_gollect_light_24dp);
        getSupportActionBar().setTitle("공지사항");
        toolbar.setTitleTextColor(Color.BLACK);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 1));

        items = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getNoticeContents();
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
    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }

    private void getNoticeContents(){
        new GetNetworkManager("/notices") {
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

                        new android.app.AlertDialog.Builder(NoticeActivity.this)
                                .setTitle(getString(R.string.network_err_msg))
                                .setPositiveButton(getString(R.string.ok), exitListener)
                                .setCancelable(false)
                                .show();
                    }
                });
            }

            @Override
            public void responseCallback(JSONObject responseJson) {
                Log.d("jaejin",responseJson.toString());
                setContents(responseJson);
            }
        }.execute();
    }
    private void setContents(JSONObject json){
        try{
            JSONArray jsonArray;
            if(json != null) {
                jsonArray = new JSONArray(json.getJSONArray("notices").toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String notice_id = jsonObject.getString("notice_id");
                    String title = jsonObject.getString("title");
                    String content = jsonObject.getString("content");

                    NoticeItem item = new NoticeItem(
                            notice_id,
                            title,
                            content
                    );
                    items.add(item);
                }
                adapter = new NoticeViewAdapter(items, this);
                recyclerView.setAdapter(adapter);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
