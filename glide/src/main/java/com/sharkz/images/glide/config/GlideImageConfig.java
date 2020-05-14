package com.sharkz.images.glide.config;

import android.content.Context;

import com.sharkz.images.glide.R;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/14  14:05
 * 描    述 GlideImage 全局配置
 * 修订历史：
 * ================================================
 */
public class GlideImageConfig {

    /**
     * 默认使用的占位图片
     */
    public static int placeholderResId = -1;

    /**
     * 默认使用的加载失败图片
     */
    public static int errorResId = -1;

    public static void setPlaceholderResId(int placeholderResId) {
        GlideImageConfig.placeholderResId = placeholderResId;
    }

    public static void setErrorResId(int errorResId) {
        GlideImageConfig.errorResId = errorResId;
    }

}
