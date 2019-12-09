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
import com.bumptech.glide.load.DecodeFormat;
import com.example.gollect.BaseActivity;
import com.example.gollect.MainActivity;
import com.example.gollect.R;
import com.example.gollect.item.TextContentsItem;
import com.example.gollect.item.VideoContentsItem;
import com.example.gollect.utility.PostNetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class VcViewAdapter extends RecyclerView.Adapter<VcViewAdapter.ViewHolder> {

    private List<VideoContentsItem> items;
    private Context context;
    private Date date;
    private SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    private String getTime;

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

        long now = System.currentTimeMillis();
        date = new Date(now);
        getTime = simpleDateFormat.format(date);

        String month  = item.getUploaded_at().substring(5,7);
        String day  = item.getUploaded_at().substring(8,10);
        String date = month + "/" + day;
        String date2 = month + "-" + day;
        String time = item.getUploaded_at().substring(11,16);
        String currentDate = getTime.substring(5,10);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getUrl())
                .into(viewHolder.ivMovie);

        if(item.getDomainId() == 1){
            viewHolder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_youtube));
        }else if(item.getDomainId() == 2){
            viewHolder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_navertv));
        }else if(item.getDomainId() == 3){
            viewHolder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_kakaotv));
        }else if(item.getDomainId() == 4){
            viewHolder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_dc));
        }else if(item.getDomainId() == 5){
            viewHolder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_inven));
        }else if(item.getDomainId() == 6){
            viewHolder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_ajou));
        }else if(item.getDomainId() == 7){
            viewHolder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_jungang));
        }else if(item.getDomainId() == 8){
            viewHolder.platform.setImageDrawable(context.getDrawable(R.drawable.logo_yonhap));
        }
        viewHolder.video_title.setText(item.getTitle());
        viewHolder.video_duration.setText(item.getDuration());
        if(date2.equals(currentDate)) viewHolder.video_uploaded_at.setText(time);
        else viewHolder.video_uploaded_at.setText(date);
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

        ImageView ivMovie, platform;
        TextView video_title, video_duration, video_uploaded_at;

        ViewHolder(View itemView) {
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
                    Integer videoContentId = items.get(getAdapterPosition()).getVideoContentId();
                    addBookmark(videoContentId);
                    break;
            }
            return true;
        }

        @Override
        public void onClick(View v) {

        }
    }

    private void addBookmark(int id){
        BaseActivity baseActivity = new BaseActivity();
        int userID = baseActivity.getUserData().getUserID();
        int contentId = id;

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("videoContentId",contentId);

            new PostNetworkManager("/bookmarks/users/"+userID+"/contents/video",jsonObject) {
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
