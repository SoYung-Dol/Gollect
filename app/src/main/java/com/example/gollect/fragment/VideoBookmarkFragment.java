package com.example.gollect.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gollect.BaseActivity;
import com.example.gollect.R;
import com.example.gollect.adapter.BmTcViewAdapter;
import com.example.gollect.adapter.BmVcViewAdapter;
import com.example.gollect.adapter.VcViewAdapter;
import com.example.gollect.item.BmTextContentsItem;
import com.example.gollect.item.BmVideoContentsItem;
import com.example.gollect.item.VideoContentsItem;
import com.example.gollect.utility.GetNetworkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoBookmarkFragment extends Fragment {

    private BmVcViewAdapter adapter;
    private RecyclerView recyclerView;
    private List<BmVideoContentsItem> items;
    private SwipeRefreshLayout swipeRefreshLayout;

    public VideoBookmarkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_bookmark, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),1));

        items = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getBmVideoContents();

        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                items.clear();
                getBmVideoContents();

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void getBmVideoContents(){
        new GetNetworkManager("/bookmarks/users/"+23+"/contents/video") {
            @Override
            public void errorCallback(int status) {
                super.errorCallback(status);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogInterface.OnClickListener exitListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finishAffinity();
                                System.runFinalization();
                                System.exit(0);
                                dialog.dismiss();
                            }
                        };

                        new android.app.AlertDialog.Builder(getActivity())
                                .setTitle(getString(R.string.network_err_msg))
                                .setPositiveButton(getString(R.string.ok), exitListener)
                                .setCancelable(false)
                                .show();
                    }
                });
            }

            @Override
            public void responseCallback(JSONObject responseJson) {
                setContents(responseJson);
            }
        }.execute();
    }
    private void setContents(JSONObject json){
        try{
            JSONArray jsonArray;
            if(json != null) {
                jsonArray = new JSONArray(json.getJSONArray("videoContents").toString());
                for (int i = jsonArray.length() - 1; i >= 0; i--) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Integer videoContentId = jsonObject.getInt("videoContentId");
                    String platformId = jsonObject.getString("platform_id");
                    String title = jsonObject.getString("title");
                    String thumbnail_src = jsonObject.getString("thumbnail_src");
                    String url = jsonObject.getString("url");
                    String duration = jsonObject.getString("duration");
                    String temp_uploaded_at = jsonObject.getString("uploaded_at");
                    int domainId = jsonObject.getInt("domain_id");

                    BmVideoContentsItem item = new BmVideoContentsItem(
                            videoContentId,
                            platformId,
                            title,
                            thumbnail_src,
                            url,
                            duration,
                            temp_uploaded_at,
                            domainId
                    );
                    items.add(item);
                }
                adapter = new BmVcViewAdapter(items, getActivity().getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
