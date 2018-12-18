package com.example.rh.core.fragment.web;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.rh.core.app.ConfigType;
import com.example.rh.core.app.MyApp;
import com.example.rh.core.base.BaseDelegate;
import com.example.rh.core.base.BaseFragment;
import com.example.rh.core.fragment.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author RH
 * @date 2018/11/1
 */
public abstract class BaseWebFragment extends BaseFragment implements IWebViewInitialize {
    private WebView mWebView = null;
    private ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String mUrl = null;
    private boolean mIsWebAvailable = false;
    private BaseFragment mTopFragment = null;

    public BaseWebFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUrl = bundle.getString(RouteKeys.URL.name());
        }
        initWebView();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) {
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    /**
     * 子类返回接口使用对象
     */
    public abstract IWebViewInitialize setInitialize();

    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            final IWebViewInitialize initialize = setInitialize();
            if (initialize != null) {
                //直接new出WebView,避免内存泄露
                final WeakReference<WebView> webViewWeakReference =
                        new WeakReference<>(new WebView(getContext()), WEB_VIEW_QUEUE);
                mWebView = webViewWeakReference.get();
                mWebView = initialize.initWebView(mWebView);
                mWebView.setWebViewClient(initialize.initWebViewClient());
                mWebView.setWebChromeClient(initialize.initWebChromeClient());
                final String name = MyApp.getConfiguration(ConfigType.JAVASCRIPT_INTERFACE);
                /**
                 * 添加javascriptInterface
                 * 第一个参数：这里需要一个与js映射的java对象
                 * 第二个参数：该java对象被映射为js对象后在js里面的对象名，在js中要调用该对象的方法就是通过这个来调用
                 */
                mWebView.addJavascriptInterface(MyWebInterface.create(this), name);
                mIsWebAvailable = true;
            } else {
                throw new NullPointerException("IWebViewInitialize is null");
            }
        }
    }

    public WebView getmWebView() {
        if (mWebView == null) {
            throw new NullPointerException("mWebView is Null");
        }
        return mIsWebAvailable ? mWebView : null;
    }

    public String getmUrl() {
        if (mUrl == null) {
            throw new NullPointerException("mUrl is Null");
        }
        return mUrl;
    }

    public void setTopFragment(BaseDelegate fragment) {
        mTopFragment = fragment;
    }

    public BaseFragment getmTopFragment() {
        if (mTopFragment == null) {
            //如果没有设置TopFragment则以此Fragment为父布局
            mTopFragment = this;
        }
        return mTopFragment;
    }
}
