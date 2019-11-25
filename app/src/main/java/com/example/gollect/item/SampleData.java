package com.example.gollect.item;


import com.example.gollect.R;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class SampleData {
    ArrayList<VideoContentsItem> items = new ArrayList<>();
    public ArrayList<VideoContentsItem> getItems() {

        VideoContentsItem movie1 = new VideoContentsItem("http://img.tf.co.kr/article/home/2016/02/17/201637941455648532.jpg",
                "오버워치", "4:30", "11/24","","");

        VideoContentsItem movie2 = new VideoContentsItem("https://post-phinf.pstatic.net/MjAxOTA2MjBfMTM0/MDAxNTYwOTk2MjEwNzIw.Q2Vnj5EcjTmU5rQBtucKLhD1Lu_WMiVe2SpAM242UDkg.F1nKAMvNA5Tg65LiwJVKaemxZozJt5Uh0PG0ztRSMjcg.JPEG/01_%EB%B0%B0%EA%B7%B8.jpg?type=w1200",
                "배틀그라운드", "11:22", "11/23","","");

        VideoContentsItem movie3 = new VideoContentsItem("http://www.busan.com/nas/wcms/wcms_data/photos/2019/05/22/2019052210200143511_m.jpg",
                "롤", "9:11", "11/11","","");

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
