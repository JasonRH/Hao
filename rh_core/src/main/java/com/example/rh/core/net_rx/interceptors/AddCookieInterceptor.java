package com.example.rh.core.net_rx.interceptors;

import android.annotation.SuppressLint;

import com.example.rh.core.utils.storage.MySharedPreferences;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author RH
 * @date 2018/11/3
 */
public class AddCookieInterceptor implements Interceptor {
    @SuppressLint("CheckResult")
    @Override
    public Response intercept(@android.support.annotation.NonNull Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        //当SharedPreferences中保存的有Web端的Cookie时，将其取出，加入到请求头中
        Observable
                .just(MySharedPreferences.getCustomAppProfile("cookie"))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String cookie) throws Exception {
                        //给原生API请求附带上WebView拦截下来的Cookie
                        builder.addHeader("Cookie", cookie);
                    }
                });

        return chain.proceed(builder.build());
    }
}
