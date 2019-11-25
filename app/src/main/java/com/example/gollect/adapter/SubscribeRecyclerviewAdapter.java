package com.example.gollect.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gollect.R;
import com.example.gollect.SubscibeActivity;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class SubscribeRecyclerviewAdapter extends RecyclerView.Adapter<SubscribeRecyclerviewAdapter.ViewHolder> {

private ArrayList<String> mData = null ;
    Activity activity;

// 아이템 뷰를 저장하는 뷰홀더 클래스.
public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView platformLogo;
    TextView platformName ;
    Button keywordBt;
    Button subscriveBt;

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
                    subscriveBt.setText("구독");
                    keywordBt.setVisibility(View.INVISIBLE);
                    status =false;
                }else if(subscriveBt.getText().toString().contains("구독")) {
                    subscriveBt.setText("구독 취소");
                    keywordBt.setVisibility(View.VISIBLE);
                    status = true;
                }
            }
        });

        keywordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }
}

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public SubscribeRecyclerviewAdapter(Activity act, ArrayList<String> list) {
        this.activity = act;
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SubscribeRecyclerviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_platform, parent, false) ;
        SubscribeRecyclerviewAdapter.ViewHolder vh = new SubscribeRecyclerviewAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SubscribeRecyclerviewAdapter.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        holder.platformName.setText(text) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    void show()
    {
        final List<String> ListItems = new ArrayList<>();
        //키워드 가져오는 메소드 필요
        ListItems.add("사과");
        ListItems.add("배");
        ListItems.add("귤");
        ListItems.add("바나나");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("키워드 관리");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                String selectedText = items[pos].toString();
            }
        });
        builder.show();
    }

}