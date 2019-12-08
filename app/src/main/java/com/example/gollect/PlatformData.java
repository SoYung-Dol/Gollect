package com.example.gollect;

public class PlatformData {

    public static final int HEADER_TYPE = 0;
    public static final int CHILD_TYPE = 1;

    String PlatformName;
    String ImageUrl;
    //PlatformId는 각각의 고유 id
    int PlatformId;
    //플랫폼의 1depth 종류
    int DomainId;
    boolean Subscribe;
    private int mType;

    public PlatformData(int platformId, boolean subscribe) {
        this.PlatformId = platformId;
        this.Subscribe = subscribe;

    }

    public PlatformData(String platformName, String imageUrl, int platformId, int type) {
        this.PlatformName = platformName;
        this.ImageUrl = imageUrl;
        this.PlatformId = platformId;
        this.mType = type;
    }

    public int getType() {
        return mType;
    }

    public String getPlatformName() {
        return PlatformName;
    }

    public void setPlatformName(String platformName) {
        PlatformName = platformName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getPlatformId() {
        return PlatformId;
    }

    public void setPlatformId(int platformId) {
        PlatformId = platformId;
    }

    public boolean isSubscribe() {
        return Subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        Subscribe = subscribe;
    }
}