package com.example.gollect.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gollect.R;
import com.example.gollect.item.TextContentsItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BmViewAdapter extends  RecyclerView.Adapter<BmViewAdapter.BmViewHolder> {

    private ArrayList<TextContentsItem> items = new ArrayList<>();

    public class BmViewHolder extends RecyclerView.ViewHolder {

        private TextView image;
        private TextView title;
        private TextView contents;

        public BmViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView1);
            title = itemView.findViewById(R.id.textView1);
            contents = itemView.findViewById(R.id.textView2);

        }
    }
    @NonNull
    @Override
    public BmViewAdapter.BmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_contents_view,parent,false);

        BmViewHolder bmViewHolder = new BmViewHolder(view);

        return bmViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BmViewAdapter.BmViewHolder holder, int position) {
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
    public void addItem(String platformId, String title, String desc) {
        TextContentsItem item = new TextContentsItem();

        item.setPlatformId(platformId);
        item.setTitle(title);
        item.setSummary(desc);

        items.add(item);
    }

 */
}
