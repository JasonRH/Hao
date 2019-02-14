package com.example.rh.daily.wanandroid;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/17
 */
public class IWan {

    interface View {

        void onLoadNewData(List<WanBean> list);

        void onLoadMoreData(List<WanBean> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData(int page);

    }
}
