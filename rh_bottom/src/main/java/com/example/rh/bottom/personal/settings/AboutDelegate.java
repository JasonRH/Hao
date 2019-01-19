package com.example.rh.bottom.personal.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.rh.bottom.R;
import com.example.rh.bottom.R2;
import com.example.rh.core.base.BaseDelegate;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.net_rx.RxRetrofitClient;
import com.example.rh.core.utils.log.MyLogger;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author RH
 * @date 2018/11/10
 */
public class AboutDelegate extends BaseDelegate {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R2.id.tv_info)
    AppCompatTextView mTextView = null;

    @Override
    protected Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        RxRetrofitClient.builder()
                .loader(getContext())
                .url("https://github.com/JasonRH/Hao/blob/master/about_app.json")
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        final String info = JSON.parseObject(s).getString("toolData");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            mTextView.setText(Html.fromHtml(info,Html.FROM_HTML_MODE_COMPACT));
                        }else {
                            mTextView.setText(info);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
