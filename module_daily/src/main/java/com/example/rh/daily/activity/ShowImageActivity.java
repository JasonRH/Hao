package com.example.rh.daily.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rh.core.app.MyApp;
import com.example.rh.core.ui.dialog.MyDialog;
import com.example.rh.daily.R;
import com.example.rh.daily.download.DownloadListener;
import com.example.rh.daily.download.DownloadService;

import java.io.File;

/**
 * @author RH
 * @date 2018/3/13
 */
public class ShowImageActivity extends AppCompatActivity {
    private static final String TAG = "ShowImageActivity";
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = findViewById(R.id.show_Image);

        String url = getIntent().getStringExtra("imageUrl");

        Glide.with(this).load(url).into(imageView);

        RelativeLayout layout = findViewById(R.id.layout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final MyDialog myDialog = new MyDialog(v.getContext());
                myDialog.setTitle("是否下载当前图片？");
                myDialog.setMessage("默认下载路径为："+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
                myDialog.setYesOnclickListener("立即下载", new MyDialog.YesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        //获取写入外部存储的权限
                        if (ContextCompat.checkSelfPermission(ShowImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ShowImageActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            if (url != null && !TextUtils.isEmpty(url)) {
                                Toast.makeText(MyApp.getApplicationContext(), "图片下载中，请稍等......", Toast.LENGTH_SHORT).show();
                                Intent download = new Intent(ShowImageActivity.this, DownloadService.class);
                                download.putExtra("url", url);
                                DownloadService.pictureDownload(new DownloadListener() {
                                    @Override
                                    public void onSuccess(String filePath) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MyApp.getApplicationContext(), "图片下载成功", Toast.LENGTH_SHORT).show();
                                                //最后通知图库更新
                                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(filePath))));
                                            }
                                        });
                                    }
                                    @Override
                                    public void onFailed() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MyApp.getApplicationContext(), "图片下载失败", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                                startService(download);
                            }
                        }
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
}
