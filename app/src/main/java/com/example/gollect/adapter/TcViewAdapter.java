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

public class TcViewAdapter extends  RecyclerView.Adapter<TcViewAdapter.TcViewHolder> {

    private ArrayList<TextContentsItem> items = new ArrayList<>();

    public class TcViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
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

        holder.image.setImageDrawable(item.getIcon());
        holder.title.setText(item.getTitle());
        holder.contents.setText(item.getSub_title());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Drawable icon, String title, String desc) {
        TextContentsItem item = new TextContentsItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setSub_title(desc);

        items.add(item);
    }
}
