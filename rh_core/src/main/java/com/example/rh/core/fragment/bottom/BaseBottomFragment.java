package com.example.rh.core.fragment.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.rh.core.R;
import com.example.rh.core.R2;
import com.example.rh.core.base.BaseDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;


/**
 * @author RH
 * @date 2018/10/24
 */
public abstract class BaseBottomFragment extends BaseDelegate implements View.OnClickListener {
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<BottomItemFragment> ITEM_FRAGMENT = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemFragment> ITEMS = new LinkedHashMap<>();
    private int mCurrentFragment = 0;
    private int mIndexFragment = 0;
    private int mClickedColor = Color.RED;

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar;

    protected abstract LinkedHashMap<BottomTabBean, BottomItemFragment> setItems(ItemBuilder builder);

    /**
     * 当前显示的Fragment
     */
    protected abstract int setIndexFragment();

    /**
     * 底部导航栏点击后的颜色
     */
    @ColorInt
    protected abstract int setClickedColor();

    @Override
    protected Object setLayout() {
        return R.layout.fragment_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexFragment = setIndexFragment();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }
        final ItemBuilder itemBuilder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemFragment> items = setItems(itemBuilder);
        ITEMS.putAll(items);
        //遍历Map
        for (Map.Entry<BottomTabBean, BottomItemFragment> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemFragment value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_FRAGMENT.add(value);
        }
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            //mBottomBar：父容器
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            //将父布局（mBottomBar）里的每个RelativeLayout（bottom_item_icon_text_layout）依次取出
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            //获取对应的布局
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEANS.get(i);
            //初始化数据
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            //初始化时加载颜色
            if (i == mIndexFragment) {
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }
        //yokeyword.fragmentation依赖包中的SupportFragment
        final ISupportFragment[] fragmentArray = ITEM_FRAGMENT.toArray(new ISupportFragment[size]);
        //加载Fragment
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexFragment, fragmentArray);
    }

    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        itemIcon.setTextColor(mClickedColor);
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
        //注意先后顺序
        getSupportDelegate().showHideFragment(ITEM_FRAGMENT.get(tag), ITEM_FRAGMENT.get(mCurrentFragment));
        mCurrentFragment = tag;
    }
}
