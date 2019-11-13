package com.example.gollect.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gollect.R;
import com.example.gollect.item.TextContentsItem;

import java.util.ArrayList;
import java.util.List;

public class TcListviewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TextContentsItem> textContentsItemList = new ArrayList<>();


    public TcListviewAdapter() {
    }

    @Override
    public int getCount() {
        return textContentsItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return textContentsItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.text_contents_listview, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        TextContentsItem textContentsItem = textContentsItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(textContentsItem.getIcon());
        titleTextView.setText(textContentsItem.getTitle());
        descTextView.setText(textContentsItem.getSub_title());

        return convertView;
    }
    public void addItem(Drawable icon, String title, String desc) {
        TextContentsItem item = new TextContentsItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setSub_title(desc);

        textContentsItemList.add(item);
    }
}
