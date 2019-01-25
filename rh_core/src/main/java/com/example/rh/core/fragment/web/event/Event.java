package com.example.rh.core.fragment.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.example.rh.core.fragment.web.BaseWebFragment;

/**
 * @author RH
 * @date 2018/11/2
 */
public abstract class Event implements IEvent {
    private Context mContext = null;
    private String mAction = null;
    private BaseWebFragment mFagment = null;
    private String mUrl = null;

    public WebView getWebView() {
        return mFagment.getmWebView();
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String mAction) {
        this.mAction = mAction;
    }


    public void setFagment(BaseWebFragment mFagment) {
        this.mFagment = mFagment;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
