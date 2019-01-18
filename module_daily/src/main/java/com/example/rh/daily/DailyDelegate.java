package com.example.rh.daily;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.fragment.bottom.BottomItemFragment;
import com.example.rh.daily.bing.BingPictureDelegate;
import com.example.rh.daily.gank.GankPictureDelegate;
import com.example.rh.daily.gank.expand.ExpandDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RH
 */
public class DailyDelegate extends BottomItemFragment {
    private ViewPager viewPager;


    @Override
    protected Object setLayout() {
        return R.layout.fragment_hotchpotch;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }


    protected void initView(View view) {
        TabLayout tabLayout = view.findViewById(R.id.hotchpotch_tab_layout);
        viewPager = view.findViewById(R.id.hotchpotch_fragment_viewpager);
        tabLayout.setupWithViewPager(viewPager);
        //标签全部显示
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //限定预加载的页面个数
        viewPager.setOffscreenPageLimit(5);

        initFragmentPagerAdapter();
    }

    private void initFragmentPagerAdapter() {
        List<Fragment> fragmentList = new ArrayList<>();
        String[] strings = new String[]{"文章", "必应", "美女"};
        fragmentList.add(new ExpandDelegate());
        fragmentList.add(new BingPictureDelegate());
        fragmentList.add(new GankPictureDelegate());
        DailyFragmentPagerAdapter pagerAdapter = new DailyFragmentPagerAdapter(getChildFragmentManager(), fragmentList, strings);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
