package com.example.gollect.item;

import android.graphics.drawable.Drawable;

public class SettingContentsItem {
    private String title;
    private Drawable icon;
/*
    public SettingContentsItem(String title,Drawable icon) {
        this.title = title;
        this.icon = icon;
    }
 */
    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
