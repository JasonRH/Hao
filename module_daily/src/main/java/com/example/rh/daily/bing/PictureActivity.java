package com.example.rh.daily.bing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.rh.core.app.MyApp;
import com.example.rh.daily.bing.net.HttpCallbackListener;
import com.example.rh.daily.bing.net.HttpUtils;
import com.example.rh.daily.R;
import com.example.rh.daily.bing.download.DownloadListener;
import com.example.rh.daily.bing.download.DownloadService;

/**
 * @author RH
 * @date 2017/11/3
 */

public class PictureActivity extends AppCompatActivity {
    private TextView pictureContentText, author, title;
    private static String words;
    private static String pictureImageId;
    private static String pictureName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        //设置toolbar替代原ActionBar
        setSupportActionBar(toolbar1);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //显示左上角的返回图标,并可点击
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        BingDailyBean bingDailyBean = (BingDailyBean) getIntent().getSerializableExtra("BingDaily_data");
        if (bingDailyBean != null) {
            pictureName = bingDailyBean.getDate();
            pictureImageId = bingDailyBean.getUrl();
            words = bingDailyBean.getCopyright();
        }


        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_tool);
        ImageView pictureImageView = findViewById(R.id.picture_image_view);
        author = findViewById(R.id.picture_author);
        title = findViewById(R.id.picture_title);
        pictureContentText = findViewById(R.id.picture_content_text);
        collapsingToolbar.setTitle(pictureName);
        Glide.with(this).load(pictureImageId).into(pictureImageView);
        //首次，无网，默认加载必应Copyright
        title.setText(words);
        //加载每日一文
        intContent(pictureName);

        findViewById(R.id.activity_picture_floatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDialog myDialog = new MyDialog(v.getContext());
                myDialog.setTitle("是否下载当前图片？");
                myDialog.setMessage("默认下载路径为：/sdcard/Download");
                myDialog.setYesOnclickListener("立即下载", new MyDialog.YesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        //获取写入外部存储的权限
                        if (ContextCompat.checkSelfPermission(PictureActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PictureActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            if (pictureImageId != null && !TextUtils.isEmpty(pictureImageId)) {
                                Toast.makeText(MyApp.getApplicationContext(), "图片下载中，请稍等......", Toast.LENGTH_SHORT).show();
                                Intent download = new Intent(PictureActivity.this, DownloadService.class);
                                download.putExtra("url", pictureImageId);
                                DownloadService.pictureDownload(new DownloadListener() {
                                    @Override
                                    public void onSuccess() {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(MyApp.getApplicationContext(), "图片下载成功", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    /**
     * 获取每日一文
     */
    private void intContent(String date) {
        //此接口用okhttp3请求会出错,可能将 //connection.setDoOutput(true)
        String url = "https://interface.meiriyiwen.com/article/day?dev=1&date=" + date;
        HttpUtils.sendHttpRequest(url, new HttpCallbackListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish(final String string) {
                final JSONObject object = JSON.parseObject(string).getJSONObject("data");
                words = object.getString("content");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        title.setText(object.getString("title"));
                        author.setText("—" + object.getString("author"));
                        //也可以使用Android富文本来加载Html格式的文本
                        pictureContentText.setText(Html.fromHtml(words));
                    }
                });
            }

            @Override
            public void onError(final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApp.getApplicationContext(), "加载每日一文失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 左上角返回图标的点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 权限申请回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MyApp.getApplicationContext(), "权限拒绝，无法下载！", Toast.LENGTH_SHORT).show();

                } else {
                    if (pictureImageId != null && !TextUtils.isEmpty(pictureImageId)) {
                        Toast.makeText(MyApp.getApplicationContext(), "权限已获取，开始下载图片......", Toast.LENGTH_SHORT).show();
                        Intent download = new Intent(this, DownloadService.class);
                        download.putExtra("url", pictureImageId);
                        DownloadService.pictureDownload(new DownloadListener() {
                            @Override
                            public void onSuccess() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MyApp.getApplicationContext(), "图片下载成功", Toast.LENGTH_SHORT).show();
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
                break;
            default:
        }
    }

}
