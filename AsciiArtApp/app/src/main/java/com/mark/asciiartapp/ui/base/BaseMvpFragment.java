package com.mark.asciiartapp.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.mark.asciiartapp.mvp.BaseMvp;
import com.mark.asciiartapp.mvp.model.base.Model;
import com.mark.asciiartapp.mvp.presenter.BasePresenter;
import com.mark.asciiartapp.mvp.view.BaseView;

public abstract class BaseMvpFragment<M extends Model, V extends BaseView, P extends BasePresenter> extends Fragment implements BaseMvp<M, V, P> {

    private P presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter = createPresenter();
        if (presenter != null){
            presenter.registerModel(createModel());
            presenter.registerView(createView());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null){
            presenter.destory();
        }
    }
}
