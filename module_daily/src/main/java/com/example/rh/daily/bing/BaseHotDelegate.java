package com.example.rh.daily.bing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rh.core.base.BaseDelegate;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.daily.R;

/**
 * @author RH
 * @date 2018/3/29
 */
public abstract class BaseHotDelegate<T extends BasePresenter> extends BaseDelegate<T> implements SwipeRefreshLayout.OnRefreshListener {

    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected Object setLayout() {
        return R.layout.base_fragment_hotchpotch;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        initView(rootView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
    }

}
