package com.mark.asciiartapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mark.asciiartapp.R;
import com.mark.asciiartapp.bean.MenuItemBean;
import com.mark.asciiartapp.listener.RVItemOnclickListener;
import com.mark.asciiartapp.ui.adapter.base.BaseRecyclerView;
import com.mark.basemodule.eventbus.SelectPictureBean;
import com.mark.basemodule.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MenuRVAdapter extends BaseRecyclerView<MenuItemBean> {

    private Context context;

    public MenuRVAdapter(Context context, List<MenuItemBean> list){
        mData = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menu_rv, viewGroup, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        MenuViewHolder menuViewHolder = (MenuViewHolder) viewHolder;
        menuViewHolder.textView.setText(mData.get(position).getStr());
        menuViewHolder.imageView.setImageResource(mData.get(position).getImagePath());

        menuViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        EventBus.getDefault().post(new SelectPictureBean(2));
                        break;
                    case 1:
                        EventBus.getDefault().post(new SelectPictureBean(1));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private class MenuViewHolder extends BaseViewHolder{
        LinearLayout layout;
        ImageView imageView;
        TextView textView;
        public MenuViewHolder(View itemView) {
            super(itemView, mClickListener);
            layout = itemView.findViewById(R.id.item_menu_rv_layout);
            imageView = itemView.findViewById(R.id.item_menu_rv_image);
            textView = itemView.findViewById(R.id.item_menu_rv_title);
        }
    }
}
