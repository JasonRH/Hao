package com.example.rh.daily.wanandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.rh.daily.fragment.BaseHotDelegate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2019/1/17
 */
public class WanDelegate extends BaseHotDelegate<WanPresenter> implements IWan.View {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<WanBean> dataList = new ArrayList<>();
    private WanAdapter adapter;
    private int page = 0;

    @Override
    protected WanPresenter setPresenter() {
        return new WanPresenter(compositeDisposable);
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        adapter = new WanAdapter(dataList);
        recyclerView.setAdapter(adapter);
        onRefresh();
        //开启动画
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);

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
        page = 0;
        presenter.loadData(page);
    }

    @Override
    public void onLoadNewData(List<WanBean> list) {
        if (list != null && list.size() != 0) {
            page++;
            adapter.setNewData(list);
        }
    }

    @Override
    public void onLoadMoreData(List<WanBean> list) {
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
        if (page > 0) {
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
