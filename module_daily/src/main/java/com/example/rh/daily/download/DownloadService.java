package com.example.rh.daily.download;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author RH
 * @date 2018/5/29
 */
public class DownloadService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private String downloadUrl;
    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_FAILED = 1;
    private static DownloadListener listener;
    private String filePath = "";

    public static void pictureDownload(DownloadListener listener1) {
        listener = listener1;
    }

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null) {
            downloadUrl = intent.getStringExtra("url");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (downloadUrl != null && !TextUtils.isEmpty(downloadUrl)) {
            startDownload(downloadUrl);
        }
    }

    public void startDownload(String url) {
        if (!url.startsWith("http")) {
            listener.onFailed();
            return;
        }
        switch (downLoad(url)) {
            case TYPE_SUCCESS:
                listener.onSuccess(filePath);
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            default:
                break;
        }
    }

    private int downLoad(String downloadUrl) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try {
            //记录已下载的文件长度
            long downloadedLength = 0;
            String fileName;
            if (downloadUrl.lastIndexOf("/") == -1) {
                //下载文件无名称时
                fileName = System.currentTimeMillis()+"";
            } else {
                fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            }
            //SD卡的Download目录
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            filePath =directory + fileName;
            file = new File(filePath);
            if (file.exists()) {
                downloadedLength = file.length();
            }
            //获取要下载的文件的长度
            long contentLength = getContentLength(downloadUrl);
            //Log.e("DownloadTask", "doInBackground: 需要下载的数据长度为" + contentLength);
            //Log.e("DownloadTask", "doInBackground: 已下载的数据长度为" + downloadedLength);
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadedLength) {
                //已下载字节和文件总字节相等，说明已经下载完成
                return TYPE_SUCCESS;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //断点下载，指定从哪个字节开始下载
                    //header用于告诉服务器我们想要从哪个字节开始下载
                    .addHeader("RANGE", "bytes=" + downloadedLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                //获取输入流
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                //跳过已下载的字节
                savedFile.seek(downloadedLength);
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1) {
                    total += len;
                    savedFile.write(b, 0, len);

                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.e("DownloadService", "onDestroy: ");
    }

    private long getContentLength(String downloadUrl) {
        OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            Request request = new Request.Builder()
                    .url(downloadUrl)
                    .build();
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            //Log.e("DownloadTask", "getContentLength:输入的下载链接错误 ");
        }
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
