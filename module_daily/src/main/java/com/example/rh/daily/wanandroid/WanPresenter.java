package com.example.rh.daily.wanandroid;

import android.accounts.NetworkErrorException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.net_rx.RxRetrofitClient;
import com.example.rh.core.utils.log.MyLogger;

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
 * @date 2019/1/17
 */
public class WanPresenter extends BasePresenter<IWan.View> implements IWan.Presenter {
    private CompositeDisposable compositeDisposable;

    public WanPresenter(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void loadData(int page) {
        RxRetrofitClient.builder()
                .url("http://www.wanandroid.com/article/list/" + page + "/json")
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
                        JSONObject jsonObject = JSON.parseObject(s).getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("datas");
                        int size = jsonArray.size();
                        List<WanBean> dataList = new ArrayList<>();
                        for (int i = 0; i < size; i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String title = object.getString("title");
                            String author = object.getString("author");
                            String time = object.getString("niceDate");
                            String category1 = object.getString("superChapterName");
                            String category2 = object.getString("chapterName");
                            String url = object.getString("link");
                            Boolean fresh = object.getBoolean("fresh");

                            JSONArray tagArray = object.getJSONArray("tags");
                            String tagName = "";
                            String tagUrl = "";
                            if (tagArray.size() > 0) {
                                JSONObject object1 = tagArray.getJSONObject(0);
                                tagName = object1.getString("name");
                                tagUrl = object1.getString("url");
                            }

                            WanBean bean = new WanBean.Builder()
                                    .setTitle(title)
                                    .setAuthor(author)
                                    .setTime(time)
                                    .setCategory(category1)
                                    .setType(category2)
                                    .setUrl(url)
                                    .setFresh(fresh)
                                    .setTagName(tagName)
                                    .setTagUrl(tagUrl)
                                    .build();
                            dataList.add(bean);
                        }
                        if (page > 0) {
                            getMyView().onLoadMoreData(dataList);
                        } else {
                            getMyView().onLoadNewData(dataList);
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
