package com.example.rh.bottom;

import android.graphics.Color;

import com.example.rh.bottom.personal.PersonalDelegate;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.fragment.bottom.BaseBottomFragment;
import com.example.rh.core.fragment.bottom.BottomItemFragment;
import com.example.rh.core.fragment.bottom.BottomTabBean;
import com.example.rh.core.fragment.bottom.ItemBuilder;
import com.example.rh.daily.DailyDelegate;
import com.example.rh.module.tools.ToolsDelegate;

import java.util.LinkedHashMap;

/**
 * @author RH
 * @date 2018/12/10
 */
public class BottomFragment extends BaseBottomFragment {
    @Override
    protected LinkedHashMap<BottomTabBean, BottomItemFragment> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomItemFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{icon-shouye}", "主页"), new DailyDelegate());
        items.put(new BottomTabBean("{icon-gongjuxiang}", "工具箱"), new ToolsDelegate());
        items.put(new BottomTabBean("{icon-wode}", "关于"), new PersonalDelegate());
        return builder.addItem(items).build();
    }

    @Override
    protected int setIndexFragment() {
        return 0;
    }

    @Override
    protected int setClickedColor() {
        return Color.parseColor("#3393D5");
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
