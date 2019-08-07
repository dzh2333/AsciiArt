package com.mark.basemodule.eventbus;

public class SelectPictureBean {

    public SelectPictureBean(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "SelectPictureBean{" +
                "eventType=" + eventType +
                '}';
    }

    private int eventType;
}
