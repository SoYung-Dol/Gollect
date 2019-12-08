package com.example.gollect.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gollect.PlatformData;
import com.example.gollect.R;

import com.example.gollect.SubscribeActivity;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SubscribeRecyclerviewAdapter extends RecyclerView.Adapter<SubscribeRecyclerviewAdapter.ViewHolder> {

    public static final int HEADER = 0;
    public static final int CHILD = 1;
    private ArrayList<PlatformData> mData = null ;
    private FlowLayout mHashtagContainer;
    private ArrayList<PlatformData> mSubsList = null;
    Activity activity;
    SubscribeActivity subscribeActivity = new SubscribeActivity();

// 아이템 뷰를 저장하는 뷰홀더 클래스.
public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView platformLogo;
    TextView platformName ;
    Button keywordBt;
    Button subscriveBt;
    public TextView header_title;
    public ImageView btn_expand_toggle;

    boolean status = false;

    ViewHolder(View itemView) {

        super(itemView) ;

        // 뷰 객체에 대한 참조. (hold strong reference)
        platformLogo = itemView.findViewById(R.id.platform_logo);
        platformName = itemView.findViewById(R.id.platform_name);
        subscriveBt = itemView.findViewById(R.id.subscribeBt);
        keywordBt = itemView.findViewById(R.id.keywordBt);

        subscriveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subscriveBt.getText().toString().contains("취소")){
                    subscribeActivity.deletePlatform(23,getAdapterPosition()+1);
                    subscriveBt.setText("구독");
                    keywordBt.setVisibility(View.INVISIBLE);
                    status =false;
                }else if(subscriveBt.getText().toString().contains("구독")) {
                    subscribeActivity.postAddKeyword(23, getAdapterPosition()+1, "");
                    subscriveBt.setText("구독 취소");
                    keywordBt.setVisibility(View.VISIBLE);
                    status = true;
                }
            }
        });

        keywordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int platform_id = mData.get(getAdapterPosition()).getPlatformId();
                show(platform_id);
            }
        });

    }
}

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SubscribeRecyclerviewAdapter(Activity act, ArrayList<PlatformData> list, ArrayList<PlatformData> subsList) {
        this.activity = act;
        mData = list ;
        this.mSubsList = subsList;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SubscribeRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_platform, parent, false) ;
        SubscribeRecyclerviewAdapter.ViewHolder vh = new SubscribeRecyclerviewAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SubscribeRecyclerviewAdapter.ViewHolder holder, int position) {
        String text = mData.get(position).getPlatformName() ;
        holder.platformName.setText(text) ;
        if(mData.get(position).getPlatformId() == -1){
            holder.subscriveBt.setVisibility(View.INVISIBLE);
            holder.keywordBt.setVisibility(View.INVISIBLE);
            Glide.with(activity).load(mData.get(position+1).getImageUrl()).into(holder.platformLogo);
        }else {
            Glide.with(activity).load(mData.get(position).getImageUrl()).into(holder.platformLogo);
            for (int i = 0; i < mSubsList.size(); i++) {
                holder.subscriveBt.setVisibility(View.VISIBLE);
                if (mData.get(position).getPlatformId() == mSubsList.get(i).getPlatformId()) {
                    Log.d(TAG, "position: " + position + " size: " + mSubsList.size() + " Name: " + mSubsList.get(i).getPlatformName());
                    holder.subscriveBt.setText("구독 취소");
                    holder.keywordBt.setVisibility(View.VISIBLE);
                    holder.status = true;
                    break;
                } else {
                    holder.subscriveBt.setText("구독");
                    holder.keywordBt.setVisibility(View.INVISIBLE);
                    holder.status = false;
                }
            }
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public void show(final int platform_number)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog,null);

        builder.setView(dialogView);

        final FlowLayout.LayoutParams hashTagLayoutParams = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
        hashTagLayoutParams.setMargins(16,16,16,16);
        mHashtagContainer = dialogView.findViewById(R.id.hashtagContainer);
        final ArrayList<String> keywordList = subscribeActivity.getKeyword(platform_number);
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO
                for (final String keyword : keywordList) {
                    if (keyword.contentEquals("") != true) {
                        final TextView textView = (TextView) activity.getLayoutInflater().inflate(R.layout.keyword_item, null);
                        textView.setText(keyword);
                        textView.setBackgroundColor(getRandomHashtagColor(activity));
                        textView.setLayoutParams(hashTagLayoutParams);
                        textView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                    //터치 이벤트
                                }
                                return false;
                            }
                        });
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Do your action
                                subscribeActivity.deleteKeyword(23, 1, textView.getText().toString());
                                mHashtagContainer.removeView(textView);
                            }
                        });

                        mHashtagContainer.addView(textView);
                    }
                }
            }
        }, 150);



        Button one = (Button) dialogView.findViewById(R.id.button1);
        Button three = (Button) dialogView.findViewById(R.id.button3);
        final EditText enterKeyword = (EditText)dialogView.findViewById(R.id.enterKeyword);

        final AlertDialog dialog = builder.create();


        one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });

        three.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String Keyword = enterKeyword.getText().toString();
                enterKeyword.setText("");
                Log.d(TAG,Keyword);
                subscribeActivity.postAddKeyword(23, platform_number, Keyword);
                //keywordList.add(Keyword);
                addKeyword(Keyword, hashTagLayoutParams);
            }
        });
        dialog.setCanceledOnTouchOutside(false);

        // Display the custom alert dialog on interface
        dialog.show();
    }

    private int getRandomHashtagColor(Context context)
    {
        int[] hashtagColors = context.getResources().getIntArray(R.array.hashtag_colors);
        int randomColor = hashtagColors[new Random().nextInt(hashtagColors.length)];
        return randomColor;
    }

    private void addKeyword(final String hashtag, FlowLayout.LayoutParams hashTagLayoutParams){
        final TextView textView = (TextView)activity.getLayoutInflater().inflate(R.layout.keyword_item, null);
        textView.setText(hashtag);
        textView.setBackgroundColor(getRandomHashtagColor(activity));
        textView.setLayoutParams(hashTagLayoutParams);
        textView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    //터치 이벤트
                }
                return false;
            }
        });
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Do your action
                subscribeActivity.deleteKeyword(23, 1, textView.getText().toString());
                mHashtagContainer.removeView(textView);
            }
        });

        mHashtagContainer.addView(textView);
    }
}