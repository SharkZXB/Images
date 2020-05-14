package com.sharkz.images.glide.listener;

import android.text.TextUtils;

import com.bumptech.glide.load.engine.GlideException;
import com.sharkz.images.glide.listener.OnProgressListener;
import com.sharkz.images.glide.tool.HandlerUtil;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/14  13:38
 * 描    述 下载进度中间件
 * 修订历史：
 * ================================================
 */
public abstract class OnProgressListenerImpl implements OnProgressListener {

    /**
     * 加载的图片的地址
     */
    private String url;

    public OnProgressListenerImpl(String url) {
        this.url = url;
    }

    @Override
    public void onProgress(String imageUrl, long bytesRead, long totalBytes, final boolean isDone, final GlideException exception) {
        if (TextUtils.equals(imageUrl, url)) {
            final int percent = (int) ((bytesRead * 1.0f / totalBytes) * 100.0f);
            HandlerUtil.post(new Runnable() {
                @Override
                public void run() {
                    onProgress(percent, isDone, exception);
                }
            });
        }
    }

    /**
     * @param percent   下载进度的百分比，不关心，大小
     * @param isDone    是否完成
     * @param exception 异常
     */
    public abstract void onProgress(int percent, boolean isDone, GlideException exception);

}
