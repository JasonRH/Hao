package com.example.rh.bottom.personal.settings;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rh.bottom.R;
import com.example.rh.bottom.R2;
import com.example.rh.bottom.personal.list.ListAdapter;
import com.example.rh.bottom.personal.list.ListBean;
import com.example.rh.bottom.personal.list.ListItemType;
import com.example.rh.core.base.BaseDelegate;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.utils.cache.CacheUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author RH
 * @date 2019/1/18
 */
public class SettingsDelegate extends BaseDelegate {

    @BindView(R2.id.delegate_settings_recycler)
    RecyclerView mRecyclerView = null;
    String cache = "0.0B";

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
       /* final ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(1)
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_OPEN_PUSH).executeCallback(null);
                            Toast.makeText(getContext(), "打开推送", Toast.LENGTH_SHORT).show();
                        } else {
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_STOP_PUSH).executeCallback(null);
                            Toast.makeText(getContext(), "关闭推送", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setText("消息推送")
                .build();*/

        try {
            cache = CacheUtils.getTotalCacheSize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_ICON_TEXT)
                .setId(2)
                .setText("清除缓存")
                .setValue(cache)
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(about);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new SettingsClickListener(this));
    }
}
