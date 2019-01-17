package com.example.rh.ui.image;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author RH
 * @date 2018/12/7
 */
public class MyGlideOptions {

    public static final RequestOptions OPTIONS = new RequestOptions()
            //磁盘缓存策略,尝试对本地和远程图片使用最佳的策略
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerInside()
            .dontAnimate();

    public static final RequestOptions OPTIONS1 = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

}
