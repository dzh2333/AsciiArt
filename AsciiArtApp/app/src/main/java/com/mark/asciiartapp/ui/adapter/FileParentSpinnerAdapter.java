package com.mark.asciiartapp.ui.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mark.asciiartapp.R;
import com.mark.asciiartapp.bean.PictureSpinnerBean;

import java.util.ArrayList;
import java.util.List;

public class FileParentSpinnerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<PictureSpinnerBean> list;

    public FileParentSpinnerAdapter(Context context, List<PictureSpinnerBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_spinner_select, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.fileParent.setText(list.get(position).getTitle());
        return convertView;
    }
    class ViewHolder {
        TextView fileParent;
        public ViewHolder(View convertView){
            fileParent = (TextView) convertView.findViewById(R.id.item_spinner_select_text);
            convertView.setTag(this);
        }
    }


}
