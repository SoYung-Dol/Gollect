package com.example.gollect.item;

import android.graphics.drawable.Drawable;

public class TextContentsItem {
    private Drawable icon;
    private String title;
    private String sub_title;

/*
    public TextContentsItem(Drawable icon, String title, String sub_title) {
        this.icon = icon;
        this.title = title;
        this.sub_title = sub_title;
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

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }
}
