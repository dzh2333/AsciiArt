package com.mark.asciiartapp.mvp.view;

public interface CameraView extends BaseView{

    void initCameraView();

    void showLastPhoto();

    void showLastAsciiPhoto();

    void initLastCameraView(String path);

    void initLastAsciiView(String path);

    void changeLastCameraView(String path);

    void changeAsciiView(String path);

}
