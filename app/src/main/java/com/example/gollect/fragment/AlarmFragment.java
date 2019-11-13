package com.example.gollect.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gollect.R;
import com.example.gollect.adapter.AlarmRecyclerviewAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmFragment extends Fragment {

    public AlarmFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alarm, container, false);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }

        RecyclerView recyclerView = v.findViewById(R.id.rv_alarm) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext())) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        AlarmRecyclerviewAdapter adapter = new AlarmRecyclerviewAdapter(list) ;
        recyclerView.setAdapter(adapter) ;
        return v;
    }
}
