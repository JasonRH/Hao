package com.example.rh.bottom.personal.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.rh.bottom.R;
import com.example.rh.bottom.personal.list.ListBean;
import com.example.rh.core.base.BaseDelegate;
import com.example.rh.core.utils.cache.CacheUtils;
import com.example.rh.core.utils.log.MyLogger;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.Objects;

/**
 * @author RH
 * @date 2018/11/10
 */
public class SettingsClickListener extends SimpleClickListener {
    private final BaseDelegate DELEGATE;

    public SettingsClickListener(BaseDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                //这是消息推送的开关
                //不需要打开新的Fragment
                break;
            case 2:
                try {
                    IconTextView cacahe =view.findViewById(R.id.icon_arrow);
                    CacheUtils.clearAllCache(Objects.requireNonNull(DELEGATE.getContext()));
                    String cache = CacheUtils.getTotalCacheSize(DELEGATE.getContext());
                    cacahe.setText(cache);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
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
