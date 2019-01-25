package com.example.rh.core.base;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.rh.core.ui.camera.CameraImageBean;
import com.example.rh.core.ui.camera.MyCamera;
import com.example.rh.core.ui.camera.RequestCodes;
import com.example.rh.core.ui.scanner.ScannerFragment;
import com.example.rh.core.utils.log.MyLogger;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Objects;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * @author RH
 * @date 2018/8/22
 * <p>
 * 不需要使用Presenter的Fragment继承该类
 */

@RuntimePermissions
public abstract class BaseCheckerDelegate extends BaseFragment {

    /**
     * 打开相机(不是直接调用方法)
     */
    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void startCamare() {
        MyCamera.start(this);
    }

    /**
     * 打开相机(这个是真正调用的方法)
     */
    public void startCameraWithCheck() {
        BaseCheckerDelegatePermissionsDispatcher.startCamareWithPermissionCheck(this);
    }

    /**
     * 扫描二维码(不直接调用)
     */
    @NeedsPermission(Manifest.permission.CAMERA)
    void startScan(BaseFragment fragment) {
        fragment.getSupportDelegate().start(new ScannerFragment());
    }

    /**
     * 扫描二维码(直接调用)
     */
    public void startScanWithCheck(BaseFragment fragment) {
        BaseCheckerDelegatePermissionsDispatcher.startScanWithPermissionCheck(this, fragment);
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraDenied() {
        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraNever() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //必须加入此方法，否则重写的onRequestPermissionsResult()会报错
        BaseCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    MyLogger.i("BaseCheckerDelegate", "拍照");
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    //剪裁后的图片替换原路径中的图片break;
                    cropPicture(resultUri, null);

                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        MyLogger.i("BaseCheckerDelegate", "本地图片剪裁");
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = MyCamera.createCropFile().getPath();
                        cropPicture(pickPath, Uri.parse(pickCropPath));
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    MyLogger.i("BaseCheckerDelegate", "图片剪裁成功后回调");
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    /*final IGlobalCallback<Uri> callback = CallbackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if (callback != null) {
                        //回调
                        callback.executeCallback(cropUri);
                    }*/
                    if (cropUri != null) {
                        Toast.makeText(getContext(), "剪裁成功，存储路径为："+cropUri.getPath(), Toast.LENGTH_LONG).show();
                        //最后通知图库更新
                        getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(cropUri.getPath()))));
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * 剪裁图片
     */
    private void cropPicture(Uri pickPath, Uri pickCropPath) {
        if (pickCropPath == null) {
            pickCropPath = Uri.parse(MyCamera.createCropFile().getPath());
        }
        UCrop.of(pickPath, pickCropPath)
                //剪裁图片最大尺寸
                .withMaxResultSize(400, 400)
                .start(Objects.requireNonNull(getContext()), this);
        MyLogger.i("BaseCheckerDelegate", "开始裁剪图片，预存放路径：" + pickCropPath.getPath());

    }
}
