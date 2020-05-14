package com.sharkz.glide.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.UiThread;

import com.bumptech.glide.Glide;
import com.sharkz.glide.config.GlideApp;
import com.sharkz.glide.listener.GlideImageListener;

import java.io.File;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/14  09:10
 * 描    述 这里处理Glide缓存的
 * 修订历史：
 * ================================================
 */
public class GlideCacheManager {

    /**
     * 获取Glide默认的缓存路径
     */
    public File getCacheDir(Context context) {
        return GlideApp.getPhotoCacheDir(context);
    }

    /**
     * 清理内存缓存
     */
    @UiThread
    public void clearMemoryCache(Context context) {
        GlideApp.get(context).clearMemory();
    }

    /**
     * 清理磁盘缓存
     */
    @SuppressLint("StaticFieldLeak")
    public void clearDiskCache(final Context context, final GlideImageListener.GlideClearListener listener) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // 必须在子线程中  This method must be called on a background thread.
                Glide.get(context).clearDiskCache();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (listener != null) {
                    listener.CleanUpTheComplete();
                }
            }
        };
    }


    // =============================================================================================


    /**
     * Glide 加载的缓存策略
     * 默认的策略是DiskCacheStrategy.AUTOMATIC
     */
//    public static DiskCacheStrategy AllStrategy = DiskCacheStrategy.ALL;            // 使用DATA和RESOURCE缓存远程数据，仅使用RESOURCE来缓存本地数据。
//    public static DiskCacheStrategy NoneStrategy = DiskCacheStrategy.NONE;          // 不使用磁盘缓存
//    public static DiskCacheStrategy DataStrategy = DiskCacheStrategy.DATA;          // 在资源解码前就将原始数据写入磁盘缓存
//    public static DiskCacheStrategy ResourceStrategy = DiskCacheStrategy.RESOURCE;      // 在资源解码后将数据写入磁盘缓存，即经过缩放等转换后的图片资源。
//    public static DiskCacheStrategy AutomaticStrategy = DiskCacheStrategy.AUTOMATIC;     // 根据原始图片数据和资源编码策略来自动选择磁盘缓存策略。


}
