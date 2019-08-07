package com.mark.asciiartapp.bean;

public class PictureBean {
    public PictureBean(String path, boolean focused, long fileTime) {
        this.path = path;
        this.focused = focused;
        this.fileTime = fileTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public long getFileTime() {
        return fileTime;
    }

    public void setFileTime(long fileTime) {
        this.fileTime = fileTime;
    }

    @Override
    public String toString() {
        return "PictureBean{" +
                "path='" + path + '\'' +
                ", focused=" + focused +
                ", fileTime=" + fileTime +
                '}';
    }

    private String path;
    private boolean focused;
    private long fileTime;
}
