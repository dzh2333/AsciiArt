package com.mark.asciiartapp.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mark.asciiartapp.R;
import com.mark.asciiartapp.bean.MenuItemBean;
import com.mark.asciiartapp.ui.adapter.MenuRVAdapter;
import com.mark.basemodule.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class PictureSelectMenu extends RelativeLayout {

    private RecyclerView recyclerView;
    private MenuRVAdapter menuRVAdapter;

    public PictureSelectMenu(@NonNull Context context) {
        super(context);
        initView();
    }

    public PictureSelectMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PictureSelectMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_menu_picture_selects, this, true);
        recyclerView = view.findViewById(R.id.select_pic_menu_rv);
        initRV();
    }

    private void initRV(){
        List<MenuItemBean> list = new ArrayList<>();
        list.add(new MenuItemBean(R.drawable.share, "分享"));
        list.add(new MenuItemBean(R.drawable.delete, "删除"));
        menuRVAdapter = new MenuRVAdapter(getContext(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(menuRVAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
