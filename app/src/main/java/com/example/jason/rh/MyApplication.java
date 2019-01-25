package com.example.jason.rh;

import android.app.Application;

import com.example.rh.core.app.MyApp;
import com.example.rh.core.net_rx.interceptors.DebugInterceptor;
import com.example.rh.ui.icon.MyFontAlibabaModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @author RH
 * @date 2018/12/5
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.init(this)
                .withApiHost("https://uland.taobao.com")
                .withIcon(new MyFontAlibabaModule())
                .withInterceptor(new DebugInterceptor("myTest", R.raw.test))
                .configure();

        //初始化logger
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
