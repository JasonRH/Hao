package com.example.rh.daily.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.rh.daily.bing.BaseHotDelegate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2019/1/16
 */
public class GankPictureDelegate extends BaseHotDelegate<GankPicturePresenter> implements IGank.View{
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<String> mPictureList = new ArrayList<>();
    private GankPictureAdapter adapter;

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.loadData();
    }

    @Override
    protected GankPicturePresenter setPresenter() {
        return new GankPicturePresenter(compositeDisposable);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        adapter = new GankPictureAdapter(mPictureList);
        recyclerView.setAdapter(adapter);
        onRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //切断所有订阅事件，防止内存泄漏。
        compositeDisposable.clear();
        //取消Fragment和Presenter之间的关联
    }

    @Override
    public void onUpdateUI(List<String> list) {
        mPictureList.clear();
        mPictureList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String s) {

    }
}
