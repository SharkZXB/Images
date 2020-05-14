package com.sharkz.glide.okhttp;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/13  17:31
 * 描    述 图片大小
 * 修订历史：
 * ================================================
 */
public class ImageSize {

    private final static String SEPARATOR = "x";

    private final int width;
    private final int height;

    public ImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(width).append(SEPARATOR).append(height).toString();
    }

}
