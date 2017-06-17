package com.lt.googlemarket.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/6/15.
 */

public class RatioImageView extends ImageView {
    public static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private float ratio;
    public RatioImageView(Context context) {
        this(context,null);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    private void initAttrs(AttributeSet attrs) {
        //宽高比例大小
        ratio = attrs.getAttributeFloatValue(NAMESPACE, "ratio", 0.0f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //widthMeasureSpec 维护了一个32位的2机制数，32个0或者1
        // 32位中有2位是用于控件模式的指定
        // 30位是用于控件宽度或者高度的指定
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //布局文件分析，width定值，和屏幕的宽度一样宽
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //通过固定的宽度大小，计算出和2.43等比例的高度的大小
        int height = (int) (widthSize/ratio);

        //固定宽度
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.EXACTLY);
        //固定高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
