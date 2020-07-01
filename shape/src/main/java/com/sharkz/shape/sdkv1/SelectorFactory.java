package com.sharkz.shape.sdkv1;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.View;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/7/1  17:00
 * 描    述 封装StateListDrawable，配合ShapeBuilder使用效果更佳，替代selector类型xml文件，减小apk体积
 * 修订历史：
 * ================================================
 */
public class SelectorFactory {

    private StateListDrawable drawable;
    private Drawable mTemplate;

    public SelectorFactory(Drawable drawable) {
        this.mTemplate = drawable;
        this.drawable = new StateListDrawable();
    }

    /**
     * @param drawable 传入默认状态下的drawable
     */
    public static SelectorFactory create(Drawable drawable) {
        return new SelectorFactory(drawable);
    }

    /**
     * 添加状态
     * @param shape 状态对应的shape
     * @param state 状态类型
     *              （这里要注意添加的顺序，只要有一个状态与之相配，背景就会被换掉。
     *              所以不要把大范围放在前面了，会造成没有什么效果了。）
     */
    public SelectorFactory addShape(Drawable shape, int... state) {
        drawable.addState(state, shape);
        return this;
    }

    /**
     * 设置背景，记得实现onClick事件监听，修改对应状态，不然无效果
     */
    public void build(View v) {
        addShape(mTemplate);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(drawable);
        }else{
            v.setBackgroundDrawable(drawable);
        }
    }
    
}
