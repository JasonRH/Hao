package com.example.rh.core.fragment.web;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.rh.core.fragment.web.client.WebChromeClientImpl;
import com.example.rh.core.fragment.web.client.WebViewClientImpl;
import com.example.rh.core.fragment.web.route.RouteKeys;
import com.example.rh.core.fragment.web.route.Router;

/**
 * @author RH
 * @date 2018/11/2
 */
public class WebFragmentImpl extends BaseWebFragment {
    private IPageLoadListener mPageLoadListener = null;

    public static WebFragmentImpl create(String url) {
        final Bundle bundle = new Bundle();
        bundle.putString(RouteKeys.URL.name(), url);
        final WebFragmentImpl fragment = new WebFragmentImpl();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected Object setLayout() {
        return getmWebView();
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        if (getmUrl() != null) {
            //用原生的方式模拟Web跳转并进行页面加载
            Router.getInstance().loadPage(this, getmUrl());
        }
    }

    @Override
    public IWebViewInitialize setInitialize() {
        //返回接口实现对象
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        //初始化WebView，做出相应的设置
        return new WebViewInitialize().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        //帮助WebView处理各种通知、请求事件
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(mPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        //辅助WebView处理JavaScript的对话框、网站图标、网站title、加载进度等
        return new WebChromeClientImpl();
    }

    /**
     * 页面加载进度回调，可在详情页中实现
     */
    public void setPageLoadListener(IPageLoadListener loadListener) {
        this.mPageLoadListener = loadListener;
    }
}
