package com.example.gollect.adapter;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gollect.R;
import com.example.gollect.item.VideoContentsItem;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class VcViewAdapter extends RecyclerView.Adapter<VcViewAdapter.ViewHolder> {

    private ArrayList<VideoContentsItem> items = new ArrayList<>();

    @Override
    public VcViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_contents_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VcViewAdapter.ViewHolder viewHolder, int position) {

        VideoContentsItem item = items.get(position);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getUrl())
                .into(viewHolder.ivMovie);

        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvContent.setText(item.getContent());
        viewHolder.tvGenre.setText(item.getGenre());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<VideoContentsItem> items) {
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener, View.OnClickListener, MenuItem.OnMenuItemClickListener{

        ImageView ivMovie;
        TextView tvTitle, tvContent, tvGenre;

        ViewHolder(View itemView) {
            super(itemView);

            ivMovie = itemView.findViewById(R.id.video_thumnail_src);

            tvTitle = itemView.findViewById(R.id.video_title);
            tvContent = itemView.findViewById(R.id.video_duration);
            tvGenre = itemView.findViewById(R.id.video_uploaded_at);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    VideoContentsItem videoContentsitem = items.get(position);
                    String titleStr = videoContentsitem.getTitle();
                    Log.d("jaejin",titleStr);
                }
            });

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem bookmark = menu.add(Menu.NONE,R.id.bookmark_menu,1,"즐겨찾기");
            bookmark.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();

            switch (id){
                case R.id.bookmark_menu:
                    String titleStr = items.get(getAdapterPosition()).getTitle();
                    Log.d("jaejin",titleStr+"짱짱");
                    break;
            }
            return true;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
