package com.wenld.commonweights;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/2/3.
 * 自定义View 简化类
 */

public abstract class CustomView extends View {
    //画布大小
    protected int mWidth;
    protected int mHeight;

    //View默认最小宽度
    protected static final int DEFAULT_MIN_WIDTH = 200;

    //中心点 坐标
    protected int mCenterX;
    protected int mCenterY;

    //园半径，如非园型 请忽略此属性
    protected float circleR;

    public CustomView(Context context) {
        super(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initValue();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = measure(widthMeasureSpec);
        mHeight = measure(heightMeasureSpec);
        mCenterX = (mWidth - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
        mCenterY = (mHeight - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop();

        circleR = Math.min(mWidth - getPaddingLeft() - getPaddingRight(), mHeight - getPaddingTop() - getPaddingBottom()) / 2;
        setMeasuredDimension(mWidth, mHeight);
    }

    private int measure(int origin) {
        int result = dip2px(DEFAULT_MIN_WIDTH);
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        reset();
    }


    public abstract void initAttrs(AttributeSet attrs);

    /**
     * 初始化 画笔之类的东西
     */
    public abstract void initValue();

    /**
     * 布局大小改变
     */
    public abstract void reset();

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    /**
     * dip转化像素
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
