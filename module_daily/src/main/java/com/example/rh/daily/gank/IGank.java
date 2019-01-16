package com.example.rh.daily.gank;

import com.example.rh.daily.bing.BingDailyBean;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/16
 */
public class IGank {
    interface View {

        void onUpdateUI(List<String> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData();

    }
}
