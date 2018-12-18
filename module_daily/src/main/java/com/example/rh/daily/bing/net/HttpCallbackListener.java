package com.example.rh.daily.bing.net;

/**
 *
 * @author RH
 * @date 2017/12/14
 */

public interface HttpCallbackListener {
    void onFinish(String string);

    void onError(Exception e);
}
