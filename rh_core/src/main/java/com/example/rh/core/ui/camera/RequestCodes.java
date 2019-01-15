package com.example.rh.core.ui.camera;


import com.yalantis.ucrop.UCrop;

/**
 * @author RH
 * @date 2018/11/7
 * <p>
 * 请求码存储
 */
public class RequestCodes {
    public static final int TAKE_PHOTO = 4;
    public static final int PICK_PHOTO = 5;
    public static final int PICK_LOCAL_QR = 6;
    public static final int CROP_PHOTO = UCrop.REQUEST_CROP;
    public static final int CROP_ERROR = UCrop.RESULT_ERROR;
}
