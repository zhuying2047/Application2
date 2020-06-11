package com.swufe.myapplication2;

public class MemoItem {
    private int id;
    private String content;
    private String date;

    public MemoItem() {
        this.content = "";
        this.date = "";

    }

    public MemoItem(String curName, String curRate) {
        this.content = curName;
        this.date = curRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}


