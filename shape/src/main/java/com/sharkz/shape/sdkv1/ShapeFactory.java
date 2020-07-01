package com.sharkz.shape.sdkv1;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/7/1  16:27
 * 描    述 代码动态创建 Shape
 * 修订历史：
 * ================================================
 */
public class ShapeFactory {


    /**
     * 矩形 椭圆 圆环 线
     */
    public static final int RECTANGLE = android.graphics.drawable.GradientDrawable.RECTANGLE;
    public static final int OVAL = android.graphics.drawable.GradientDrawable.OVAL;
    public static final int RING = android.graphics.drawable.GradientDrawable.RING;
    public static final int LINE = android.graphics.drawable.GradientDrawable.LINE;


    /**
     * GradientDrawable在Android中便是shape标签的代码实现，利用GradientDrawable也可以创建出各种形状。
     */
    private GradientDrawable drawable;

    /**
     * 属性容器
     */
    private AttrContainer container;

    /**
     * 是否运行（起作用）
     */
    private boolean isOperate;


    // =============================================================================================


    /**
     * 构造方法 私有
     */
    private ShapeFactory() {
        drawable = new GradientDrawable();
        // API 16
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            container = new AttrContainer();
        }
    }

    /**
     * 创建当前实例
     */
    public static ShapeFactory create() {
        return new ShapeFactory();
    }

    /**
     * 重置
     *
     * @param v 目标View
     */
    public static void clearBg(View v) {
        v.setBackgroundResource(0);
    }

    // =============================================================================================


    /**
     * 设置shape的type类型
     *
     * @param type {{@link #LINE,RECTANGLE,OVAL,RING}}
     */
    public ShapeFactory Type(int type) {
        drawable.setShape(type);
        if (container != null) {
            container.type = type;
        }
        return this;
    }


    /**
     * 设置Stroke（实线描边）
     *
     * @param px    width,需要px值
     * @param color color值
     */
    public ShapeFactory Stroke(int px, int color) {
        drawable.setStroke(px, color);
        if (container != null) {
            container.stokewidth = px;
            container.stokeColor = color;
        }
        return this;
    }

    /**
     * 设置Stroke（虚线描边）
     *
     * @param px        width,需要px值
     * @param color     color值
     * @param dashWidth dashWidth 横线的宽度
     * @param dashGap   dashGap 点与点间的距离
     */
    public ShapeFactory Stroke(int px, int color, int dashWidth, int dashGap) {
        drawable.setStroke(px, color, dashWidth, dashGap);
        if (container != null) {
            container.stokewidth = px;
            container.stokeColor = color;
            container.dashWidth = dashWidth;
            container.dashGap = dashGap;
        }
        return this;
    }


    /**
     * 设置圆角
     *
     * @param px 圆角角度，四个角保持一致
     */
    public ShapeFactory Radius(float px) {
        drawable.setCornerRadius(px);
        if (container != null) {
            container.setRadius(px, px, px, px);
        }
        return this;
    }

    /**
     * 设置圆角
     *
     * @param topLeft  左上
     * @param topRight 右上
     * @param botRight 右下
     * @param botLeft  左下
     */
    public ShapeFactory RoundRadius(float topLeft, float topRight, float botRight, float botLeft) {
        drawable.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, botRight, botRight, botLeft, botLeft});
        if (container != null) {
            container.setRadius(topLeft, topRight, botLeft, botRight);
        }
        return this;
    }


    /**
     * 设置填充色
     *
     * @param color 背景颜色 填充色
     */
    public ShapeFactory Solid(int color) {
        drawable.setColor(color);
        if (container != null) {
            container.soild = color;
        }
        return this;
    }

    /**
     * 设置填充色 渐变，默认的Linear渐变
     *
     * @param startColor  开始颜色
     * @param centerColor 中心颜色
     * @param endColor    结束颜色
     */
    public ShapeFactory Gradient(int startColor, int centerColor, int endColor) {
        return GradientInit(GradientDrawable.Orientation.TOP_BOTTOM, startColor, centerColor, endColor);
    }

    /**
     * 渐变，设置角度(实质调用的Gradient(GradientDrawable.Orientation orientation, int startColor, int
     * centerColor, int endColor)方法)
     *
     * @param angle       角度，需要是45的整数倍
     * @param startColor  开始颜色
     * @param centerColor 中心颜色
     * @param endColor    结束颜色
     */
    public ShapeFactory Gradient(int angle, int startColor, int centerColor, int endColor) {
        angle = angle % 360;
        GradientDrawable.Orientation orientation = null;
        switch (angle) {
            case 0:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case 45:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case 90:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 135:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case 180:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case 225:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
            case 270:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 315:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
        }
        return Gradient(orientation, startColor, centerColor, endColor);
    }

    /**
     * 渐变，设置渐变方向
     *
     * @param orientation 方向支持类型
     *                    0-LEFT_RIGHT
     *                    45-BL_TR
     *                    90-BOTTOM_TOP
     *                    135-BR_TL
     *                    180-RIGHT_LEFT
     *                    225-TR_BL
     *                    270-TOP_BOTTOM
     *                    315-TL_BR
     * @param startColor  开始颜色
     * @param centerColor 中心颜色
     * @param endColor    结束颜色
     */
    public ShapeFactory Gradient(GradientDrawable.Orientation orientation, int startColor, int centerColor, int endColor) {
        return GradientInit(orientation, startColor, centerColor, endColor);
    }

    /**
     * 兼容低版本，重新构造drawable，对应调用operateMethod方法重新build，
     * 保证新的drawable与原始drawabel相同
     */
    private ShapeFactory GradientInit(GradientDrawable.Orientation orientation, int startColor, int centerColor, int endColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            drawable.setOrientation(orientation);
            drawable.setColors(new int[]{startColor, centerColor, endColor});
        } else {
            isOperate = true;
            drawable = new GradientDrawable(orientation, new int[]{startColor, centerColor, endColor});
        }
        return this;
    }

    /**
     * 兼容低版本，重新构造drawable，对应调用operateMethod方法重新build，
     * 保证新的drawable与原始drawabel相同
     */
    public ShapeFactory GradientInit(GradientDrawable.Orientation orientation, int... colors) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            drawable.setOrientation(orientation);
            drawable.setColors(colors);
        } else {
            isOperate = true;
            drawable = new GradientDrawable(orientation, colors);
        }
        return this;
    }

    /**
     * 渐变type
     *
     * @param type linear (default.)-LINEAR_GRADIENT
     *             circular-RADIAL_GRADIENT
     *             sweep-SWEEP_GRADIENT
     * @return
     */
    public ShapeFactory GradientType(int type) {
        drawable.setGradientType(type);
        if (container != null) {
            container.gradientType = type;
        }
        return this;
    }

    /**
     * 这两个属性只有在type不为linear情况下起作用。
     *
     * @param x 相对X的渐变位置
     * @param y 相对Y的渐变位置
     * @return
     */
    public ShapeFactory GradientCenter(float x, float y) {
        drawable.setGradientCenter(x, y);
        if (container != null) {
            container.gradientCenterX = x;
            container.gradientCenterY = y;
        }
        return this;
    }

    /**
     * 该属性只有在type="radial"有效
     *
     * @param radius 渐变颜色的半径
     * @return
     */
    public ShapeFactory GradientRadius(float radius) {
        drawable.setGradientRadius(radius);
        if (container != null) {
            container.gradinetRadius = radius;
        }
        return this;
    }


    /**
     * 设置size
     *
     * @param width  宽
     * @param height 高
     * @return
     */
    public ShapeFactory setSize(int width, int height) {
        drawable.setSize(width, height);
        if (container != null) {
            container.width = width;
            container.height = height;
        }
        return this;
    }


    /**
     * 传入View，设置Bac
     */
    public void build(View v) {
        build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(drawable);
        } else {
            v.setBackgroundDrawable(drawable);
        }
    }


    /**
     * 返回构建的drawable
     */
    public GradientDrawable build() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return drawable;
        } else {
            if (isOperate) {
                operateMethod();
            }
        }
        return drawable;
    }

    /**
     * Build.VERSION_CODES.JELLY_BEAN（4.1）以下渐变需要重新构造drawable，所以需要重新build
     */
    private void operateMethod() {
        if (container != null) {
            this.Type(container.type)
                    .Stroke(container.stokewidth, container.stokeColor, container.dashWidth, container.dashGap)
                    .RoundRadius(container.topLeft, container.topRight, container.botRight, container.botLeft)
                    .setSize(container.width, container.height)
                    .GradientType(container.gradientType)
                    .GradientCenter(container.gradientCenterX, container.gradientCenterY)
                    .GradientRadius(container.gradinetRadius);
            if (container.soild != 0) {
                Solid(container.soild);
            }
        }
    }


    /**
     * 兼容低版本  4.1 一下
     */
    private class AttrContainer {
        public int type;
        public int stokewidth;
        public int stokeColor;
        public int dashWidth;
        public int dashGap;
        public int soild;
        public float topLeft, topRight, botLeft, botRight;
        public int width, height;
        public int gradientType;
        public float gradinetRadius;

        public float gradientCenterX, gradientCenterY;

        private void setRadius(float topleft, float topright, float botleft, float botright) {
            this.topLeft = topleft;
            this.topRight = topright;
            this.botLeft = botleft;
            this.botRight = botright;
        }
    }

}
