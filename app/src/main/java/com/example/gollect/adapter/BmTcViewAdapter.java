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
import com.example.gollect.item.BmTextContentsItem;
import com.example.gollect.item.TextContentsItem;
import com.example.gollect.utility.DeleteNetworkManager;
import com.example.gollect.utility.PostNetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BmTcViewAdapter extends  RecyclerView.Adapter<BmTcViewAdapter.BmViewHolder> {

    private List<BmTextContentsItem> items;
    private Context context;
    private Date date;
    private SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private String getTime;

    public BmTcViewAdapter(List<BmTextContentsItem> listitems, Context context){
        this.items = listitems;
        this.context = context;
    }

    public class BmViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener, View.OnClickListener, MenuItem.OnMenuItemClickListener{

        private ImageView platform;
        private TextView title;
        private TextView summary;
        private TextView upload_time;
        private ImageView imageView;

        public BmViewHolder(@NonNull View itemView) {
            super(itemView);
            platform = itemView.findViewById(R.id.platform);
            title = itemView.findViewById(R.id.title);
            summary = itemView.findViewById(R.id.summary);
            upload_time = itemView.findViewById(R.id.uploaded_time);
            imageView = itemView.findViewById(R.id.imageSrc);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    BmTextContentsItem bmTextContentsitem = items.get(position);
                    String url = bmTextContentsitem.getUrl();
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
                    Integer textContentId = items.get(getAdapterPosition()).getTextContentId();
                    deleteBookmark(textContentId,getAdapterPosition());
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
    @NonNull
    @Override
    public BmTcViewAdapter.BmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_contents_view,parent,false);

        BmViewHolder bmViewHolder = new BmViewHolder(view);

        return bmViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BmTcViewAdapter.BmViewHolder holder, int position) {
        BmTextContentsItem item = items.get(position);

        long now = System.currentTimeMillis();
        date = new Date(now);
        getTime = simpleDateFormat.format(date);

        String month  = item.getUploaded_at().substring(5,7);
        String day  = item.getUploaded_at().substring(8,10);
        String date = month + "/" + day;
        String date2 = month + "-" + day;
        String time = item.getUploaded_at().substring(11,16);
        String currentDate = getTime.substring(5,10);

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
        holder.title.setText(item.getTitle());
        holder.summary.setText(item.getSummary());
        if(date2.equals(currentDate)) holder.upload_time.setText(time);
        else holder.upload_time.setText(date);

        Glide.with(holder.itemView.getContext())
                .load(item.getImg_src())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void deleteBookmark(int id, int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, items.size());  // 아이템 삭제시 즉시 삭제 처리 되는 코드 3줄
        int textcontentid = id;
        BaseActivity baseActivity = new BaseActivity();
        int userID = baseActivity.getUserData().getUserID();

        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("hi",textcontentid);

            new DeleteNetworkManager("/bookmarks/users/"+userID+"/contents/text/"+textcontentid,jsonObject) {
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
