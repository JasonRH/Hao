package com.example.rh.daily.bing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.rh.daily.fragment.BaseHotDelegate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2018/1/23
 */
public class BingPictureDelegate extends BaseHotDelegate<BingPicturePresenter> implements IBing.View {
    private List<BingDailyBean> bingDailyBeanList = new ArrayList<>();
    private BingPictureAdapter bingPictureAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int page = 1;

    @Override
    protected BingPicturePresenter setPresenter() {
        return new BingPicturePresenter(compositeDisposable);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        bingPictureAdapter = new BingPictureAdapter(bingDailyBeanList);
        recyclerView.setAdapter(bingPictureAdapter);
        //加载更多
        bingPictureAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (presenter != null) {
                    presenter.loadData(page);
                }
            }
        }, recyclerView);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        page = 1;
        presenter.loadData(page);
    }

    @Override
    public void onLoadNewData(List<BingDailyBean> list) {
        if (list != null && list.size() != 0) {
            page++;
            bingPictureAdapter.setNewData(list);
        }
    }

    @Override
    public void onLoadMoreData(List<BingDailyBean> list) {
        if (list == null || list.size() == 0) {
            //加载完成
            bingPictureAdapter.loadMoreEnd();
        } else {
            page++;
            bingPictureAdapter.addData(list);
            bingPictureAdapter.loadMoreComplete();
        }
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String s) {
        if (page > 1) {
            //加载失败
            bingPictureAdapter.loadMoreFail();
        } else {
            Toast.makeText(this.getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //切断所有订阅事件，防止内存泄漏。
        compositeDisposable.clear();
        //取消Fragment和Presenter之间的关联
    }
}
