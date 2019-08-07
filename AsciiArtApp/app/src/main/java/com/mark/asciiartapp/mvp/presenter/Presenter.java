package com.mark.asciiartapp.mvp.presenter;

import com.mark.asciiartapp.mvp.model.base.Model;
import com.mark.asciiartapp.mvp.view.BaseView;

public interface Presenter<M extends Model, V extends BaseView> {

    void registerModel(M Model);

    void registerView(V view);

    V getView();

    void destory();
}