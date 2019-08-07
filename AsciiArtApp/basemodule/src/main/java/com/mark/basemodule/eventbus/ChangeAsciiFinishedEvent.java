package com.mark.basemodule.eventbus;

public class ChangeAsciiFinishedEvent {


    public ChangeAsciiFinishedEvent(boolean changeFinished, String path) {
        this.changeFinished = changeFinished;
        this.path = path;
    }

    public boolean isChangeFinished() {
        return changeFinished;
    }

    public void setChangeFinished(boolean changeFinished) {
        this.changeFinished = changeFinished;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ChangeAsciiFinishedEvent{" +
                "changeFinished=" + changeFinished +
                ", path='" + path + '\'' +
                '}';
    }

    private boolean changeFinished;
    private String path;
}
