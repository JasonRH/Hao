package com.example.rh.core.utils.callback;

/**
 * @author RH
 * @date 2018/11/8
 * <p>
 * 通用回调接口
 */
public interface IGlobalCallback<T> {
    void executeCallback(T args);
}
