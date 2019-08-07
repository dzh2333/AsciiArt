package com.mark.asciiartapp.mvp.view;

import com.mark.asciiartapp.bean.PictureBean;

import java.util.List;

public interface PictureView extends BaseView{

    /**
     * 展示图片数据
     * @param list
     */
    void showPictureInRV(List<PictureBean> list);

    /**
     * 改变图片数据
     * @param list
     */
    void changePictureInRV(List<PictureBean> list);


}
