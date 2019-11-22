package com.example.gollect.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gollect.R;
import com.example.gollect.adapter.VcViewAdapter;
import com.example.gollect.item.SampleData;
import com.example.gollect.item.VideoContentsItem;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    private VcViewAdapter adapter;
    private ArrayList<VideoContentsItem> items = new ArrayList<>();


    public VideoFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        adapter = new VcViewAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setItems(new SampleData().getItems());

        return view;
    }

}
