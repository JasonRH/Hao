package com.example.rh.daily.gank.beauty;

import com.example.rh.daily.bing.BingDailyBean;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/16
 */
public class IGank {
    interface View {

        void onLoadNewData(List<String> list);

        void onLoadMoreData(List<String> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData(int page);

    }
}
