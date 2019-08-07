package com.mark.asciiartapp.eventbus;

public class CameraEventBean {

    public CameraEventBean(int eventType, String path) {
        this.eventType = eventType;
        this.path = path;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "CameraEventBean{" +
                "eventType=" + eventType +
                ", path='" + path + '\'' +
                '}';
    }

    private int eventType;
    private String path;

}
