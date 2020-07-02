package com.sharkz.shape;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.view.View;


/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/7/2  10:13
 * 描    述 层级 Shape 工厂
 * 修订历史：
 * ================================================
 */
public class LayerDrawableFactory implements IShapeFactory<Drawable, View> {


    /**
     * 有层级能力的 Drawable
     */
    private LayerDrawable drawable;

    private LayerDrawableFactory(Drawable[] drawables) {
        drawable = new LayerDrawable(drawables);
    }

    public static LayerDrawableFactory getInstance(Drawable... drawables) {
        return new LayerDrawableFactory(drawables);
    }


    // =============================================================================================


    public LayerDrawableFactory Left(int index, int px) {
        drawable.setLayerInset(index, 0, 0, 0, px);
        return this;
    }

    public LayerDrawableFactory Top(int index, int px) {
        drawable.setLayerInset(index, 0, 0, 0, px);
        return this;
    }

    public LayerDrawableFactory Right(int index, int px) {
        drawable.setLayerInset(index, 0, 0, 0, px);
        return this;
    }

    public LayerDrawableFactory Bottom(int index, int px) {
        drawable.setLayerInset(index, 0, 0, 0, px);
        return this;
    }

    public LayerDrawableFactory setInset(int index, int left, int top, int right, int bottom) {
        drawable.setLayerInset(index, left, top, right, bottom);
        return this;
    }


    // =============================================================================================


    @Override
    public void into(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    @Override
    public Drawable build() {
        return null;
    }
}
