package com.example.rh.core.ui.scanner;

import android.content.Context;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * @author RH
 * @date 2018/11/14
 */
public class ScanView extends ZBarScannerView {
    public ScanView(Context context) {
        super(context);
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new MyViewFinderView(context);
    }
}
