package com.mark.asciiartapp.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mark.asciiartapp.listener.RVItemOnclickListener;

import java.util.List;

public abstract class BaseRecyclerView<T> extends RecyclerView.Adapter {

    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_FOOTER = 1;
    protected static final int TYPE_NORMAL = 2;

    protected View mHeaderView;
    protected View mFooterView;
    protected List<T> mData;
    protected String mTag;
    protected RVItemOnclickListener mClickListener;

    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return mData.size();
        }else if(mHeaderView != null && mFooterView == null){
            return mData.size() + 1;
        }else if(mHeaderView == null && mFooterView != null){
            return mData.size() + 1;
        }else{
            return mData.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (mHeaderView != null) {
            if (position == 0) {
                return TYPE_HEADER;
            }
        }
        if (mFooterView != null) {
            if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            }
        }
        return TYPE_NORMAL;
    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RVItemOnclickListener mListener;
        public BaseViewHolder(View itemView, RVItemOnclickListener listener) {
            super(itemView);
            this.mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener == null){
                return;
            }
            mListener.onItemClick(v, getPosition());
        }
    }

    public void changeListData(List<T> list){
        mData = list;
        notifyDataSetChanged();
    }

}
