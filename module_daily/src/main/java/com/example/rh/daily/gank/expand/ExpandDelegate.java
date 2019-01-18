package com.example.rh.daily.gank.expand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.rh.core.fragment.web.WebFragmentImpl;
import com.example.rh.daily.R;
import com.example.rh.daily.fragment.BaseHotDelegate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2019/1/17
 */
public class ExpandDelegate extends BaseHotDelegate<ExpandPresenter> implements IExpand.View {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<ExpandBean> dataList = new ArrayList<>();
    private ExpandAdapter adapter;
    private int page = 1;

    @Override
    protected ExpandPresenter setPresenter() {
        return new ExpandPresenter(compositeDisposable);
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        adapter = new ExpandAdapter(dataList);
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
        page = 1;
        presenter.loadData(page);
    }

    @Override
    public void onLoadNewData(List<ExpandBean> list) {
        if (list != null && list.size() != 0) {
            page++;
            adapter.setNewData(list);
        }
    }

    @Override
    public void onLoadMoreData(List<ExpandBean> list) {
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
