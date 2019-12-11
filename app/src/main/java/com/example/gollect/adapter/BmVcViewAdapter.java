package com.example.gollect.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
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
import com.example.gollect.BaseActivity;
import com.example.gollect.MainActivity;
import com.example.gollect.R;
import com.example.gollect.item.BmVideoContentsItem;
import com.example.gollect.utility.DeleteNetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BmVcViewAdapter extends RecyclerView.Adapter<BmVcViewAdapter.BmVcViewHolder>  {

    private List<BmVideoContentsItem> items;
    private Context context;
    private Date date;
    private SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private String getTime;

    public BmVcViewAdapter(List<BmVideoContentsItem> items, Context context){
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public BmVcViewAdapter.BmVcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_contents_view,parent,false);

        BmVcViewAdapter.BmVcViewHolder bmVcViewHolder = new BmVcViewAdapter.BmVcViewHolder(view);

        return bmVcViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BmVcViewAdapter.BmVcViewHolder holder, int position) {
        BmVideoContentsItem item = items.get(position);

        long now = System.currentTimeMillis();
        date = new Date(now);
        getTime = simpleDateFormat.format(date);

        String month  = item.getUploaded_at().substring(5,7);
        String day  = item.getUploaded_at().substring(8,10);
        String date = month + "/" + day;
        String date2 = month + "-" + day;
        String time = item.getUploaded_at().substring(11,16);
        String currentDate = getTime.substring(5,10);

        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.ivMovie);

        if(item.getDomainId() == 1){
            holder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_youtube));
        }else if(item.getDomainId() == 2){
            holder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_navertv));
        }else if(item.getDomainId() == 3){
            holder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_kakaotv));
        }else if(item.getDomainId() == 4){
            holder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_dc));
        }else if(item.getDomainId() == 5){
            holder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_inven));
        }else if(item.getDomainId() == 6){
            holder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_ajou));
        }else if(item.getDomainId() == 7){
            holder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_jungang));
        }else if(item.getDomainId() == 8){
            holder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_yonhap));
        }
        holder.video_title.setText(item.getTitle());
        holder.video_duration.setText(item.getDuration());
        if(date2.equals(currentDate)) holder.video_uploaded_at.setText(time);
        else holder.video_uploaded_at.setText(date);
        Glide.with(holder.itemView.getContext())
                .load(item.getThumbnail_src())
                .into(holder.ivMovie);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class BmVcViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener, View.OnClickListener, MenuItem.OnMenuItemClickListener{

        ImageView ivMovie, platform;
        TextView video_title, video_duration, video_uploaded_at;

        public BmVcViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovie = itemView.findViewById(R.id.video_thumnail_src);
            platform = itemView.findViewById(R.id.platform);
            video_title = itemView.findViewById(R.id.video_title);
            video_duration = itemView.findViewById(R.id.video_duration);
            video_uploaded_at = itemView.findViewById(R.id.video_uploaded_at);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    BmVideoContentsItem videoContentsitem = items.get(position);
                    String url = videoContentsitem.getUrl();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            });

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();

            switch (id){
                case R.id.delete_menu:
                    if(getAdapterPosition() >= 0) {
                        int videoContentId = items.get(getAdapterPosition()).getVideoContentId();
                        deleteBookmark(videoContentId, getAdapterPosition());
                    }
                    break;
            }
            return true;
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem bookmark = menu.add(Menu.NONE,R.id.delete_menu,1,"즐겨찾기 삭제");
            bookmark.setOnMenuItemClickListener(this);
        }
    }

    private void deleteBookmark(int id, int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());
        int videocontentid = id;
        BaseActivity baseActivity = new BaseActivity();
        int userID = baseActivity.getUserData().getUserID();

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("hi",videocontentid);

            new DeleteNetworkManager("/bookmarks/users/"+userID+"/contents/video/"+videocontentid,jsonObject) {
                @Override
                public void errorCallback(int status) {
                    super.errorCallback(status);

                    ((MainActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogInterface.OnClickListener exitListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((MainActivity)context).finishAffinity();
                                    System.runFinalization();
                                    System.exit(0);
                                    dialog.dismiss();
                                }
                            };

                            new android.app.AlertDialog.Builder(context)
                                    .setTitle(Resources.getSystem().getString(R.string.network_err_msg))
                                    .setPositiveButton(Resources.getSystem().getString(R.string.ok), exitListener)
                                    .setCancelable(false)
                                    .show();
                        }
                    });
                }

                @Override
                public void responseCallback(JSONObject responseJson) {
                    try {
                        if (responseJson.getString("result").contains("success")) {

                        }else{
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
