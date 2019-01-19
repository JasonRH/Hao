package com.example.rh.core.net_rx;

import android.content.Context;

import com.example.rh.core.app.MyApp;
import com.example.rh.core.ui.loader.LoaderStyle;
import com.example.rh.core.ui.loader.MyLoader;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @author RH
 * @date 2018/10/16
 */
public class RxRetrofitClient {
    private final String URL;
    /**
     * 浅拷贝
     */
    private final WeakHashMap<String, Object> PARAMS = RxRetrofitCreator.getParams();
    private final RequestBody BODY;
    /**
     * 上传文件
     */
    private final File FILE;
    /**
     * 进度条样式
     */
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    public RxRetrofitClient(String url,
                            Map<String, Object> params,
                            RequestBody body,
                            File file,
                            LoaderStyle style,
                            Context context) {
        this.URL = url;
        //深拷贝
        PARAMS.putAll(params);
        this.BODY = body;
        this.FILE = file;
        this.LOADER_STYLE = style;
        this.CONTEXT = context;
    }

    public static RxRetrofitClientBuilder builder() {
        return new RxRetrofitClientBuilder();
    }

    /***返回一个Observable可观察者对象*/
    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> put() {
        if (BODY == null) {
            return request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    private Observable<String> request(HttpMethod method) {
        final RxRetrofitService service = RxRetrofitCreator.getRxRetrofitService();
        Observable<String> observable = null;
        //加载进度条Dialog
        if (LOADER_STYLE != null) {
            MyLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
        }
        //取消进度条Dialog
        onRequestFinish();

        return observable;
    }

    /**
     * 下载1
     */
    public final Observable<ResponseBody> download() {
        final Observable<ResponseBody> responseBodyObservable = RxRetrofitCreator.getRxRetrofitService().download(URL, PARAMS);
        return responseBodyObservable;
    }

    private void onRequestFinish() {
        //取消进度条Dialog
        if (LOADER_STYLE != null) {
            MyApp.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyLoader.stopLoading();
                }
            }, 0);
        }
    }
}
