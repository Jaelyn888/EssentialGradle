package com.yishanxiu.yishang.view;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.yishanxiu.yishang.R;

/**
 * 圆形头像
 * @author sk06
 *
 */
public class CircleImageView extends com.lzy.widget.CircleImageView {



    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, com.lzy.widget.R.styleable.CircleImageView, defStyle, 0);
        int mBorderWidth = a.getDimensionPixelSize(com.lzy.widget.R.styleable.CircleImageView_civ_BorderWidth, 0);
        int mBorderColor = a.getColor(com.lzy.widget.R.styleable.CircleImageView_civ_BorderColor, Color.TRANSPARENT);
        a.recycle();
        setBorderColor(mBorderColor);
        setBorderWidth(mBorderWidth);
    }
}