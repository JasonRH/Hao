package com.example.rh.daily.bing;

import android.accounts.NetworkErrorException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.net_rx.RxRetrofitClient;

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
 * @date 2018/4/9
 */
public class BingPicturePresenter extends BasePresenter<IBing.View> implements IBing.Presenter {
    private CompositeDisposable compositeDisposable;

    public BingPicturePresenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void loadData() {
        RxRetrofitClient.builder()
                .url("http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n= + 8")
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
                        JSONArray array = JSON.parseObject(s).getJSONArray("images");
                        int size = array.size();
                        List<BingDailyBean> bingDailyBeans = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            JSONObject object = array.getJSONObject(i);
                            String url = object.getString("url");
                            String startdate = object.getString("startdate");
                            String copyright = object.getString("copyright");
                            BingDailyBean dailyBean = new BingDailyBean();
                            dailyBean.setUrl(url);
                            dailyBean.setDate(startdate);
                            dailyBean.setCopyright(copyright);
                            bingDailyBeans.add(dailyBean);
                        }
                        getMyView().onUpdateUI(bingDailyBeans);
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
                            getMyView().showToast("网络数据获取失败\n" + e);
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
