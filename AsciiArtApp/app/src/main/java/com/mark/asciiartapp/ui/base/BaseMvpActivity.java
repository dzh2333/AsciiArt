package com.mark.asciiartapp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mark.asciiartapp.mvp.BaseMvp;
import com.mark.asciiartapp.mvp.model.base.Model;
import com.mark.asciiartapp.mvp.presenter.BasePresenter;
import com.mark.asciiartapp.mvp.view.BaseView;

import butterknife.ButterKnife;

public abstract class BaseMvpActivity<M extends Model, V extends BaseView, P extends BasePresenter> extends AppCompatActivity implements BaseMvp<M, V, P> {

    protected P presenter;
    private int layout_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getProviderId());
        ButterKnife.bind(this);
        presenter = createPresenter();
        if (presenter != null){
            presenter.registerModel(createModel());

            presenter.registerView(createView());
        }
    }
    protected abstract int getProviderId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null){
            presenter.destory();
        }
    }

}
