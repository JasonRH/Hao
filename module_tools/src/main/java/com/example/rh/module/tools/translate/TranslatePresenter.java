package com.example.rh.module.tools.translate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.rh.core.base.BasePresenter;
import com.example.rh.core.net_rx.RxRetrofitClient;
import com.example.rh.core.utils.log.MyLogger;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.flowable.FlowableOnErrorReturn;
import io.reactivex.schedulers.Schedulers;

/**
 * @author RH
 * @date 2019/1/21
 */
public class TranslatePresenter extends BasePresenter<ITranslate.View> implements ITranslate.Presenter {
    private CompositeDisposable disposable;
    private Translation translation;
    private String YouDaoBaseUrl = "http://fanyi.youdao.com/openapi.do";
    private String YouDaoKeyFrom = "1221212";
    private String YouDaoKey = "2017150387";
    private String YouDaoType = "data";
    private String YouDaoDoctype = "json";
    private String YouDaoVersion = "1.1";

    public TranslatePresenter(CompositeDisposable compositeDisposable) {
        disposable = compositeDisposable;
    }

    @Override
    public void toTranslate(final String content, final String source, final String target) {
        final String YouDaoUrl = YouDaoBaseUrl + "?keyfrom=" + YouDaoKeyFrom + "&key=" + YouDaoKey + "&type="
                + YouDaoType + "&doctype=" + YouDaoDoctype + "&type=" + YouDaoType + "&version=" + YouDaoVersion
                + "&q=" + content;
        MyLogger.i("TranslatePresenter", YouDaoUrl);
        RxRetrofitClient.builder()
                .url(YouDaoUrl)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        JSONObject object = JSON.parseObject(s);
                        String errorCode = object.getString("errorCode");
                        if (errorCode.equals("20")) {
                            getMyView().onTranslatedText("要翻译的文本过长");
                        } else if (errorCode.equals("30 ")) {
                            getMyView().onTranslatedText("无法进行有效的翻译");
                        } else if (errorCode.equals("40")) {
                            getMyView().onTranslatedText("不支持的语言类型");
                        } else if (errorCode.equals("50")) {
                            getMyView().onTranslatedText("无效的key");
                        } else {
                            String message = "翻译结果：";
                            JSONArray array = object.getJSONArray("translation");
                            String[] translations = array.toArray(new String[array.size()]);
                            for (String translation : translations) {
                                message += "\t" + translation;
                            }
                            // 有道词典-基本词典
                            if (object.containsKey("basic")) {
                                JSONObject basic = object.getJSONObject("basic");
                                if (basic.containsKey("phonetic")) {
                                    String phonetic = basic.getString("phonetic");
                                    // message += "\n\t" + phonetic;
                                }
                                if (basic.containsKey("explains")) {
                                    String explains = basic.getString("explains");
                                    // message += "\n\t" + explains;
                                }
                            }
                            // 有道词典-网络释义
                            if (object.containsKey("web")) {
                                JSONArray web = object.getJSONArray("web");
                                int count = web.size();
                                for (int i = 0; i < count; i++) {
                                    JSONObject object1 = web.getJSONObject(i);
                                    if (object1.containsKey("key")) {
                                        String key = web.getJSONObject(i).getString("key");
                                        message += "\n（" + (i + 1) + "）" + key + "\n";
                                    }
                                    if (object1.containsKey("value")) {
                                        JSONArray array1 = object1.getJSONArray("value");
                                        String[] values = array1.toArray(new String[array1.size()]);
                                        for (int j = 0; j < values.length; j++) {
                                            String value = values[j];
                                            message += value;
                                            if (j < values.length - 1) {
                                                message += "，";
                                            }
                                        }
                                    }
                                }
                            }
                            getMyView().onTranslatedText(message);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
