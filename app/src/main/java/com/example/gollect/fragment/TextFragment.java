package com.example.gollect.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gollect.R;
import com.example.gollect.adapter.TcListviewAdapter;
import com.example.gollect.item.TextContentsItem;

public class TextFragment extends ListFragment {

    private TcListviewAdapter adapter;
    public TextFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        adapter = new TcListviewAdapter();
        setListAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title1","contents1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title2","contents2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ic_launcher_foreground), "title3","contents3");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        TextContentsItem item = (TextContentsItem) l.getItemAtPosition(position);

        String titleStr = item.getTitle();
        String sub_titleStr = item.getSub_title();
        Drawable iconDrawable = item.getIcon();

        Log.d("jaejin",position+titleStr);
        Log.d("jaejin",position+sub_titleStr);
    }
    public void addItem2(Drawable icon, String title, String desc){
        Log.d("jaejin","?????");
        adapter.addItem(icon,title,desc);
    }
}
