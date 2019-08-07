package com.mark.asciiartapp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mark.asciiartapp.R;
import com.mark.asciiartapp.application.MainApplication;
import com.mark.asciiartapp.mvp.model.base.MainModel;
import com.mark.asciiartapp.mvp.model.impl.MainModelImpl;
import com.mark.asciiartapp.mvp.presenter.MainPresenter;
import com.mark.asciiartapp.mvp.view.MainView;
import com.mark.asciiartapp.ui.base.BaseMvpActivity;
import com.mark.basemodule.eventbus.ChangeAsciiFinishedEvent;
import com.mark.basemodule.eventbus.SelectedBean;
import com.mark.basemodule.util.LogUtils;
import com.mark.basemodule.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainModel, MainView, MainPresenter> implements MainView {

    @BindView(R.id.select_resources)
    Button selectResources;
    @BindView(R.id.use_special_effects)
    Button useSpecialEffects;
    @BindView(R.id.output_imageview)
    ImageView imageView;

    @BindView(R.id.main_goto_camera)
    ImageView gotoCamera;

    private String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        MainApplication.permissionUtils.applyPermissions(MainActivity.this, permissions);

        init();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectedBean selectedBean){
        switch (selectedBean.getEventType()){
            case 0:
                break;
            case 1:
                presenter.changeBitmapToAscii(MainActivity.this, selectedBean.getPath());
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChangeAsciiFinishedEvent event){
        if (event.isChangeFinished()){
            Glide.with(MainActivity.this).load(event.getPath()).into(imageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getProviderId() {
        return R.layout.activity_main;
    }

    private void init(){
        if (presenter != null){
            presenter.getData();
        }
        gotoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CameraActivity.class));
            }
        });
    }

    @Override
    public MainModel createModel() {
        return new MainModelImpl();
    }

    @Override
    public MainView createView() {
        return this;
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void setData(String content) {
    }

    @Override
    public void setImageViewResources(String path) {
        Glide.with(MainActivity.this).load(path).into(imageView);
    }

    @Override
    public void showToast(String content) {

    }

    @Override
    public void showProcess() {

    }

    @OnClick({R.id.select_resources, R.id.use_special_effects})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.use_special_effects:

                break;
            case R.id.select_resources:
                startActivity(new Intent(MainActivity.this, SelectPicActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){


            }else {
                //不再询问拒绝
                if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[0])){
                    LogUtils.d("权限被不再询问拒绝");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
