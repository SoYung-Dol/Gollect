package com.example.gollect.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gollect.R;
import com.example.gollect.adapter.BmTcViewAdapter;


public class TextBookmarkFragment extends Fragment {

    private BmTcViewAdapter adapter;
    public TextBookmarkFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_textbookmark, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),1));

        adapter = new BmTcViewAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

/*
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
*/
        return view;
    }
}
