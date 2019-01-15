package com.example.rh.module.tools.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.TextUtils;


import com.example.rh.core.base.BasePresenter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author RH
 * @date 2019/1/14
 */
public class ScannerPresenter extends BasePresenter<IScanner.View> implements IScanner.Presenter {
    private CompositeDisposable disposable;
    private Bitmap bitmap = null;
    private Bitmap logoBitmap = null;

    public ScannerPresenter(CompositeDisposable compositeDisposable) {
        disposable = compositeDisposable;
    }

    @Override
    public void creatQRCode(final String content, final int i) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                //dp转px
                int px = PhoneUtils.dp2px(230);
                //width 和 height的单位为像素
                bitmap = createBitmap(content, px, px);
                if (bitmap != null) {
                    switch (i) {
                        case 0:
                            //不加Logo
                            e.onNext(0);
                            break;
                        case 1:
                            //加支付宝Logo
                            e.onNext(1);
                            break;
                        case 2:
                            //加微信Logo
                            e.onNext(2);
                            System.out.println("weixin");
                            break;
                        default:
                            break;
                    }
                }
            }
        })
                .map(new Function<Integer, Bitmap>() {
                    @Override
                    public Bitmap apply(Integer integer) throws Exception {
                        if (integer == 0) {
                            return bitmap;
                        } else if (integer == 1) {
                            //logoBitmap = readBitMap(MyApp.getApplicationContext(), R.mipmap.alipay_icon_small);
                            return addLogo(bitmap, logoBitmap);
                        } else {
                            //logoBitmap = readBitMap(MyApp.getApplicationContext(), R.mipmap.wxpay_icon_small);
                            return addLogo(bitmap, logoBitmap);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        getMyView().showQRCode(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMyView().showToast("生成二维码失败");
                    }

                    @Override
                    public void onComplete() {
                        bitmap.recycle();
                        logoBitmap.recycle();
                        bitmap = null;
                        logoBitmap = null;
                    }
                });
    }


    private Bitmap createBitmap(String content, int width, int height) {
        //判断URL合法性
        if (content == null || "".equals(content) || content.length() < 1) {
            return null;
        }
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置空白边距的宽度，//default is 4
        // hints.put(EncodeHintType.MARGIN, 2);
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            //图像数据转换，使用了矩阵转换
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0xff000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        //获取图片的宽高
        int srcWidth = qrBitmap.getWidth();
        int srcHeight = qrBitmap.getHeight();
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();

        //logo大小为二维码整体大小的1/6
        float scaleFactor = srcWidth * 1.0f / 6 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(qrBitmap, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logoBitmap, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap.recycle();
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }

    private Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        //RGB_565或ARGB_8888
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        //FileInputStream fis = new FileInputStream(imgPath);//根据图片路径获取
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public Bitmap readBitMapByPath(String imgPath) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        //RGB_565或ARGB_8888
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        FileInputStream fis = null;
        try {
            //根据图片路径获取
            fis = new FileInputStream(imgPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return BitmapFactory.decodeStream(fis, null, opt);
    }

    public Result scanningImage(String path) {
        Bitmap scanBitmap;
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        //DecodeHintType 和EncodeHintType
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        //设置二维码内容的编码
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

        BitmapFactory.Options options = new BitmapFactory.Options();
        //先获取原大小
        options.inJustDecodeBounds = true;
        //获取新的大小
        options.inJustDecodeBounds = false;
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0) {
            sampleSize = 1;
        }
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        int width = scanBitmap.getWidth();
        int height = scanBitmap.getHeight();
        int[] data = new int[width * height];
        scanBitmap.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        //RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException | ChecksumException | FormatException e) {
            e.printStackTrace();
        } finally {
            scanBitmap.recycle();
        }
        return null;
    }


}
