package com.example.rh.module.tools;

import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.rh.core.app.MyApp;
import com.example.rh.core.base.BaseDelegate;

/**
 * @author RH
 * @date 2019/1/4
 */
public class ToolsClickListener extends SimpleClickListener {
    private final BaseDelegate DELEGATE;

    public ToolsClickListener(BaseDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ToolBean bean = (ToolBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                if (bean.getDelegate() != null) {
                    DELEGATE.getMyParentFragment().getSupportDelegate().start(bean.getDelegate());
                }else {
                    Toast.makeText(MyApp.getApplicationContext(),"功能开发中，敬请期待！",Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                //照相机
                DELEGATE.startCameraWithCheck();
                break;
            default:
                if (bean.getDelegate() != null) {
                    //DELEGATE.getSupportDelegate().start(bean.getDelegate());
                    DELEGATE.getMyParentFragment().getSupportDelegate().start(bean.getDelegate());
                }else {
                    Toast.makeText(MyApp.getApplicationContext(),"功能开发中，敬请期待！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
