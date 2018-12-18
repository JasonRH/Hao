package com.example.rh.core.fragment.bottom;

import android.widget.Toast;

import com.example.rh.core.R;
import com.example.rh.core.app.MyApp;
import com.example.rh.core.base.BaseDelegate;
import com.example.rh.core.base.BasePresenter;

/**
 * @author RH
 * @date 2018/10/24
 */
public abstract class BottomItemFragment<T extends BasePresenter> extends BaseDelegate<T> {
    private long touchTime = 0;
    private final int WAIT_TIME = 2000;

    /**
     * 使用fragmentation原生方法，实现双击退出
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - touchTime < WAIT_TIME) {
            _mActivity.finish();
        } else {
            touchTime = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出" + MyApp.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
