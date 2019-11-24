package com.example.gollect.item;

import android.graphics.drawable.Drawable;

public class TextContentsItem {
    private String platformId;
    private String title;
    private String summary;
    private String url;
    private String img_src;
    private String uploaded_at;


    public TextContentsItem(String platformId, String title, String summary, String url,String img_src,String uploaded_at) {
        this.platformId = platformId;
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.img_src = img_src;
        this.uploaded_at = uploaded_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public String getUploaded_at() {
        return uploaded_at;
    }

    public void setUploaded_at(String uploaded_at) {
        this.uploaded_at = uploaded_at;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
