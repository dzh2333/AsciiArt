package com.mark.asciiartapp.eventbus;

import com.mark.asciiartapp.bean.PictureBean;

import java.util.List;

public class PictureBeanListEvent {

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public List<PictureBean> getList() {
        return list;
    }

    public void setList(List<PictureBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PictureBeanListEvent{" +
                "mode=" + mode +
                ", list=" + list +
                '}';
    }

    private int mode;
    private List<PictureBean> list;

    public PictureBeanListEvent(int mode, List<PictureBean> list) {
        this.mode = mode;
        this.list = list;
    }

}
