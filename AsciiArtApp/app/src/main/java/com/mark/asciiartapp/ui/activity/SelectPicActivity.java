package com.mark.asciiartapp.ui.activity;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mark.asciiartapp.R;
import com.mark.asciiartapp.bean.PictureBean;
import com.mark.asciiartapp.bean.PictureSpinnerBean;
import com.mark.asciiartapp.constants.Constants;
import com.mark.asciiartapp.eventbus.PictureBeanListEvent;
import com.mark.asciiartapp.listener.RVItemOnclickListener;
import com.mark.asciiartapp.mvp.model.base.PictureModel;
import com.mark.asciiartapp.mvp.model.impl.PictureModelImpl;
import com.mark.asciiartapp.mvp.presenter.PicturePresenter;
import com.mark.asciiartapp.mvp.view.PictureView;
import com.mark.asciiartapp.ui.adapter.AllPictureRVAdapter;
import com.mark.asciiartapp.ui.base.BaseMvpActivity;
import com.mark.asciiartapp.ui.widget.AutoToolBar;
import com.mark.asciiartapp.ui.widget.PictureSelectMenu;
import com.mark.asciiartapp.ui.widget.SureButton;
import com.mark.basemodule.eventbus.SelectPictureBean;
import com.mark.basemodule.eventbus.SelectedBean;
import com.mark.basemodule.util.HuaWeiBottomUiFit;
import com.mark.basemodule.util.LogUtils;
import com.mark.basemodule.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectPicActivity extends BaseMvpActivity<PictureModel, PictureView, PicturePresenter> implements PictureView{

    @BindView(R.id.select_pic_rv)
    RecyclerView selectRV;

    @BindView(R.id.select_pic_toolbar)
    AutoToolBar autoToolBar;

    private AllPictureRVAdapter allPictureRVAdapter;

    private PictureSelectMenu pictureSelectMenu;
    private SureButton sureButton;


    private boolean menuShowStatus = false;
    private boolean sureButtonShowStatus = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        init();
        LogUtils.d("常量为" + Constants.DCIM_CAMERA_PATH);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        pictureSelectMenu = new PictureSelectMenu(SelectPicActivity.this);
        ((ViewGroup)getWindow().getDecorView()).addView(pictureSelectMenu);
        pictureSelectMenu.setVisibility(View.INVISIBLE);
        setMenuShowStatus(false);
//        HuaWeiBottomUiFit.assistActivity(pictureSelectMenu);

        sureButton = new SureButton(SelectPicActivity.this);
        ((ViewGroup)getWindow().getDecorView()).addView(sureButton);
        HuaWeiBottomUiFit.assistActivity(sureButton);
        sureButton.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void init(){
        if (presenter != null){
            presenter.showAllPicture(SelectPicActivity.this);
        }
        initView();
    }

    private void initView(){
        autoToolBar.setSelectMenu(pictureSelectMenu);
        autoToolBar.setActivity(SelectPicActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectedBean selectedBean){
        switch (selectedBean.getEventType()){
            case 0:
                break;
            case 1:
                finish();
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectPictureBean bean){
        switch (bean.getEventType()){
            case 0:
                //取消
                allPictureRVAdapter.setMode(0);
                sureButton.setVisibility(View.INVISIBLE);
                sureButtonShowStatus = false;
                break;
            case 1:
                //删除
                allPictureRVAdapter.setMode(1);
                pictureSelectMenu.setVisibility(View.INVISIBLE);
                autoToolBar.setMenuButtonCancelStatus(true);
                setMenuShowStatus(true);
                sureButton.setVisibility(View.VISIBLE);
                sureButtonShowStatus = true;
                break;
            case 2:
                //分享
                allPictureRVAdapter.setMode(2);
                pictureSelectMenu.setVisibility(View.INVISIBLE);
                autoToolBar.setMenuButtonCancelStatus(true);
                setMenuShowStatus(true);
                sureButton.setVisibility(View.VISIBLE);
                sureButtonShowStatus = true;
                break;
            case 10:
                //点击了确认
                List<PictureBean> focusedItemBean = allPictureRVAdapter.getFocusedItem();
                if (focusedItemBean.size() ==0){
                    ToastUtils.showToast("请选择1个或以上单位");
                }else {
                    switch (allPictureRVAdapter.getMode()){
                        case 1:
                            //删除数据操作
                            presenter.changePictureDataOrShareData(SelectPicActivity.this,
                                    1,
                                    focusedItemBean,
                                    allPictureRVAdapter.isRootPath(),
                                    allPictureRVAdapter.getSourceParentPath());
                            break;
                        case 2:
                            /*
                             弹出分享栏，选择分享方式。方式有：
                             1、微信
                             2、蓝牙
                             3、微博
                             */

                            break;
                        default:

                            break;
                    }
                }
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PictureBeanListEvent event){
        switch (event.getMode()){
            case 0:
                break;
            case 1:
                //需要判断当前展示的文件夹资源是哪个
                if (event.getList().size() > 0){
                    changePictureInRV(event.getList());
                }
                break;
            case 100:
                //改变Spinner数据
                changePictureInRV(event.getList());
                break;
            case 101:
                //改变Spinner数据
                changePictureInRV(event.getList());
                break;
            case 102:
                //改变Spinner数据
                changePictureInRV(event.getList());
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PictureSpinnerBean bean){
        presenter.spinnerChangePictureRVData(SelectPicActivity.this, bean);
        allPictureRVAdapter.setSourceParentPath(bean.getPath());
        if (bean.isRootPath()){
            allPictureRVAdapter.setRootPath(true);
        }else {
            allPictureRVAdapter.setRootPath(false);
        }

    }

    @Override
    protected int getProviderId() {
        return R.layout.activity_select_pic;
    }


    @Override
    public void showToast(String content) {

    }

    @Override
    public void showProcess() {

    }

    @Override
    public PictureModel createModel() {
        return new PictureModelImpl();
    }

    @Override
    public PictureView createView() {
        return this;
    }

    @Override
    public PicturePresenter createPresenter() {
        return new PicturePresenter();
    }

    @Override
    public void showPictureInRV(List<PictureBean> list) {
        allPictureRVAdapter  = new AllPictureRVAdapter(SelectPicActivity.this, list, new RVItemOnclickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        selectRV.setAdapter(allPictureRVAdapter);
        selectRV.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void changePictureInRV(List<PictureBean> list) {
        allPictureRVAdapter.setDataList(list);
    }


    public boolean isMenuShowStatus() {
        return menuShowStatus;
    }

    public void setMenuShowStatus(boolean menuShowStatus) {
        this.menuShowStatus = menuShowStatus;
    }



}
