package com.example.rh.module.tools.translate;

import android.graphics.Bitmap;

/**
 * @author RH
 * @date 2019/1/21
 */
public interface ITranslate {
    interface View {
        void onTranslatedText(String s);

        void showToast(String s);
    }

    interface Presenter {
        void toTranslate(String content, String source, String target);
    }
}
