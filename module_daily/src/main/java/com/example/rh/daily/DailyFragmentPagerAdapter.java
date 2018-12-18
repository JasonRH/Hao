package com.example.rh.daily;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author RH
 * @date 2018/3/29
 */
public class DailyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    private String[] strings;

    public DailyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] strings) {
        super(fm);
        this.fragmentList = fragmentList;
        this.strings = strings;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList != null ? fragmentList.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }
}
