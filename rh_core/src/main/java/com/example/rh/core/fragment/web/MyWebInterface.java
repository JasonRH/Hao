package com.example.rh.core.fragment.web;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.rh.core.fragment.web.event.Event;
import com.example.rh.core.fragment.web.event.EventManager;

/**
 * @author RH
 * @date 2018/11/2
 * <p>
 * 与js映射的java对象 ,在该对象里实现给js调用的方法
 * 暴露的方法只需用JavascriptInterface标签注明即可
 */
class MyWebInterface {
    private final BaseWebFragment fragment;

    public MyWebInterface(BaseWebFragment fragment) {
        this.fragment = fragment;
    }

    static MyWebInterface create(BaseWebFragment fragment) {
        return new MyWebInterface(fragment);
    }

    @JavascriptInterface
    public String event(String params) {
        //js调用内部原生方法，在此统一处理
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().getEvent(action);
        if (event != null) {
            event.setAction(action);
            event.setFagment(fragment);
            event.setContext(fragment.getContext());
            event.setUrl(fragment.getmUrl());
            //根据action，调用具体的实现类
            return event.execute(params);
        }
        return null;
    }

    //TODO:测试方法，无意义
    @JavascriptInterface
    public void showToast(String s) {
        Toast.makeText(fragment.getContext(), s, Toast.LENGTH_SHORT).show();
    }

}
