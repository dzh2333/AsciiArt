package com.mark.asciiartapp.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mark.asciiartapp.R;
import com.mark.basemodule.eventbus.SelectedBean;

import org.greenrobot.eventbus.EventBus;

public class PictureDialog extends Dialog {

    private ImageView imageView;
    private Button button;
    private Button dismissButton;
    private String path;

    public PictureDialog( Context context) {
        super(context);
        initView(context);
    }

    public PictureDialog(Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected PictureDialog(Context context, boolean cancelable,  DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context){
        setContentView(R.layout.view_picture_info);
        imageView = findViewById(R.id.dialog_picture_info_image);
        button = findViewById(R.id.dialog_picture_info_button);
        dismissButton = findViewById(R.id.dialog_picture_info_dismiss);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SelectedBean(1, path));
            }
        });
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setImageViewResources(String path){
        Glide.with(getContext()).load(path).into(imageView);
        this.path = path;
    }
}
