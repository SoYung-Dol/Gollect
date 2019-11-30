package com.example.gollect.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.gollect.item.TextContentsItem;
import com.example.gollect.item.VideoContentsItem;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class VcViewAdapter extends RecyclerView.Adapter<VcViewAdapter.ViewHolder> {

    private List<VideoContentsItem> items;
    private Context context;

    public VcViewAdapter(List<VideoContentsItem> listitems, Context context){
        this.items = listitems;
        this.context = context;
    }

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

        viewHolder.video_title.setText(item.getTitle());
        viewHolder.video_duration.setText(item.getDuration());
        viewHolder.video_uploaded_at.setText(item.getUploaded_at());
        Glide.with(viewHolder.itemView.getContext())
                .load(item.getThumbnail_src())
                .into(viewHolder.ivMovie);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener, View.OnClickListener, MenuItem.OnMenuItemClickListener{

        ImageView ivMovie;
        TextView video_title, video_duration, video_uploaded_at;

        ViewHolder(View itemView) {
            super(itemView);

            ivMovie = itemView.findViewById(R.id.video_thumnail_src);

            video_title = itemView.findViewById(R.id.video_title);
            video_duration = itemView.findViewById(R.id.video_duration);
            video_uploaded_at = itemView.findViewById(R.id.video_uploaded_at);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    VideoContentsItem videoContentsitem = items.get(position);
                    String url = videoContentsitem.getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
