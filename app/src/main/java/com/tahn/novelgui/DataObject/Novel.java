package com.tahn.novelgui.DataObject;

public class Novel {
    private String name;
    private int img;
    private String rate;


    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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
