package com.example.rh.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author RH
 * @date 2018/8/22
 *
 * 需要使用Presenter的Fragment继承该类
 */
public abstract class BaseDelegate<T extends BasePresenter> extends BaseCheckerDelegate {

    protected T presenter;

    /**
     * 得到父布局
     */
    @SuppressWarnings("unchecked")
    public <F extends BaseDelegate> F getMyParentFragment() {
        return (F) getParentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = setPresenter();
        attachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*
         * 解绑Fragment和Presenter
         */
        if (presenter != null) {
            presenter.detachView();
        }
    }

    protected abstract T setPresenter();

    /**
     * 绑定Fragment和Presenter
     */
    private void attachView() {
        if (presenter != null) {
            presenter.attachView(this);
        }
    }
}
