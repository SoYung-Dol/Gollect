package com.example.gollect.item;

public class BmTextContentsItem {
    private int textContentId;
    private String platformId;
    private String title;
    private String summary;
    private String url;
    private String img_src;
    private String uploaded_at;
    private int domainId;

    public BmTextContentsItem(int textContentId, String platformId, String title, String summary, String url, String img_src, String uploaded_at, int domainId) {
        this.textContentId = textContentId;
        this.platformId = platformId;
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.img_src = img_src;
        this.uploaded_at = uploaded_at;
        this.domainId = domainId;
    }

    public int getDomainId() {
        return domainId;
    }

    public void setDomainId(int domainId) {
        this.domainId = domainId;
    }

    public int getTextContentId() {
        return textContentId;
    }

    public void setTextContentId(int textContentId) {
        this.textContentId = textContentId;
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
}
