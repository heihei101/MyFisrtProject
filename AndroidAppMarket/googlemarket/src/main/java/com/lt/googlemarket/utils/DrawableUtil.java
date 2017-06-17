package com.lt.googlemarket.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by HASEE.
 */
public class DrawableUtil {
    /**
     * @param rgb   随机颜色
     * @param radius    圆角半径
     * @return  生成圆角的随机图片
     */
    public static Drawable drawDrawable(int rgb, int radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        //设置绘制的形状
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        //设置图片颜色
        gradientDrawable.setColor(rgb);
        //设置图片圆角半径
        gradientDrawable.setCornerRadius(radius);

        return gradientDrawable;
    }

    public static Drawable getStateListDrawable(Drawable normalDrawable, Drawable pressDrawable) {
        //选中时候的图片和未选中时候的图片
        StateListDrawable stateListDrawable = new StateListDrawable();
        //参数一:指定状态
        //参数二:此状态下图片效果

        /*<selector xmlns:android="http://schemas.android.com/apk/res/android">
            <item android:state_pressed="true" android:drawable="@drawable/list_item_bg_pressed"/>
            <item android:drawable="@drawable/list_item_bg_normal"/>
        </selector>*/
        //选中状态下,匹配选中图片
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},pressDrawable);
        //未选中的状态下,匹配normal图片
        stateListDrawable.addState(new int[]{},normalDrawable);

        return stateListDrawable;
    }
}
