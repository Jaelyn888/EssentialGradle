package net.tsz.afinal.toast;

/**
 * Created by Jaelyn on 16/3/4.
 */
import android.content.Context;

public class Toast {
    private ToastUtil util;
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;

    public Toast(Context context) {
        this.util = ToastUtil.initToast(context);
    }

    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast toast = new Toast(context);
        toast.setText(text);
        return toast;
    }

    public static Toast makeText(Context context, int resId, int duration) {
        return makeText(context, context.getResources().getString(resId), duration);
    }

    public void setText(CharSequence s) {
        this.util.getToast().setText(s);
    }

    public void setText(int resId) {
        this.util.getToast().setText(resId);
    }

    public void show() {
        this.util.showToast();
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        this.util.getToast().setGravity(gravity, xOffset, yOffset);
    }

    public void setDuration(int duration) {
    }

    public void setMargin(float horizontalMargin, float verticalMargin) {
        this.util.getToast().setMargin(horizontalMargin, verticalMargin);
    }

    public void cancel() {
        this.util.hideToast();
    }
}
