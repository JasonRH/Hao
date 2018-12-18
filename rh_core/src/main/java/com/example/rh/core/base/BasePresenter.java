package com.example.rh.core.base;

import java.lang.ref.WeakReference;

/**
 * @author RH
 * @date 2018/3/20
 */
public class BasePresenter<V> {
    private WeakReference<V> weakReference;

    /**
     * 将View和Presenter进行关联
     *
     * 该View（V）在初始化Presenter的时候传入,例如extends BasePresenter<IExample.View>
     * 传入接口则获取接口,传入fragment则获取fragment
     *
     * 本程序传入的view一般为 传入接口的实现对象
     */
    public void attachView(V view) {
        weakReference = new WeakReference<>(view);
    }

    /**
     * 解除关联
     */
    public void detachView() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }

    /**
     * 获取对应的View
     */
    protected V getMyView() {
        return weakReference.get();
    }
}
