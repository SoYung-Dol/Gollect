package com.example.gollect.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gollect.R;
import com.example.gollect.item.NoticeItem;
import com.example.gollect.item.TextContentsItem;
import com.example.gollect.item.VideoContentsItem;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeViewAdapter extends RecyclerView.Adapter<NoticeViewAdapter.NotiViewHolder>  {

    private List<NoticeItem> items;
    private Context context;

    public NoticeViewAdapter(List<NoticeItem> listitems, Context context){
        this.items = listitems;
        this.context = context;
    }
    public class NotiViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView id;

        public NotiViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notice_title);
            id = itemView.findViewById(R.id.notice_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    NoticeItem noticeitem = items.get(position);
                    String content = noticeitem.getContent();
                }
            });
        }
    }

    @NonNull
    @Override
    public NoticeViewAdapter.NotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_contents_view,parent,false);

        NoticeViewAdapter.NotiViewHolder noticeViewHolder = new NoticeViewAdapter.NotiViewHolder(view);

        return noticeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter.NotiViewHolder holder, int position) {
        NoticeItem item = items.get(position);

        holder.id.setText(item.getId());
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
