package com.example.gollect.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gollect.AlarmActivity;
import com.example.gollect.BaseActivity;
import com.example.gollect.LoginActivity;
import com.example.gollect.R;
import com.example.gollect.RequestPlatformActivity;
import com.example.gollect.SubscribeActivity;
import com.example.gollect.item.SettingContentsItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StViewAdapter extends RecyclerView.Adapter<StViewAdapter.StViewHolder> {
    private ArrayList<SettingContentsItem> items = new ArrayList<>();
    private Context context;
    public StViewAdapter(Context context){
        this.context = context;
    }

    public class StViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView img;

        public StViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            img = itemView.findViewById(R.id.img_src);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    //구독정보설정
                    if(position == 0) {
                        context.startActivity(new Intent(context, SubscribeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    else if(position == 1) { //알림키워드설정
                        context.startActivity(new Intent(context, AlarmActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else if(position == 2) { //플랫폼 신청
                        context.startActivity(new Intent(context, RequestPlatformActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else if(position == 3){ //로그아웃
                        new BaseActivity().googleSignout();
                        context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public StViewAdapter.StViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_contents_view,parent,false);

        StViewAdapter.StViewHolder stViewHolder = new StViewAdapter.StViewHolder(view);

        return stViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StViewAdapter.StViewHolder holder, int position) {
        SettingContentsItem item = items.get(position);

        holder.title.setText(item.getTitle());
        holder.img.setImageDrawable(item.getIcon());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Drawable icon, String title) {
        SettingContentsItem item = new SettingContentsItem();

        item.setIcon(icon);
        item.setTitle(title);

        items.add(item);
    }
}
