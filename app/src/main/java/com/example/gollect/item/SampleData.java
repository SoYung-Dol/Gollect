package com.example.gollect.item;


import com.example.gollect.R;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class SampleData {
    ArrayList<VideoContentsItem> items = new ArrayList<>();
    public ArrayList<VideoContentsItem> getItems() {

        VideoContentsItem movie1 = new VideoContentsItem("https://i.ytimg.com/vi/KEgC50mX8ho/maxresdefault.jpg",
                "아이린", "4분", "1분전");

        VideoContentsItem movie2 = new VideoContentsItem("https://img.insight.co.kr/static/2018/01/26/700/779f9cylfw1j82j39iny.jpg",
                "전지현", "4분", "1분전");

        VideoContentsItem movie3 = new VideoContentsItem("http://img1.daumcdn.net/thumb/S414x237/?fname=http%3A%2F%2Fcfile66.uf.daum.net%2Fimage%2F2160B93F576CE6162456B6",
                "한지민", "4분", "1분전");

        items.add(movie1);
        items.add(movie2);
        items.add(movie3);

        items.add(movie1);
        items.add(movie2);
        items.add(movie3);

        items.add(movie1);
        items.add(movie2);
        items.add(movie3);

        return items;
    }
}
