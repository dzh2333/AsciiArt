package com.mark.asciiartapp.mvp;

import com.mark.asciiartapp.mvp.model.base.Model;
import com.mark.asciiartapp.mvp.presenter.BasePresenter;
import com.mark.asciiartapp.mvp.view.BaseView;

public interface BaseMvp<M extends Model, V extends BaseView, P extends BasePresenter> {

    M createModel();

    V createView();

    P createPresenter();
}
