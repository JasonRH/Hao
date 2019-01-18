package com.example.rh.daily.gank.expand;

import java.util.List;

/**
 * @author RH
 * @date 2019/1/17
 */
public class IExpand {

    interface View {

        void onLoadNewData(List<ExpandBean> list);

        void onLoadMoreData(List<ExpandBean> list);

        void stopLoading();

        void showToast(String s);

    }

    interface Presenter {

        void loadData(int page);

    }
}
