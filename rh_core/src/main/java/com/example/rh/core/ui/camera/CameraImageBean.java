package com.example.rh.core.ui.camera;

import android.net.Uri;

/**
 * @author RH
 * @date 2018/11/7
 * <p>
 * 存储一些中间值
 */
public final class CameraImageBean {

    private Uri mPath = null;

    /**
     * 饿汉单例模式
     */
    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance() {
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}
