package com.example.rh.ui.image;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author RH
 * @date 2018/12/7
 */
public class MyGlideOptions {
    public static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerInside()
            .dontAnimate();
}
