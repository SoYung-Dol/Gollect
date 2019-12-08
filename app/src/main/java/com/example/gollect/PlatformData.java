package com.example.gollect;

public class PlatformData {
    String PlatformName;
    String ImageUrl;
    //PlatformId는 각각의 고유 id
    int PlatformId;
    //플랫폼의 1depth 종류
    int DomainId;
    boolean Subscribe;

    public PlatformData(int platformId, boolean subscribe) {
        this.PlatformId = platformId;
        this.Subscribe = subscribe;

    }

    public PlatformData(String platformName, String imageUrl, int platformId) {
        this.PlatformName = platformName;
        this.ImageUrl = imageUrl;
        this.PlatformId = platformId;
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