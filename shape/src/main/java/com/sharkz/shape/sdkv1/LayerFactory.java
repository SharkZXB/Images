package com.sharkz.shape.sdkv1;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/7/1  16:48
 * 描    述 层 多层Drawable 一起使用
 * 修订历史：
 * ================================================
 */
public class LayerFactory {

    private LayerDrawable drawable;

    public LayerFactory(Drawable[] drawables) {
        drawable = new LayerDrawable(drawables);
    }

    public static LayerFactory create(Drawable... drawables) {
        return new LayerFactory(drawables);
    }

    public LayerFactory Left(int index, int px) {
        drawable.setLayerInset(index, 0, 0, 0, px);
        return this;
    }

    public LayerFactory Top(int index, int px) {
        drawable.setLayerInset(index, 0, 0, 0, px);
        return this;
    }

    public LayerFactory Right(int index, int px) {
        drawable.setLayerInset(index, 0, 0, 0, px);
        return this;
    }

    public LayerFactory Bottom(int index, int px) {
        drawable.setLayerInset(index, 0, 0, 0, px);
        return this;
    }

    public LayerFactory setInset(int index,int left,int top,int right,int bottom) {
        drawable.setLayerInset(index, left, top, right, bottom);
        return this;
    }

    public void build(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        }else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public Drawable build() {
        return drawable;
    }
    
}
