package com.mark.asciiartapp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mark.asciiartapp.R;
import com.mark.basemodule.eventbus.SelectPictureBean;

import org.greenrobot.eventbus.EventBus;

public class SureButton extends RelativeLayout {

    private Button sureButton;

    public SureButton(Context context) {
        super(context);
        initView();
    }

    public SureButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SureButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_sure_button, this, true);
        sureButton = view.findViewById(R.id.view_sure_button);

        sureButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SelectPictureBean(10));
            }
        });
    }
}
