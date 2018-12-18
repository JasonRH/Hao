package com.example.rh.core.utils.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.rh.core.app.MyApp;

/**
 * @author RH
 * @date 2018/10/9
 */
public class DimenUtils {
    public static int getScreenWidth() {
        final Resources resources = MyApp.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = MyApp.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
