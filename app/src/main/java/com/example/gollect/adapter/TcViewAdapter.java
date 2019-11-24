package com.example.gollect.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gollect.R;
import com.example.gollect.item.TextContentsItem;
import com.example.gollect.item.VideoContentsItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TcViewAdapter extends  RecyclerView.Adapter<TcViewAdapter.TcViewHolder> {

    private List<TextContentsItem> items;
    private Context context;

    public TcViewAdapter(List<TextContentsItem> listitems, Context context){
        this.items = listitems;
        this.context = context;
    }
    public class TcViewHolder extends RecyclerView.ViewHolder {

        private TextView image;
        private TextView title;
        private TextView contents;

        public TcViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView1);
            title = itemView.findViewById(R.id.textView1);
            contents = itemView.findViewById(R.id.textView2);
        }
    }
    @NonNull
    @Override
    public TcViewAdapter.TcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_contents_view,parent,false);

        TcViewAdapter.TcViewHolder tcViewHolder = new TcViewAdapter.TcViewHolder(view);

        return tcViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TcViewAdapter.TcViewHolder holder, int position) {
        TextContentsItem item = items.get(position);

        holder.image.setText(item.getPlatformId());
        holder.title.setText(item.getTitle());
        holder.contents.setText(item.getSummary());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
/*
    public void addItem(String platformId, String title, String summary) {
        TextContentsItem item = new TextContentsItem();

        item.setPlatformId(platformId);
        item.setTitle(title);
        item.setSummary(summary);

        items.add(item);
    }

 */
}
