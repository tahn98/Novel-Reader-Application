package com.tahn.novelgui.DataObject;

public class Novel {
    private String name;
    private int img;
    private String rate;
    private String desc;
    private String date;

    public String getRate() {
        return rate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Novel(String name, String desc, String date, int img){
        this.name = name;
        this.desc = desc;
        this.date = date;
        this.img = img;
    }

    public Novel(String name, int img, String rate) {
        this.name = name;
        this.img = img;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
