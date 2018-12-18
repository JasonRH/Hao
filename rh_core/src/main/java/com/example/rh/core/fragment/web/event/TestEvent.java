package com.example.rh.core.fragment.web.event;

import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.rh.core.utils.log.MyLogger;

/**
 * @author RH
 * @date 2018/11/2
 */

//TODO:示例类，未使用，具体在主Moudule实现
public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_SHORT).show();
        if (getAction().equals("test")) {
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    //调用js方法
                    webView.evaluateJavascript("nativeCall();", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            //此处为 js 返回的结果
                            MyLogger.d("TestEvent", s);
                        }
                    });
                }
            });
        }
        return null;
    }
}
