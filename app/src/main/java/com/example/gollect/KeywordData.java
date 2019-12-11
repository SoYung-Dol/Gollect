package com.example.gollect;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KeywordData extends RealmObject {
    @PrimaryKey
    int id;
    String keyword;

    public KeywordData() {
    }

    public KeywordData(int id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
