package com.example.rh.bottom.personal.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.rh.bottom.R;
import com.example.rh.bottom.R2;
import com.example.rh.core.base.BaseDelegate;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.net_rx.RxRetrofitClient;
import com.example.rh.core.ui.loader.MyLoader;

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
    @BindView(R2.id.tv_info)
    AppCompatTextView info = null;
    @BindView(R2.id.tv_adress)
    AppCompatTextView address = null;
    @BindView(R2.id.tv_contact)
    AppCompatTextView contact = null;
    @BindView(R2.id.tv_api)
    AppCompatTextView api = null;

    @Override
    protected Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        info.setText(getString(R.string.app_info));
        address.setText(getString(R.string.app_address));
        contact.setText(getString(R.string.app_contact));
        api.setText(getString(R.string.about_api));


    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
