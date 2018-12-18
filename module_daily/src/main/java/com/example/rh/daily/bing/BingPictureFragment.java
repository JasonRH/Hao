package com.example.rh.daily.bing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2018/1/23
 */
public class BingPictureFragment extends BaseHotchpotchFragment<BingPicturePresenter> implements IBing.View {
    private List<BingDailyBean> bingDailyBeanList = new ArrayList<>();
    private BingPictureAdapter bingPictureAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected BingPicturePresenter setPresenter() {
        return new BingPicturePresenter(compositeDisposable);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        bingPictureAdapter = new BingPictureAdapter(bingDailyBeanList);
        recyclerView.setAdapter(bingPictureAdapter);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.loadData();
    }

    @Override
    public void onUpdateUI(List<BingDailyBean> list) {
        bingDailyBeanList.clear();
        bingDailyBeanList.addAll(list);
        bingPictureAdapter.notifyDataSetChanged();
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //切断所有订阅事件，防止内存泄漏。
        compositeDisposable.clear();
        //取消Fragment和Presenter之间的关联
    }
}
