package com.example.gollect.item;

public class VideoContentsItem {
    private String platformId;
    private String url;
    private String title;
    private String thumbnail_src;
    private String duration;
    private String uploaded_at;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail_src() {
        return thumbnail_src;
    }

    public void setThumbnail_src(String thumbnail_src) {
        this.thumbnail_src = thumbnail_src;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUploaded_at() {
        return uploaded_at;
    }

    public void setUploaded_at(String uploaded_at) {
        this.uploaded_at = uploaded_at;
    }

    public VideoContentsItem(String platformId, String title, String thumbnail_src, String url, String duration, String uploaded_at) {

        this.platformId = platformId;
        this.title = title;
        this.thumbnail_src = thumbnail_src;
        this.url = url;
        this.duration = duration;
        this.uploaded_at = uploaded_at;
    }

}
