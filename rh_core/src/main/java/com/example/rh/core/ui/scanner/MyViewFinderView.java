package com.example.rh.core.ui.scanner;

import android.content.Context;
import android.graphics.Color;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * @author RH
 * @date 2018/11/14
 */
public class MyViewFinderView extends ViewFinderView {
    public MyViewFinderView(Context context) {
        super(context);
        //扫描框正方形
        mSquareViewFinder = true;
        //边框设置颜色
        mBorderPaint.setColor(Color.YELLOW);
        mLaserPaint.setColor(Color.YELLOW);
    }
}
