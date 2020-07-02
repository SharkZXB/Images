package com.sharkz.shape;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;


/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/7/1  16:27
 * 描    述 统一处理 shape 减少xml资源泛滥
 * https://github.com/DrownCoder/SupperShape
 * https://github.com/LiangLuDev/DevShapeUtils
 * 修订历史：
 * ================================================
 */
public class ShapeFactory {

    @SuppressLint("StaticFieldLeak")
    private static Application context;

    /**
     * TODO 必须在全局Application先调用，获取context上下文
     *
     * @param app Application
     */
    public  static void init(Application app) {
        context = app;
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        initialize();
        return context;
    }

    /**
     * 检测是否调用初始化方法
     */
    private static void initialize() {
        if (context == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 DevShapeUtils.init() 初始化！");
        }
    }


    /**
     * 设置样式（主要是椭圆和矩形）
     * @param shapeModel 样式类型 例 DevShape.RECTANGLE 矩形
     * @return OvalShape
     */
    public static GradientDrawableFactory shape(@GradientDrawableFactory.Shape int shapeModel) {
        return GradientDrawableFactory.getInstance(shapeModel);
    }

    /**
     * 背景状态选择器（背景颜色）
     *
     * @param selectorState Selector状态 例：DevSelector.STATE_PRESSED  按压状态
     * @param pressedColorResId 触摸颜色 例：R.color.colorPrimary
     * @param normalColorResId  正常颜色 例：R.color.colorPrimary
     * @return DevSelector
     */
    public static StateListDrawableFactory selector(@StateListDrawableFactory.SelectorState int selectorState, @ColorRes int pressedColorResId, @ColorRes int normalColorResId) {
        return StateListDrawableFactory.getInstance().selector(selectorState,new ColorDrawable(ShapeFactory.getContext().getResources().getColor(pressedColorResId)), new ColorDrawable(ShapeFactory.getContext().getResources().getColor(normalColorResId)));
    }

    /**
     * .
     * 背景状态选择器（背景颜色）
     *
     * @param selectDrawable 触摸颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     * @param normalDrawable  正常颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     * @return DevSelector
     */
    public static StateListDrawableFactory selector(@StateListDrawableFactory.SelectorState int selectorState, Drawable selectDrawable, Drawable normalDrawable) {
        return StateListDrawableFactory.getInstance().selector(selectorState,selectDrawable, normalDrawable);
    }

    /**
     * 是否按压背景状态选择器（背景颜色）
     *
     * @param pressedColorResId 触摸颜色 例：R.color.colorPrimary
     * @param normalColorResId  正常颜色 例：R.color.colorPrimary
     * @return DevSelector
     */
    public static StateListDrawableFactory selectorPressed(@ColorRes int pressedColorResId, @ColorRes int normalColorResId) {
        return StateListDrawableFactory.getInstance().selectorPressed(new ColorDrawable(ShapeFactory.getContext().getResources().getColor(pressedColorResId)), new ColorDrawable(ShapeFactory.getContext().getResources().getColor(normalColorResId)));
    }

    /**
     * .
     * 是否按压背景状态选择器（背景颜色）
     *
     * @param pressedColor 触摸颜色 例：#ffffff
     * @param normalColor  正常颜色 例：#ffffff
     * @return DevSelector
     */
    public static StateListDrawableFactory selectorPressed(String pressedColor, String normalColor) {
        return StateListDrawableFactory.getInstance().selectorPressed(new ColorDrawable(Color.parseColor(pressedColor)), new ColorDrawable(Color.parseColor(normalColor)));
    }

    /**
     * .
     * 是否按压背景状态选择器（背景Drawable）
     *
     * @param pressedDrawable 触摸颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     * @param normalDrawable  正常颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     * @return DevSelector
     */
    public static StateListDrawableFactory selectorPressed(Drawable pressedDrawable, Drawable normalDrawable) {
        return StateListDrawableFactory.getInstance().selectorPressed(pressedDrawable, normalDrawable);
    }

    /**
     *
     * 是否可用背景状态选择器（背景颜色）
     *
     * @param enableColor view 可用颜色 例：#ffffff
     * @param disableColor  view 不可用颜色 例：#ffffff
     * @return DevSelector
     */
    public static StateListDrawableFactory selectorEnable(String enableColor, String disableColor) {
        return StateListDrawableFactory.getInstance().selectorEnable(new ColorDrawable(Color.parseColor(enableColor)), new ColorDrawable(Color.parseColor(disableColor)));
    }

    /**
     * 是否可用背景状态选择器（背景颜色）
     *
     * @param enableColor 触摸颜色 例：R.color.colorPrimary
     * @param disableColor  正常颜色 例：R.color.colorPrimary
     * @return DevSelector
     */
    public static StateListDrawableFactory selectorEnable(@ColorRes int enableColor, @ColorRes int disableColor) {
        return StateListDrawableFactory.getInstance().selectorEnable(new ColorDrawable(ShapeFactory.getContext().getResources().getColor(enableColor)), new ColorDrawable(ShapeFactory.getContext().getResources().getColor(disableColor)));
    }

    /**
     * .
     * 是否按压背景状态选择器（背景Drawable）
     *
     * @param enableDrawable 触摸颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     * @param disableDrawable  正常颜色 例：Context.getResources.getDrawable(R.drawable/mipmap.xxx)
     * @return DevSelector
     */
    public static StateListDrawableFactory selectorEnable(Drawable enableDrawable, Drawable disableDrawable) {
        return StateListDrawableFactory.getInstance().selectorEnable(enableDrawable, disableDrawable);
    }


}
