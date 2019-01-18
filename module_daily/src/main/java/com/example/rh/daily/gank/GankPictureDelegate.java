package com.example.rh.daily.gank;

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
 * @date 2019/1/16
 */
public class GankPictureDelegate extends BaseHotDelegate<GankPicturePresenter> implements IGank.View {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<String> mPictureList = new ArrayList<>();
    private GankPictureAdapter adapter;
    private int page = 1;

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

        //加载更多
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (presenter != null) {
                    presenter.loadData(page);
                }
            }
        }, recyclerView);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        page = 1;
        presenter.loadData(page);
    }

    @Override
    public void onLoadNewData(List<String> list) {
        if (list != null && list.size() != 0) {
            page++;
            adapter.setNewData(list);
        }
    }

    @Override
    public void onLoadMoreData(List<String> list) {
        if (list == null || list.size() == 0) {
            //加载完成
            adapter.loadMoreEnd();
        } else {
            page++;
            adapter.addData(list);
            adapter.loadMoreComplete();
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
            adapter.loadMoreFail();
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
