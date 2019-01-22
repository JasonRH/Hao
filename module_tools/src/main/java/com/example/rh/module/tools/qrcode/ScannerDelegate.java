package com.example.rh.module.tools.qrcode;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.rh.core.base.BaseDelegate;
import com.example.rh.core.ui.camera.RequestCodes;
import com.example.rh.core.ui.dialog.MyDialog;
import com.example.rh.core.utils.callback.CallbackManager;
import com.example.rh.core.utils.callback.CallbackType;
import com.example.rh.core.utils.callback.IGlobalCallback;
import com.example.rh.core.utils.path.UriToPathUtil;
import com.example.rh.core.utils.picture.PictureUtil;
import com.example.rh.module.tools.R;
import com.example.rh.module.tools.R2;
import com.google.zxing.Result;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

/**
 * @author RH
 * @date 2019/1/5
 */
public class ScannerDelegate extends BaseDelegate<ScannerPresenter> implements IScanner.View {
    @BindView(R2.id.delegate_sanner_result)
    AppCompatEditText textView;
    @BindView(R2.id.delegate_image)
    AppCompatImageView imageView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Bitmap bitmap = null;

    @Override
    protected ScannerPresenter setPresenter() {
        return new ScannerPresenter(compositeDisposable);
    }

    /**
     * 扫描二维码采用ZBar，其它采用二维码操作采用zxing
     */
    @OnClick(R2.id.delegate_sanner)
    void onClickScanQrCode() {
        startScanWithCheck(this);
    }

    @OnClick(R2.id.delegate_sanner_local)
    void onClickLocalScanQrCode() {
        //打开相册
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, RequestCodes.PICK_LOCAL_QR);
    }

    @OnClick(R2.id.delegate_sanner_create)
    void onClickCreateQrCode() {
        String s = textView.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            presenter.creatQRCode(s, 0);
        } else {
            Toast.makeText(getContext(), "请在文本区输入内容！", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R2.id.delegate_sanner_copy)
    void onClickCopy() {
        String s = textView.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) Objects.requireNonNull(getContext()).getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("QrCode", textView.getText().toString());
            // 将ClipData内容放到系统剪贴板里。
            if (cm != null) {
                cm.setPrimaryClip(mClipData);
                Toast.makeText(getContext(), "复制完成！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "文本内容为空！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Object setLayout() {
        return R.layout.delegate_scanner;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onBindView(Bundle savedInstanceState, View rootView) {
        //扫描二维码回调
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(String args) {
                        textView.setText(args);
                        //内容显示后隐藏光标
                        textView.setCursorVisible(false);
                    }
                });

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    //再次点击显示光标
                    textView.setCursorVisible(true);
                }
                return false;
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final MyDialog myDialog = new MyDialog(getContext());
                myDialog.setTitle("提示");
                myDialog.setMessage("将二维码保存到相册？");
                myDialog.setYesOnclickListener("确定", new MyDialog.YesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        PictureUtil.saveImageToGallery(getContext(), bitmap);
                        myDialog.dismiss();
                    }
                });
                myDialog.setNoOnClickListener("取消", new MyDialog.NoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        myDialog.dismiss();
                    }
                });
                myDialog.show();
                return false;
            }
        });
    }

    @Override
    public void showQRCode(Bitmap bitmap) {
        if (bitmap != null) {
            imageView.setImageBitmap(null);
            imageView.setImageBitmap(bitmap);
            this.bitmap = bitmap;
        }
    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCodes.PICK_LOCAL_QR && data != null) {
                //将Uri转Path
                String path = UriToPathUtil.handleImageOnKitKat(this.getContext(), data);
                //扫描图片二维码结果
                Result result = presenter.scanningImage(path);
                if (result !=null) {
                    textView.setText(result.getText());
                    //内容显示后隐藏光标
                    textView.setCursorVisible(false);
                }else {
                    Toast.makeText(getContext(), "解析失败！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
