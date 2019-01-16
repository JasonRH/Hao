package com.example.rh.daily.download;

/**
 *
 * @author RH
 * @date 2017/12/19
 */

public interface DownloadListener {
    void onSuccess(String filePath);
    void onFailed();
}
