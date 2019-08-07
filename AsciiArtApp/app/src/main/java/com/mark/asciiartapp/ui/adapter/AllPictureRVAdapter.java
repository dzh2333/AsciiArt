package com.mark.asciiartapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.mark.asciiartapp.R;
import com.mark.asciiartapp.bean.PictureBean;
import com.mark.asciiartapp.listener.RVItemOnclickListener;
import com.mark.asciiartapp.ui.adapter.base.BaseRecyclerView;
import com.mark.asciiartapp.ui.widget.PictureDialog;

import java.util.ArrayList;
import java.util.List;

public class AllPictureRVAdapter extends BaseRecyclerView<PictureBean> {

    private int mode;
    private Context context;

    private String sourceParentPath;
    private boolean rootPath;

    public AllPictureRVAdapter(Context context, List<PictureBean> list, RVItemOnclickListener listener){
        this.context = context;
        mData = list;
        mClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_select_pic, viewGroup, false);
        return new PicViewHolder(view, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final PicViewHolder picViewHolder = (PicViewHolder) viewHolder;
        Glide.with(context).load(mData.get(i).getPath()).into(picViewHolder.imageView);
        final int position = i;

        if (mode == 1 || mode == 2){
            if (!mData.get(position).isFocused()){
                picViewHolder.selectBg.setVisibility(View.VISIBLE);
                picViewHolder.selectCheckbox.setVisibility(View.VISIBLE);

                mData.get(position).setFocused(false);
                Glide.with(context).load(R.drawable.picture_no_focused).into(picViewHolder.selectBg);
                Glide.with(context).load(R.drawable.check_box).into(picViewHolder.selectCheckbox);
            }
        }else {
            //还原为正常状态
            picViewHolder.selectCheckbox.setVisibility(View.GONE);
            picViewHolder.selectBg.setVisibility(View.GONE);
            mData.get(position).setFocused(false);
        }

        picViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == 1 || mode == 2){
                    if (mData.get(position).isFocused()){
                        picViewHolder.selectBg.setVisibility(View.VISIBLE);
                        picViewHolder.selectCheckbox.setVisibility(View.VISIBLE);

                        mData.get(position).setFocused(false);
                        Glide.with(context).load(R.drawable.picture_no_focused).into(picViewHolder.selectBg);
                        Glide.with(context).load(R.drawable.check_box).into(picViewHolder.selectCheckbox);

                    }else {
                        mData.get(position).setFocused(true);
                        picViewHolder.selectBg.setVisibility(View.VISIBLE);
                        picViewHolder.selectCheckbox.setVisibility(View.VISIBLE);
                        Glide.with(context).load(R.drawable.check_box_focused).into(picViewHolder.selectCheckbox);
                        Glide.with(context).load(R.drawable.picture_focused).into(picViewHolder.selectBg);
                    }
                }else {
                    //还原为正常状态
                    picViewHolder.selectCheckbox.setVisibility(View.GONE);
                    picViewHolder.selectBg.setVisibility(View.GONE);
                    mData.get(position).setFocused(false);

                    PictureDialog pictureDialog = new PictureDialog(context);
                    pictureDialog.setImageViewResources(mData.get(position).getPath());
                    pictureDialog.setCanceledOnTouchOutside(true);
                    pictureDialog.show();
                }

            }
        });
    }

    private class PicViewHolder extends BaseViewHolder{
        ImageView imageView;
        RelativeLayout layout;
        ImageView selectBg;
        ImageView selectCheckbox;
        public PicViewHolder(View itemView, RVItemOnclickListener listener) {
            super(itemView, listener);
            layout = itemView.findViewById(R.id.item_rv_select_pic_layout);
            imageView = itemView.findViewById(R.id.item_rv_select_pic_imageview);
            selectBg = itemView.findViewById(R.id.item_rv_select_pic_select);
            selectCheckbox = itemView.findViewById(R.id.item_rv_select_pic_checkbox);
        }
    }

    public void setMode(int mode){
        this.mode = mode;
        notifyDataSetChanged();
    }

    public int getMode(){
        return this.mode;
    }

    public List<PictureBean> getFocusedItem(){
        List<PictureBean> res = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++){
            if (mData.get(i).isFocused()){
                res.add(mData.get(i));
            }
        }
        return res;
    }

    public List<PictureBean> getDataList(){
        return mData;
    }

    public void setDataList(List<PictureBean> list){
        mData = list;
        notifyDataSetChanged();
        initFocusedStatus();
    }

    public void initFocusedStatus(){
        for (int i = 0; i < mData.size(); i++){
            mData.get(i).setFocused(false);
        }
    }

    public String getSourceParentPath() {
        return sourceParentPath;
    }

    public void setSourceParentPath(String sourceParentPath) {
        this.sourceParentPath = sourceParentPath;
    }

    public boolean isRootPath() {
        return rootPath;
    }

    public void setRootPath(boolean rootPath) {
        this.rootPath = rootPath;
    }

}
