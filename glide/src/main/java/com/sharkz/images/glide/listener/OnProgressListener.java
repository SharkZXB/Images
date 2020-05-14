package com.sharkz.images.glide.listener;

import com.bumptech.glide.load.engine.GlideException;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  17:32
 * 描    述
 * 修订历史：
 * ================================================
 */
public interface OnProgressListener {

    /**
     * @param imageUrl   图片地址
     * @param bytesRead  下载了多少字节
     * @param totalBytes 总共的大小
     * @param isDone     是否完成
     * @param exception  异常
     */
    void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, GlideException exception);

}
