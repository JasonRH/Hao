package com.example.rh.core.net_rx;

import android.content.Context;

import com.example.rh.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author RH
 * @date 2018/10/16
 */
public class RxRetrofitClientBuilder {
    /**浅拷贝，会改变原对象的值*/
    private Map<String, Object> params = RxRetrofitCreator.getParams();
    private String mUrl = null;
    private RequestBody mBody = null;
    private File file = null;
    private LoaderStyle mLoaderStyle = null;
    private Context mContext = null;

    public final RxRetrofitClientBuilder url(String mUrl) {
        this.mUrl = mUrl;
        return this;
    }

    public final RxRetrofitClientBuilder params(WeakHashMap<String, Object> mParams) {
        params.putAll(mParams);
        return this;
    }

    public final RxRetrofitClientBuilder params(String key, Object valus) {
        params.put(key, valus);
        return this;
    }

    public final RxRetrofitClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    public final RxRetrofitClientBuilder file(File file) {
        this.file = file;
        return this;
    }

    public final RxRetrofitClientBuilder file(String filePath) {
        this.file = new File(filePath);
        return this;
    }

    public final RxRetrofitClientBuilder loader(Context context) {
        this.mContext = context;
        //默认一种Dialog样式
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRetrofitClientBuilder loader(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }

    public final RxRetrofitClient build() {
        return new RxRetrofitClient(mUrl, params,mBody, file, mLoaderStyle, mContext);
    }
}
