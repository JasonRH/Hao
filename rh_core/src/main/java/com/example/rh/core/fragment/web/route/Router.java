package com.example.rh.core.fragment.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.example.rh.core.base.BaseFragment;
import com.example.rh.core.fragment.web.BaseWebFragment;
import com.example.rh.core.fragment.web.WebFragmentImpl;

/**
 * @author RH
 * @date 2018/11/2
 * <p>
 * 路由类
 */
public class Router {
    private Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(BaseWebFragment fragment, String url) {
        //如果是电话协议
        if (url.contains("tel:")) {
            callPhone(fragment.getContext(), url);
            return true;
        }

        //在topFragment的基础上代开新的页面，此次给定的是EcBottomFragment
        final BaseFragment topFragment = fragment.getmTopFragment();

        //打开新的页面
        final WebFragmentImpl webFragment = WebFragmentImpl.create(url);

        topFragment.getSupportDelegate().start(webFragment);
        return true;
    }

    private void loadWebPage(WebView webView, String url) {
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("webView is null");
        }
    }

    private void loadLocalPage(WebView webView, String url) {
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    public void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public void loadPage(BaseWebFragment fragment, String url) {
        loadPage(fragment.getmWebView(), url);
    }

    private void callPhone(Context context, String uri) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }
}
