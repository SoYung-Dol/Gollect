package com.example.gollect.adapter;

import com.example.gollect.fragment.AlarmFragment;
import com.example.gollect.fragment.BookmarkFragment;
import com.example.gollect.fragment.SettingFragment;
import com.example.gollect.fragment.TextFragment;
import com.example.gollect.fragment.VideoFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position){
        switch (position) {
            case 0:
                TextFragment textFragment = new TextFragment();
                return textFragment;
            case 1:
                VideoFragment videoFragment = new VideoFragment();
                return videoFragment;
            case 2:
                BookmarkFragment bookmarkFragment = new BookmarkFragment();
                return bookmarkFragment;
            case 3:
                AlarmFragment alarmFragment = new AlarmFragment();
                return alarmFragment;
            case 4:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return tabCount;
    }
}
