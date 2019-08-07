package com.mark.asciiartapp.bean;

public class PictureSpinnerBean {

    public PictureSpinnerBean(String title, String path, boolean rootPath, int eventNms) {
        this.title = title;
        this.path = path;
        this.rootPath = rootPath;
        this.eventNms = eventNms;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRootPath() {
        return rootPath;
    }

    public void setRootPath(boolean rootPath) {
        this.rootPath = rootPath;
    }

    public int getEventNms() {
        return eventNms;
    }

    public void setEventNms(int eventNms) {
        this.eventNms = eventNms;
    }

    @Override
    public String toString() {
        return "PictureSpinnerBean{" +
                "title='" + title + '\'' +
                ", path='" + path + '\'' +
                ", rootPath=" + rootPath +
                ", eventNms=" + eventNms +
                '}';
    }

    private String title;
    private String path;
    private boolean rootPath;
    private int eventNms;
}
