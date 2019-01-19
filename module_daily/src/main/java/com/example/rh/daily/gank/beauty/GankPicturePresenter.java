package com.example.rh.daily.gank.beauty;

import android.accounts.NetworkErrorException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.net_rx.RxRetrofitClient;
import com.example.rh.daily.gank.beauty.IGank.Presenter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @author RH
 * @date 2019/1/16
 */
public class GankPicturePresenter extends BasePresenter<IGank.View> implements Presenter {
    private CompositeDisposable compositeDisposable;

    public GankPicturePresenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadData(int page) {
        RxRetrofitClient.builder()
                .url("http://gank.io/api/data/福利/10/" + page)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        JSONArray jsonArray = JSON.parseObject(s).getJSONArray("results");
                        int size = jsonArray.size();
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String url = object.getString("url");
                            list.add(url);
                        }
                        if (page > 1) {
                            getMyView().onLoadMoreData(list);
                        } else {
                            getMyView().onLoadNewData(list);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
                            getMyView().showToast("请求超时，稍后再试");
                        } else if (e instanceof ConnectException || e instanceof NetworkErrorException || e instanceof UnknownHostException) {
                            getMyView().showToast("网络异常，稍后再试");
                        } else if (e instanceof HttpException) {
                            getMyView().showToast("服务器异常，稍后再试");
                        } else if (e instanceof NullPointerException) {
                            //特殊处理
                            e.printStackTrace();
                        } else {
                            //_onError("请求失败，稍后再试");
                            getMyView().showToast("网络数据获取失败");
                        }

                        getMyView().stopLoading();
                    }

                    @Override
                    public void onComplete() {
                        getMyView().stopLoading();
                    }
                });
    }
}
