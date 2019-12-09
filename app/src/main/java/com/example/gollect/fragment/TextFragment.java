package com.example.gollect.fragment;

import android.content.DialogInterface;
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
import com.example.gollect.adapter.TcViewAdapter;
import com.example.gollect.item.TextContentsItem;
import com.example.gollect.utility.GetNetworkManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TextFragment extends Fragment{

    private TcViewAdapter adapter;
    private List<TextContentsItem> items;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public TextFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));

        items = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getTextContents();

        swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                items.clear();
                getTextContents();

                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void getTextContents(){
        BaseActivity baseActivity = new BaseActivity();
        int userID = baseActivity.getUserData().getUserID();
        new GetNetworkManager("/contents/text/users/"+userID) {
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
                jsonArray = new JSONArray(json.getJSONArray("textContents").toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int textContentId = jsonObject.getInt("textContentId");
                    String platformId = jsonObject.getString("platform_id");
                    String title = jsonObject.getString("title");
                    String summary = jsonObject.getString("abstract");
                    String url = jsonObject.getString("url");
                    String imgSrc = jsonObject.getString("img_src");
                    String temp_uploaded_at = jsonObject.getString("uploaded_at");
                    int domainId = jsonObject.getInt("domain_id");

                    TextContentsItem item = new TextContentsItem(
                            textContentId,
                            platformId,
                            title,
                            summary,
                            url,
                            imgSrc,
                            temp_uploaded_at,
                            domainId
                    );
                    items.add(item);
                }
                adapter = new TcViewAdapter(items, getActivity().getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
