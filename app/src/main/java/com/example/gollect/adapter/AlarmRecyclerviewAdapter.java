package com.example.gollect.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gollect.AlarmData;
import com.example.gollect.R;

import java.util.ArrayList;
import java.util.Date;

public class AlarmRecyclerviewAdapter extends RecyclerView.Adapter<AlarmRecyclerviewAdapter.ViewHolder> {

    private ArrayList<String> nameData = null ;
    private ArrayList<String> contentsData = null ;
    private ArrayList<Integer> residData = null ;
    private ArrayList<String> respackageData = null ;
    private ArrayList<String> dateData = null;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName ;
        TextView time;
        TextView contents;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            appIcon = itemView.findViewById(R.id.app_logo);
            appName = itemView.findViewById(R.id.app_name);
            time = itemView.findViewById(R.id.timestamp);
            contents = itemView.findViewById(R.id.alarm_contents);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public AlarmRecyclerviewAdapter(ArrayList<String> nameList, ArrayList<String> contentsList, ArrayList<Integer> resIdList, ArrayList<String> resPackageList, ArrayList<String> dateList) {
        nameData = nameList;
        contentsData = contentsList;
        residData = resIdList;
        respackageData = resPackageList;
        dateData = dateList;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public AlarmRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_alarm, parent, false) ;
        AlarmRecyclerviewAdapter.ViewHolder vh = new AlarmRecyclerviewAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(AlarmRecyclerviewAdapter.ViewHolder holder, int position) {
        String appName = nameData.get(position) ;
        String contents = contentsData.get(position);
        String resPackage = respackageData.get(position);
        String date = dateData.get(position);
        int resId = residData.get(position);

        Icon icon = null;
        holder.appName.setText(appName) ;
        holder.contents.setText(contents);
        holder.time.setText(date);
        Log.d("ICON",resPackage + " " +resId);
        holder.appIcon.setImageIcon(icon.createWithResource(resPackage, resId));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return nameData.size() ;
    }
}