package com.example.rh.module.tools.qrcode;

import android.graphics.Bitmap;

/**
 * @author RH
 * @date 2019/1/14
 */
public interface IScanner {
    interface View {
        void showQRCode(Bitmap bitmap);
        void showToast(String s);
    }
    interface Presenter{
        void creatQRCode(String content, int i);
    }
}
