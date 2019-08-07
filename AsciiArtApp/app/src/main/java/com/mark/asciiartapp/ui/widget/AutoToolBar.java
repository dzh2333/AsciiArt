package com.mark.asciiartapp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mark.asciiartapp.R;
import com.mark.asciiartapp.bean.MenuItemBean;
import com.mark.asciiartapp.bean.PictureSpinnerBean;
import com.mark.asciiartapp.constants.Constants;
import com.mark.asciiartapp.ui.activity.SelectPicActivity;
import com.mark.asciiartapp.ui.adapter.FileParentSpinnerAdapter;
import com.mark.asciiartapp.ui.adapter.MenuRVAdapter;
import com.mark.basemodule.eventbus.SelectPictureBean;
import com.mark.basemodule.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class AutoToolBar extends LinearLayout {

    private ImageView preButton;
    private Spinner spinner;
    private ImageView menuButton;
    private PictureSelectMenu selectMenu;

    private boolean selectedMenu = false;

    private boolean recyclerviewShow = false;

    private Activity activity;

    private FileParentSpinnerAdapter fileParentSpinnerAdapter;

    public AutoToolBar(Context context) {
        super(context);
        initView(context);
    }

    public AutoToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AutoToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.view_tool_bar, this, true);
        setClipChildren(false);
        preButton = view.findViewById(R.id.view_toolbar_pre);
        spinner = view.findViewById(R.id.view_toolbar_spinner);
        menuButton = view.findViewById(R.id.view_toolbar_menu_button);

        initListener();
        initSpinner();
    }


    private void initListener(){
        preButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        menuButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMenu){
                    setMenuButtonCancelStatus(false);
                    EventBus.getDefault().post(new SelectPictureBean(0));
                }else {
                    if (recyclerviewShow){
                        recyclerviewShow = false;
                        selectMenu.setVisibility(INVISIBLE);
                    }else {
                        recyclerviewShow = true;
                        selectMenu.setVisibility(VISIBLE);
                    }
                }
            }
        });
    }

    private void initSpinner(){
        final List<PictureSpinnerBean> spinnerList = new ArrayList<>();
        spinnerList.add(new PictureSpinnerBean("所有图片", "",true,100));
        spinnerList.add(new PictureSpinnerBean("Ascii文件夹", Constants.ASCII_PATH, false,101));
        spinnerList.add(new PictureSpinnerBean("相机", Constants.DCIM_CAMERA_PATH, false,102));
        fileParentSpinnerAdapter = new FileParentSpinnerAdapter(getContext(), spinnerList);
        spinner.setAdapter(fileParentSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(spinnerList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void setSelectMenu(PictureSelectMenu selectMenu){
        this.selectMenu = selectMenu;
    }

    /**
     * 设置菜单栏的状态
     * 状态有2种：
     * 1、普通状态
     * 2、取消状态
     * @param cancel 设置为取消状态
     */
    public void setMenuButtonCancelStatus(boolean cancel){
        if (cancel){
            if (!selectedMenu){
                menuButton.setImageResource(R.drawable.cancel);
                this.selectedMenu = true;
            }
        }else {
            if (selectedMenu){
                menuButton.setImageResource(R.drawable.menu);
                selectedMenu = false;
            }
        }
    }

}
