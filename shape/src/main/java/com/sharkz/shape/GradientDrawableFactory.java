package com.sharkz.shape;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/7/1  16:27
 * 描    述 代码动态创建 Shape
 * 修订历史：
 * ================================================
 */
public class GradientDrawableFactory implements IShapeFactory<Drawable, View> {

    /**
     * Shape样式 --> 矩形 椭圆 圆环 线
     */
    public static final int RECTANGLE = android.graphics.drawable.GradientDrawable.RECTANGLE;
    public static final int OVAL = android.graphics.drawable.GradientDrawable.OVAL;
    public static final int RING = android.graphics.drawable.GradientDrawable.RING;
    public static final int LINE = android.graphics.drawable.GradientDrawable.LINE;
    @IntDef({RECTANGLE, OVAL, RING, LINE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Shape {
    }

    /**
     * Shape渐变色方向 --> 线性渐变方向定义
     */
    public static final String TOP_BOTTOM = "TOP_BOTTOM";   //上到下渐变
    public static final String TR_BL = "TR_BL";             //右上到左下渐变
    public static final String RIGHT_LEFT = "RIGHT_LEFT";   //右到左渐变
    public static final String BR_TL = "BR_TL";             //右下到左上渐变
    public static final String BOTTOM_TOP = "BOTTOM_TOP";   //下到上渐变
    public static final String BL_TR = "BL_TR";             //左下到右上渐变
    public static final String LEFT_RIGHT = "LEFT_RIGHT";   //左到右渐变
    public static final String TL_BR = "TL_BR";             //左上到右下渐变
    @StringDef({TOP_BOTTOM, TR_BL, RIGHT_LEFT, BR_TL, BOTTOM_TOP, BL_TR, LEFT_RIGHT, TL_BR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GradientOrientation {
    }


    // =============================================================================================


    static GradientDrawableFactory mGradientDrawableFactory;

    //渐变类型  默认线性
    private int gradientType = GradientDrawable.LINEAR_GRADIENT;

    //样式形状 默认矩形
    private static int shape = RECTANGLE;

    //背景颜色
    private int backgroundColor;
    //边框线颜色
    private int lineColor;
    //实线宽度 默认1px
    private int lineWidth = 1;

    /*虚线参数*/
    //虚线边框宽度 默认1px
    private int dashLineWidth = 1;
    //虚线颜色
    private int dashLineColor = 1;
    //虚线的实线宽度
    private float dashWidth = 1;
    //虚线间隙宽度
    private float dashGap = 1;

    /*渐变参数*/
    //渐变颜色数组
    private int[] gradientColors;
    //辐射渐变半径
    private float radialRadius;

    //渐变方向 默认从上到下
    private @GradientOrientation
    String gradientOrientation = TOP_BOTTOM;

    /*设置样式标志位*/
    //是否设置背景
    private boolean isBackgroundColor;
    //是否边框实线样式
    private boolean isLine;
    //是否边框虚线样式
    private boolean isDashLine;
    //是否渐变
    private boolean isGradient;

    /*设置圆角*/
    //是否圆角
    private boolean isRadius;
    //右上圆角弧度
    private float topRightRadius = 0;
    //左上圆角弧度
    private float topLeftRadius = 0;
    //右下圆角弧度
    private float bottomRightRadius = 0;
    //左下圆角弧度
    private float bottomLeftRadius = 0;


    // =============================================================================================


    /**
     * 创建当前实例
     *
     * @param shapeModel shape样式
     * @return
     */
    public static GradientDrawableFactory getInstance(int shapeModel) {
        mGradientDrawableFactory = new GradientDrawableFactory();
        shape = shapeModel;
        return mGradientDrawableFactory;
    }


    // =============================================================================================


    /**
     * 背景填充样式
     *
     * @param backgroundColorResId 例：R.color.colorPrimary
     * @return BaseShape
     */
    public GradientDrawableFactory solid(@ColorRes int backgroundColorResId) {
        this.isBackgroundColor = true;
        this.backgroundColor = ShapeFactory.getContext().getResources().getColor(backgroundColorResId);
        return this;
    }

    /**
     * 背景填充样式
     *
     * @param backgroundColor 例：#ffffff
     * @return BaseShape
     */
    public GradientDrawableFactory solid(String backgroundColor) {
        if (TextUtils.isEmpty(backgroundColor)) {
            backgroundColor = "#333333";
        }
        this.isBackgroundColor = true;
        this.backgroundColor = Color.parseColor(backgroundColor);
        return this;
    }


    /**
     * 边框实线样式
     *
     * @param lineColorResId 边框线颜色 例：R.color.colorPrimary
     * @param lineWidth      边框线宽度 dp
     * @return BaseShape
     */
    public GradientDrawableFactory line(int lineWidth, @ColorRes int lineColorResId) {
        this.isLine = true;
        this.lineWidth = lineWidth;
        this.lineColor = ShapeFactory.getContext().getResources().getColor(lineColorResId);
        return this;
    }

    /**
     * 边框实线样式
     *
     * @param lineColor 边框线颜色 例：#ffffff
     * @param lineWidth 边框线宽度 dp
     * @return BaseShape
     */
    public GradientDrawableFactory line(int lineWidth, String lineColor) {
        this.isLine = true;
        this.lineWidth = lineWidth;
        this.lineColor = Color.parseColor(lineColor);
        return this;
    }

    /**
     * 边框虚线样式
     *
     * @param dashLineColorResId 边框线颜色 例：R.color.colorPrimary
     * @param dashLineWidth      边框虚线宽度 dp
     * @param dashWidth          虚线宽度 dp
     * @param dashGap            间隙宽度 dp
     * @return BaseShape
     */
    public GradientDrawableFactory dashLine(int dashLineWidth, @ColorRes int dashLineColorResId, float dashWidth, float dashGap) {
        this.isDashLine = true;
        this.dashLineWidth = dashLineWidth;
        this.dashWidth = dashWidth;
        this.dashGap = dashGap;
        this.dashLineColor = ShapeFactory.getContext().getResources().getColor(dashLineColorResId);
        return this;
    }


    /**
     * 边框虚线样式
     *
     * @param dashLineColor 边框线颜色 例：#ffffff
     * @param dashLineWidth 边框虚线宽度 dp
     * @param dashWidth     虚线宽度 dp
     * @param dashGap       间隙宽度 dp
     * @return BaseShape
     */
    public GradientDrawableFactory dashLine(int dashLineWidth, String dashLineColor, float dashWidth, float dashGap) {
        this.isDashLine = true;
        this.dashLineWidth = dashLineWidth;
        this.dashWidth = dashWidth;
        this.dashGap = dashGap;
        this.dashLineColor = Color.parseColor(dashLineColor);
        return this;
    }


    // =============================================================================================


    /**
     * 渐变样式
     * 默认 线性渐变 GradientDrawable.LINEAR_GRADIENT
     * 默认方向 从上到下渐变 GradientDrawable.Orientation.TOP_BOTTOM
     *
     * @param startColor 渐变开始端颜色 例：R.color.colorPrimary
     * @param endColor   渐变结束端颜色 例：R.color.colorPrimary
     * @return BaseShape
     */
    public GradientDrawableFactory gradient(@ColorRes int startColor, @ColorRes int endColor) {
        this.isGradient = true;
        this.gradientColors = new int[2];
        this.gradientColors[0] = ShapeFactory.getContext().getResources().getColor(startColor);
        this.gradientColors[1] = ShapeFactory.getContext().getResources().getColor(endColor);
        this.gradientType = GradientDrawable.LINEAR_GRADIENT;
        this.gradientOrientation = TOP_BOTTOM;
        return this;
    }

    /**
     * 渐变样式
     * 默认 线性渐变 GradientDrawable.LINEAR_GRADIENT
     * 默认方向 从上到下渐变 GradientDrawable.Orientation.TOP_BOTTOM
     *
     * @param startColor 渐变开始端颜色 例：#ffffff
     * @param endColor   渐变结束端颜色 例：#ffffff
     * @return BaseShape
     */
    public GradientDrawableFactory gradient(String startColor, String endColor) {
        this.isGradient = true;
        this.gradientColors = new int[2];
        this.gradientColors[0] = Color.parseColor(startColor);
        this.gradientColors[1] = Color.parseColor(endColor);
        this.gradientType = GradientDrawable.LINEAR_GRADIENT;
        this.gradientOrientation = TOP_BOTTOM;
        return this;
    }


    /**
     * 线性渐变样式
     * 默认方向 从上到下渐变 GradientDrawable.Orientation.TOP_BOTTOM
     *
     * @param gradientColorsResId 渐变颜色数组 数组元素 例：R.color.colorPrimary
     * @return BaseShape
     */
    public GradientDrawableFactory gradientLinear(@ColorRes int... gradientColorsResId) {
        this.isGradient = true;
        this.gradientType = GradientDrawable.LINEAR_GRADIENT;
        this.gradientOrientation = TOP_BOTTOM;
        if (gradientColorsResId.length > 1) {
            this.gradientColors = new int[gradientColorsResId.length];
            for (int i = 0; i < gradientColorsResId.length; i++) {
                this.gradientColors[i] = ShapeFactory.getContext().getResources().getColor(gradientColorsResId[i]);
            }
        } else {
            throw new ExceptionInInitializerError("渐变颜色数组至少需要两个颜色");
        }
        return this;
    }


    /**
     * 线性渐变样式
     * 默认方向 从上到下渐变 TOP_BOTTOM
     *
     * @param gradientColorsResId 渐变颜色数组 数组元素 例：#ffffff
     * @return BaseShape
     */
    public GradientDrawableFactory gradientLinear(String... gradientColorsResId) {
        this.isGradient = true;
        this.gradientType = GradientDrawable.LINEAR_GRADIENT;
        this.gradientOrientation = TOP_BOTTOM;
        if (gradientColorsResId.length > 1) {
            this.gradientColors = new int[gradientColorsResId.length];
            for (int i = 0; i < gradientColorsResId.length; i++) {
                this.gradientColors[i] = Color.parseColor(gradientColorsResId[i]);
            }
        } else {
            throw new ExceptionInInitializerError("渐变颜色数组至少需要两个颜色");
        }
        return this;
    }

    /**
     * 设置渐变方向
     *
     * @param gradientOrientation 渐变方向
     * @return BaseShape
     */
    public GradientDrawableFactory orientation(@GradientOrientation String gradientOrientation) {
        this.gradientOrientation = gradientOrientation;
        return this;
    }

    /**
     * 扫描渐变样式
     *
     * @param gradientColors 渐变颜色数组 数组元素 例：R.color.colorPrimary
     * @return BaseShape
     */
    public GradientDrawableFactory gradientSweep(@ColorRes int... gradientColors) {
        this.isGradient = true;
        this.gradientType = GradientDrawable.SWEEP_GRADIENT;
        if (gradientColors.length > 1) {
            this.gradientColors = new int[gradientColors.length];
            for (int i = 0; i < gradientColors.length; i++) {
                this.gradientColors[i] = ShapeFactory.getContext().getResources().getColor(gradientColors[i]);
            }
        } else {
            throw new ExceptionInInitializerError("渐变颜色数组至少需要两个颜色");
        }
        return this;
    }


    /**
     * 扫描渐变样式
     *
     * @param gradientColors 渐变颜色数组 数组元素 例：#ffffff
     * @return BaseShape
     */
    public GradientDrawableFactory gradientSweep(String... gradientColors) {
        this.isGradient = true;
        this.gradientType = GradientDrawable.SWEEP_GRADIENT;
        if (gradientColors.length > 1) {
            this.gradientColors = new int[gradientColors.length];
            for (int i = 0; i < gradientColors.length; i++) {
                this.gradientColors[i] = Color.parseColor(gradientColors[i]);
            }
        } else {
            throw new ExceptionInInitializerError("渐变颜色数组至少需要两个颜色");
        }
        return this;
    }


    /**
     * 辐射渐变样式
     *
     * @param gradientColors 渐变颜色数组 数组元素 例：R.color.colorPrimary
     * @return BaseShape
     */
    public GradientDrawableFactory gradientRadial(float radialRadius, int... gradientColors) {
        this.isGradient = true;
        this.gradientType = GradientDrawable.RADIAL_GRADIENT;
        this.radialRadius = radialRadius;
        if (gradientColors.length > 1) {
            this.gradientColors = new int[gradientColors.length];
            for (int i = 0; i < gradientColors.length; i++) {
                this.gradientColors[i] = ShapeFactory.getContext().getResources().getColor(gradientColors[i]);
            }
        } else {
            throw new ExceptionInInitializerError("渐变颜色数组至少需要两个颜色");
        }
        return this;
    }


    /**
     * 辐射渐变样式
     *
     * @param gradientColors 渐变颜色数组 数组元素 例：#ffffff
     * @param radialRadius   辐射渐变半径
     * @return BaseShape
     */
    public GradientDrawableFactory gradientRadial(float radialRadius, String... gradientColors) {
        this.isGradient = true;
        this.gradientType = GradientDrawable.RADIAL_GRADIENT;
        this.radialRadius = radialRadius;
        if (gradientColors.length > 1) {
            this.gradientColors = new int[gradientColors.length];
            for (int i = 0; i < gradientColors.length; i++) {
                this.gradientColors[i] = Color.parseColor(gradientColors[i]);
            }
        } else {
            throw new ExceptionInInitializerError("渐变颜色数组至少需要两个颜色");
        }
        return this;
    }


    // =============================================================================================


    /**
     * 设置圆角弧度 默认设置4个圆角
     *
     * @param radius 圆角弧度
     */
    public GradientDrawableFactory radius(float radius) {
        this.isRadius = true;
        this.topRightRadius = radius;
        this.topLeftRadius = radius;
        this.bottomRightRadius = radius;
        this.bottomLeftRadius = radius;
        return this;
    }

    /**
     * 设置右上圆角
     *
     * @param topRightRadius 圆角弧度
     * @return DevShape
     */
    public GradientDrawableFactory trRadius(float topRightRadius) {
        this.isRadius = true;
        this.topRightRadius = topRightRadius;
        return this;
    }


    /**
     * 设置左上圆角
     *
     * @param topLeftRadius 圆角弧度
     * @return DevShape
     */
    public GradientDrawableFactory tlRadius(float topLeftRadius) {
        this.isRadius = true;
        this.topLeftRadius = topLeftRadius;
        return this;
    }


    /**
     * 设置右下圆角
     *
     * @param bottomRightRadius 圆角弧度
     * @return DevShape
     */
    public GradientDrawableFactory brRadius(float bottomRightRadius) {
        this.isRadius = true;
        this.bottomRightRadius = bottomRightRadius;
        return this;
    }

    /**
     * 设置左下圆角
     *
     * @param bottomLeftRadius 圆角弧度
     * @return DevShape
     */
    public GradientDrawableFactory blRadius(float bottomLeftRadius) {
        this.isRadius = true;
        this.bottomLeftRadius = bottomLeftRadius;
        return this;
    }


    // =============================================================================================


    @Override
    public void into(View view) {
        view.setBackground(createShape());
    }

    @Override
    public Drawable build() {
        return createShape();
    }


    // =============================================================================================


    /**
     * 生成样式
     *
     * @return Drawable
     */
    private Drawable createShape() {
        GradientDrawable drawable = new GradientDrawable();
        if (shape == OVAL) {
            drawable.setShape(GradientDrawable.OVAL);
        } else {
            drawable.setShape(GradientDrawable.RECTANGLE);
        }
        if (isRadius) {
            float[] radii = {dip2px(topLeftRadius), dip2px(topLeftRadius),
                    dip2px(topRightRadius), dip2px(topRightRadius),
                    dip2px(bottomRightRadius), dip2px(bottomRightRadius),
                    dip2px(bottomLeftRadius), dip2px(bottomLeftRadius)};
            drawable.setCornerRadii(radii);//设置圆角
        }

        if (isBackgroundColor) {
            drawable.setColor(backgroundColor);// 设置背景颜色
        }

        if (isLine) {
            drawable.setStroke(dip2px(lineWidth), lineColor);// 设置边框线
        }

        if (isDashLine) {
            drawable.setStroke(dip2px(dashLineWidth), dashLineColor, dip2px(dashWidth), dip2px(dashGap));//设置虚线
        }

        if (isGradient) {
            switch (gradientType) {
                case GradientDrawable.LINEAR_GRADIENT:
                    drawable.setOrientation(createGradientOrientation());//设置线性渐变方向
                    break;
                case GradientDrawable.RADIAL_GRADIENT:
                    drawable.setGradientRadius(dip2px(radialRadius));//设置辐射渐变辐射范围半径
                    break;
                case GradientDrawable.SWEEP_GRADIENT:
                    break;
            }
            drawable.setGradientType(gradientType);
            drawable.setColors(gradientColors);
        }

        return drawable;
    }

    /**
     * 设置线性渐变方向
     *
     * @return GradientDrawable.Orientation
     */
    private GradientDrawable.Orientation createGradientOrientation() {
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TOP_BOTTOM;
        switch (gradientOrientation) {
            case TOP_BOTTOM:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case TR_BL:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
            case RIGHT_LEFT:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case BR_TL:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case BOTTOM_TOP:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case BL_TR:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case LEFT_RIGHT:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case TL_BR:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;

        }
        return orientation;
    }


    /**
     * 单位转换工具类
     *
     * @param dipValue 值
     * @return 返回值
     */
    private int dip2px(float dipValue) {
        final float scale = ShapeFactory.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
