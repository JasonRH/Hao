package com.example.rh.daily.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rh.daily.R;

import java.util.Objects;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * @author RH
 * @date 2018/1/20
 */
public class WebActivity extends AppCompatActivity {
    private static final String TAG = "WebActivity";
    private String url;
    private WebView webView;
    private ProgressBar progressBar;
    private TextSwitcher textSwitcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = findViewById(R.id.web_activity_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initData();
        initView();
        initWebView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initData() {
        url = getIntent().getStringExtra("url");
    }

    private void initView() {
        progressBar = findViewById(R.id.content_webview_progressbar);
        webView = findViewById(R.id.content_web_view);
        textSwitcher = findViewById(R.id.web_text_switcher);
        textSwitcher.setFactory(() -> {
            TextView textView = new TextView(WebActivity.this);
            textView.setTextSize(18);
            textView.setTextColor(Color.WHITE);
            textView.setSingleLine(true);
            textView.setMarqueeRepeatLimit(-1);
            //从右到左滚动
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            //1.5s后开始滚动
            textView.postDelayed(() -> textView.setSelected(true), 1500);
            return textView;
        });


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //缩放操作
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        //其他细节操作
        //设置缓存，根据cache-control决定是否从网络上取数据
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");

        //设置不用系统浏览器打开,直接显示在当前Webview
        webView.setWebViewClient(new WebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100) {
                    Log.e(TAG, "进度: " + newProgress);
                    if (progressBar.getVisibility() != View.VISIBLE) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);

                } else {
                    Log.e(TAG, "完成");
                    progressBar.setProgress(100);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (textSwitcher != null) {
                    textSwitcher.setText(title);
                } else {
                    Log.e(TAG, "Welcome ! ! ! ");
                }
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();

        } else if (i == R.id.web_browser) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "打开失败，没有找到可以打开该链接的其它应用", Toast.LENGTH_SHORT).show();
            }

        } else if (i == R.id.web_copy) {
            if (url != null && !TextUtils.isEmpty(url)) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) Objects.requireNonNull(this).getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("webUrl", url);
                // 将ClipData内容放到系统剪贴板里。
                if (cm != null) {
                    cm.setPrimaryClip(mClipData);
                    Toast.makeText(this, "复制完成！", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "文本内容为空！", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.web_refresh) {
            webView.loadUrl(url);
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }

        super.onDestroy();
    }
}
