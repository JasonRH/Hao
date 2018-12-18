package com.example.rh.core.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.view.MotionEvent;

import com.example.rh.core.R;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @author RH
 * @date 2018/8/22
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements ISupportActivity {

    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);
    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate(savedInstanceState);

        setPresenter();
        attachView();

        //代码创建FrameLayout布局
        initContainer(savedInstanceState);
    }

    private void initContainer(Bundle savedInstanceState) {
        //V7 包中的FrameLayout有异常,可以使用FrameLayout
        @SuppressLint("RestrictedApi") final ContentFrameLayout contentFrameLayout = new ContentFrameLayout(this);
        contentFrameLayout.setId(R.id.delegate_container);
        setContentView(contentFrameLayout);
        if (savedInstanceState == null) {
            //加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
            //fragmentation 中封装的方法
            mDelegate.loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    /**
     * 传入第一个Fragment
     */
    protected abstract BaseFragment setRootDelegate();

    protected abstract void setPresenter();

    private void attachView() {
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        mDelegate.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return mDelegate;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return mDelegate.extraTransaction();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
    }

    @Override
    final public void onBackPressed() {
        mDelegate.onBackPressed();
    }

    @Override
    public void onBackPressedSupport() {
        mDelegate.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimator();
    }

    @Override
    public void post(Runnable runnable) {
        mDelegate.post(runnable);
    }
}
