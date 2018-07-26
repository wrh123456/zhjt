package com.example.zhjt.Bean;

public class title {
    private int id;
    private String titletext;
    public title(int id,String titletext){
        this.id=id;
        this.titletext=titletext;
    }

    public String getTitletext() {
        return titletext;
    }

    public void setTitletext(String titletext) {
        this.titletext = titletext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
