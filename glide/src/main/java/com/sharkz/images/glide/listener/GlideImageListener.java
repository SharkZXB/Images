package com.sharkz.images.glide.listener;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-02-15  13:48
 * 描    述 当前用到的接口都在这里统一管理
 * 修订历史：
 * ================================================
 */
public interface GlideImageListener {

    /**
     * 磁盘缓存清理 回调通知
     */
    interface GlideClearListener {
        void CleanUpTheComplete();
    }


    /**
     * 获取Bitmap
     */
    interface IGetBitmapListener {
        void onBitmap(Bitmap bitmap);
    }


    /**
     * 设置此皆苦是为了业务需要，一般不需要关心网络请求回来的drawable，但是业务需要切换的地方的话，需要拿到网络请求回来的drawable
     */
    interface IGetDrawableListener {
        void onDrawable(Drawable drawable);
    }


    /**
     * 监听图片下载进度
     */
    interface IImageLoaderListener {

        /**
         * 监听图片下载错误
         *
         * @param url
         * @param target
         * @param exception
         */
        void onLoadingFailed(String url, ImageView target, Exception exception);

        /**
         * 监听图片加载成功
         *
         * @param url
         * @param target
         */
        void onLoadingComplete(String url, ImageView target);

    }


}
