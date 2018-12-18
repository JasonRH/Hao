package com.example.rh.core.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.rh.core.base.BaseCheckerDelegate;
import com.example.rh.core.utils.callback.CallbackManager;
import com.example.rh.core.utils.callback.CallbackType;
import com.example.rh.core.utils.callback.IGlobalCallback;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * @author RH
 * @date 2018/11/14
 */
public class ScannerFragment extends BaseCheckerDelegate implements ZBarScannerView.ResultHandler {
    private ScanView mScanView = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanView == null) {
            mScanView = new ScanView(getContext());
        }
        //自动对焦
        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    protected Object setLayout() {
        return mScanView;
    }

    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView != null) {
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView != null) {
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        final IGlobalCallback<String> callback = CallbackManager
                .getInstance()
                .getCallback(CallbackType.ON_SCAN);
        if (callback != null) {
            callback.executeCallback(result.getContents());
        }
        getSupportDelegate().pop();
    }
}
