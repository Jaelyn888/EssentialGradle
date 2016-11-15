package com.yishanxiu.yishang.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.yishanxiu.yishang.R;

import java.io.File;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧 Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CustomImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        if(width<=0&&height<=0){
            width=DisplayUtil.screenWidth/3;
            height=width;
        }
        BitmapHelper.loadLocalImage(activity,path,imageView, BitmapHelper.LoadImgOption.BrandLogo,true,width,height);
    }

    @Override
    public void clearMemoryCache() {
    }
}
