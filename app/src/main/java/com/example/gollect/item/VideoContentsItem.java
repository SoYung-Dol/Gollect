package com.example.gollect.item;

public class VideoContentsItem {
    private String url;
    private String title;
    private String duration;
    private String uploaded_time;

    public VideoContentsItem(String url, String title, String duration, String uploaded_time) {

        this.url = url;
        this.title = title;
        this.duration = duration;
        this.uploaded_time = uploaded_time;

    }

    public String getUrl() {
        return url;
    }

    public String getGenre() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return uploaded_time;
    }
}
