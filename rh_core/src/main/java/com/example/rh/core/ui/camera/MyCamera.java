package com.example.rh.core.ui.camera;

import android.net.Uri;

import com.example.rh.core.base.BaseCheckerDelegate;
import com.example.rh.core.utils.file.FileUtil;

/**
 * @author RH
 * @date 2018/11/7
 * <p>
 * 照相机调用类
 */
public class MyCamera {

    /**
     * 路径存放剪裁过的图片
     */
    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }


    /**
     * 弹出底部Dialog，选择开启照相机或选择图片
     */
    public static void start(BaseCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
