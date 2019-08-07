package com.mark.asciiartapp.bean;

public class MenuItemBean {

    public MenuItemBean(int imagePath, String str) {
        this.imagePath = imagePath;
        this.str = str;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    private int imagePath;
    private String str;
}
