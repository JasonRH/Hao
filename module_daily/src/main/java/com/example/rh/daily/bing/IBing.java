package com.example.rh.daily.bing;


import java.util.List;

/**
 * @author RH
 * @date 2018/4/9
 */
public interface IBing {
    interface View {

        void onUpdateUI(List<BingDailyBean> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData();

    }
}
