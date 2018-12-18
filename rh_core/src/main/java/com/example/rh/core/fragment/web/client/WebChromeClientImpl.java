package com.example.rh.core.fragment.web.client;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * @author RH
 * @date 2018/11/2
 *
 * 辅助WebView处理JavaScript的对话框、网站图标、网站title、加载进度等
 */
public class WebChromeClientImpl extends WebChromeClient{

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }
}
