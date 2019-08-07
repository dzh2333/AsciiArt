package com.mark.asciiartapp.mvp.view;

public interface MainView extends BaseView{

    void setData(String content);

    /**
     * 将资源转化为Ascii资源
     * @param path
     */
    void setImageViewResources(String path);
}
