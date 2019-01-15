package com.example.rh.core.utils.picture;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.rh.core.utils.toast.MyToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author RH
 * @date 2019/1/14
 */
public class PictureUtil {
    /**
     * 保存图片到图库
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Pictures");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MyToastUtil.showShortToast(context, "保存成功");
        } catch (FileNotFoundException e) {
            MyToastUtil.showShortToast(context, "保存失败");
            e.printStackTrace();
        } catch (IOException e) {
            MyToastUtil.showShortToast(context, "保存失败");
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        /*try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            MyToastUtil.showShortToast(context, "保存成功");
        } catch (FileNotFoundException e) {
            MyToastUtil.showShortToast(context, "保存失败");
            e.printStackTrace();
        }*/
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
    }

}
