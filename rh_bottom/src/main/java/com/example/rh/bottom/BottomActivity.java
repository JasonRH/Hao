package com.example.rh.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.rh.core.base.BaseActivity;
import com.example.rh.core.base.BaseFragment;

import qiu.niorgai.StatusBarCompat;

/**
 * @author RH
 * @date 2018/12/5
 */
public class BottomActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实现沉浸式状态栏
        //StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    protected BaseFragment setRootDelegate() {
        return new BottomFragment();
    }

    @Override
    protected void setPresenter() {

    }
}
