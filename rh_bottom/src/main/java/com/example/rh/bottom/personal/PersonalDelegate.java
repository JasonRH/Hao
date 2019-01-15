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


        /*final ToolBean address = new ToolBean.Builder()
                .setItemType(ToolItemType.ITEM_NORMAL)
                .setId(1)
                .setText("使用帮助")
                //.setmFragment(new AddressFragment())
                .build();

        final ToolBean system = new ToolBean.Builder()
                .setItemType(ToolItemType.ITEM_NORMAL)
                .setId(2)
                //.setmFragment(new SettingsFragment())
                .setText("系统设置")
                .build();*/

        final List<ListBean> data = new ArrayList<>();
        data.add(file);
        //data.add(address);
        //data.add(system);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvSettings.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRvSettings.setAdapter(adapter);
        mRvSettings.addOnItemTouchListener(new PersonalClickListener(this));
    }
}
