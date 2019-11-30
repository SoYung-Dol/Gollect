package com.example.gollect.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gollect.BaseActivity;
import com.example.gollect.MainActivity;
import com.example.gollect.R;
import com.example.gollect.item.SettingContentsItem;
import com.example.gollect.item.TextContentsItem;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.gollect.BaseActivity.DEBUG_LOG;

public class StViewAdapter extends RecyclerView.Adapter<StViewAdapter.StViewHolder> {
    private ArrayList<SettingContentsItem> items = new ArrayList<>();
    public static GoogleApiClient mGoogleApiClient;

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
                    if(position == 0){ //구독정보설정

                    }else if(position == 1){ //알림키워드설정

                    }else if(position == 2){ //로그아웃
                        new BaseActivity().googleSignout();
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
