package com.example.gollect.item;

public class BmVideoContentsItem {
    private int videoContentId;
    private String platformId;
    private String url;
    private String title;
    private String thumbnail_src;
    private String duration;
    private String uploaded_at;
    private int domainId;

    public BmVideoContentsItem(int videoContentId, String platformId, String title, String thumbnail_src, String url, String duration, String uploaded_at, int domainId) {
        this.videoContentId = videoContentId;
        this.platformId = platformId;
        this.url = url;
        this.title = title;
        this.thumbnail_src = thumbnail_src;
        this.duration = duration;
        this.uploaded_at = uploaded_at;
        this.domainId = domainId;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public int getVideoContentId() {
        return videoContentId;
    }

    public void setVideoContentId(int videoContentId) {
        this.videoContentId = videoContentId;
    }

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
}
