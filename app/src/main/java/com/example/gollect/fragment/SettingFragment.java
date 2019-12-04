package com.example.gollect.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gollect.R;
import com.example.gollect.adapter.StViewAdapter;

public class SettingFragment extends Fragment {

    private StViewAdapter adapter;

    public SettingFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        adapter = new StViewAdapter(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_subscriptions_red_24dp), "내 구독 정보 설정");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_notifications_24dp), "알림 키워드 설정");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_add_platform_24dp),"플랫폼 신청");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_lock_open_gray24dp), "로그아웃");
        return view;
    }
}
