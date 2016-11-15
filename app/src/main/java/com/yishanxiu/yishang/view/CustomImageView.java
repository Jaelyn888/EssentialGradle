package com.yishanxiu.yishang.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yishanxiu.yishang.R;

/**
 * Created by Jaelyn on 2015/10/4 0004.
 * 自定义图片缩放比例
 */
public class CustomImageView extends ImageView {
    private int mCellWidth;
    private int mCellHeight;
    /**
     * imageView 缩放比例
     */
    private float scaleRate = 0;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyle, 0);
        scaleRate = a.getFloat(R.styleable.CustomImageView_scaleRate, 0);
        a.recycle();
    }

    public void setmCellWidth(int w) {
        this.mCellWidth = w;
        this.requestLayout();
    }

    public void setmCellHeight(int h) {
        this.mCellHeight = h;
        this.requestLayout();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (scaleRate != 0) {
            // 父容器传过来的宽度方向上的模式
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            // 父容器传过来的高度方向上的模式
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);

            // 父容器传过来的宽度的值
            int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
                    - getPaddingRight();
            // 父容器传过来的高度的值
            int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop()
                    - getPaddingBottom();

            if (widthMode == MeasureSpec.EXACTLY
                    && heightMode != MeasureSpec.EXACTLY && scaleRate != 0.0f) {
                // 判断条件为，宽度模式为Exactly，也就是填充父窗体或者是指定宽度；
                // 且高度模式不是Exaclty，代表设置的既不是fill_parent也不是具体的值，于是需要具体测量
                // 且图片的宽高比已经赋值完毕，不再是0.0f
                // 表示宽度确定，要测量高度
                height = (int) (width / scaleRate + 0.5f);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                        MeasureSpec.EXACTLY);
            } else if (widthMode != MeasureSpec.EXACTLY
                    && heightMode == MeasureSpec.EXACTLY && scaleRate != 0.0f) {
                // 判断条件跟上面的相反，宽度方向和高度方向的条件互换
                // 表示高度确定，要测量宽度
                width = (int) (height / scaleRate + 0.5f);

                widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                        MeasureSpec.EXACTLY);
            }

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}