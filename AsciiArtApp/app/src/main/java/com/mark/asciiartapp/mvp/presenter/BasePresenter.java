package com.mark.asciiartapp.mvp.presenter;

import com.mark.asciiartapp.mvp.model.base.Model;
import com.mark.asciiartapp.mvp.view.BaseView;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<M extends Model, V extends BaseView> implements Presenter <M, V>{

    private WeakReference<V> wrk;
    protected M model;

    @Override
    public void registerModel(M Model) {
        this.model = Model;
    }

    @Override
    public void registerView(V view) {
        wrk = new WeakReference<>(view);
    }

    @Override
    public V getView() {
        return wrk == null ? null : wrk.get();
    }

    @Override
    public void destory() {
        if (wrk != null){
            wrk.clear();
            wrk = null;
        }
        onViewDestory();
    }

    protected abstract void onViewDestory();
}
