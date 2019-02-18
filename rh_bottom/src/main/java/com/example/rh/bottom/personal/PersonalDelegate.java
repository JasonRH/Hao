package com.example.rh.bottom.personal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.rh.bottom.R;
import com.example.rh.bottom.R2;
import com.example.rh.bottom.personal.list.ListAdapter;
import com.example.rh.bottom.personal.list.ListBean;
import com.example.rh.bottom.personal.list.ListItemType;
import com.example.rh.bottom.personal.settings.AboutDelegate;
import com.example.rh.bottom.personal.settings.SettingsDelegate;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.fragment.bottom.BottomItemFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author RH
 * @date 2018/12/11
 */
public class PersonalDelegate extends BottomItemFragment {

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRvSettings;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {

        final ListBean file = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_ICON_TEXT)
                .setId(0)
                .setImageUrl("{icon-wenjian1}")
                .setText("文件管理")
                .build();

        final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_ICON_TEXT)
                .setId(1)
                .setmFragment(new SettingsDelegate())
                .setImageUrl("{icon-setting}")
                .setText("系统设置")
                .build();

        final ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_ICON_TEXT)
                .setId(2)
                .setmFragment(new AboutDelegate())
                .setImageUrl("{icon-intro1}")
                .setText("APP简介")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(file);
        data.add(system);
        data.add(about);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }
}
