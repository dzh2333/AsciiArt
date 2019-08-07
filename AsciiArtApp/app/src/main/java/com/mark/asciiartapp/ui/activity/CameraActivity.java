package com.mark.asciiartapp.ui.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mark.asciiartapp.R;
import com.mark.asciiartapp.bean.PictureBean;
import com.mark.asciiartapp.eventbus.CameraEventBean;
import com.mark.asciiartapp.mvp.model.base.CameraModel;
import com.mark.asciiartapp.mvp.model.base.PictureModel;
import com.mark.asciiartapp.mvp.model.impl.CameraModelImpl;
import com.mark.asciiartapp.mvp.presenter.CameraPresenter;
import com.mark.asciiartapp.mvp.presenter.PicturePresenter;
import com.mark.asciiartapp.mvp.view.CameraView;
import com.mark.asciiartapp.mvp.view.PictureView;
import com.mark.asciiartapp.ui.base.BaseMvpActivity;
import com.mark.asciiartapp.ui.widget.CameraButton;
import com.mark.asciiartapp.ui.widget.CameraGLSurfaceView;
import com.mark.asciiartapp.ui.widget.PictureDialog;
import com.mark.asciiartapp.ui.widget.camera.CameraInterface;
import com.mark.basemodule.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class CameraActivity extends BaseMvpActivity<CameraModel, CameraView, CameraPresenter> implements CameraView {

    @BindView(R.id.camera_take)
    CameraButton cameraButton;
    @BindView(R.id.camera_last_shot_view)
    ImageView lastShotPicture;
    @BindView(R.id.camera_last_shot_generated_view)
    ImageView lastGeneratedPicture;

    @BindView(R.id.camera_view)
    CameraGLSurfaceView glSurfaceView;

    private String lastShotPicturePath;
    private String lastGeneratedPicturePath;

    public boolean canTakePhoto = true;
    private static final int MIN_TAKE_PHOTO_DELAY_TIME= 3000;
    private static long lastCameraTime;

    private HandlerThread handlerThread;
    private Handler handler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CameraEventBean eventBean){
        switch (eventBean.getEventType()){
            case 1:
                presenter.changeSmallView(eventBean.getEventType(), eventBean.getPath());
                presenter.changePictureToAscii(CameraActivity.this, eventBean.getPath());
                break;
            case 2:
                presenter.changeSmallView(2, eventBean.getPath());
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void init(){
        initView();
    }

    private void initView(){

        presenter.initSmallView();

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        CameraInterface.getInstance().doTakePicture();
                        Looper.loop();
                    }
                }).start();

            }
        });

        /*
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                long currentClickTime = System.currentTimeMillis();
                if ((currentClickTime - lastCameraTime) >= MIN_TAKE_PHOTO_DELAY_TIME) {
                    flag = true;
                    LogUtils.d("拍照：时间通过");
                    lastCameraTime = currentClickTime;
                }else {
                    LogUtils.d("拍照：时间不通过");
                }


                if (flag){
                    LogUtils.d("拍照：");
                    handlerThread = new HandlerThread("TakePhotoThread");
                    handlerThread.start();
                    handler = new Handler(handlerThread.getLooper()){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            CameraInterface.getInstance().doTakePicture();
                        }
                    };
                    handler.sendEmptyMessage(0);
                }
            }
        });
*/
        lastShotPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureDialog dialog = new PictureDialog(CameraActivity.this);
                dialog.setImageViewResources(getLastShotPicturePath());
                dialog.show();
            }
        });

        lastGeneratedPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureDialog dialog = new PictureDialog(CameraActivity.this);
                dialog.setImageViewResources(getLastGeneratedPicturePath());
                dialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected int getProviderId() {
        return R.layout.activity_camera;
    }

    @Override
    public CameraModel createModel() {
        return new CameraModelImpl();
    }

    @Override
    public CameraView createView() {
        return this;
    }

    @Override
    public CameraPresenter createPresenter() {
        return new CameraPresenter();
    }


    @Override
    public void showToast(String content) {

    }

    @Override
    public void showProcess() {

    }

    @Override
    public void initCameraView() {

    }

    @Override
    public void showLastPhoto() {
        PictureDialog dialog = new PictureDialog(CameraActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setImageViewResources(getLastShotPicturePath());
        dialog.show();
    }

    @Override
    public void showLastAsciiPhoto() {
        PictureDialog dialog = new PictureDialog(CameraActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setImageViewResources(getLastGeneratedPicturePath());
        dialog.show();
    }

    @Override
    public void initLastCameraView(String path) {
        Glide.with(CameraActivity.this).load(path).into(lastShotPicture);
        setLastShotPicturePath(path);
    }

    @Override
    public void initLastAsciiView(String path) {
        Glide.with(CameraActivity.this).load(path).into(lastGeneratedPicture);
        setLastGeneratedPicturePath(path);
    }

    @Override
    public void changeLastCameraView(String path) {
        Glide.with(CameraActivity.this).load(path).into(lastShotPicture);
        LogUtils.d("接收到了数据" + path);
        setLastShotPicturePath(path);
    }

    @Override
    public void changeAsciiView(String path) {
        Glide.with(CameraActivity.this).load(path).into(lastGeneratedPicture);
        setLastGeneratedPicturePath(path);
    }

    public String getLastShotPicturePath() {
        return lastShotPicturePath;
    }

    public void setLastShotPicturePath(String lastShotPicturePath) {
        this.lastShotPicturePath = lastShotPicturePath;
    }

    public String getLastGeneratedPicturePath() {
        return lastGeneratedPicturePath;
    }

    public void setLastGeneratedPicturePath(String lastGeneratedPicturePath) {
        this.lastGeneratedPicturePath = lastGeneratedPicturePath;
    }
}
