package com.yishanxiu.yishang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Jaelyn on 2015/9/2 0002.
 * 禁止触摸事件向上传递
 */
public class NoTouchLinearLayout extends LinearLayout {
	private boolean isTouchOutSideCancel=true;
	private TouchOutSideListener touchOutSideListener;



	public TouchOutSideListener getTouchOutSideListener() {
		return touchOutSideListener;
	}

	/**
	 * 设置触摸到外部控件的监听
	 * @param touchOutSideListener
	 */
	public void setTouchOutSideListener(TouchOutSideListener touchOutSideListener) {
		this.touchOutSideListener = touchOutSideListener;
	}

	/**
	 * 触摸外部是否拦截外事件上层传递
	 *
	 * @return
	 */
	public boolean isTouchOutSideCancel() {
		return isTouchOutSideCancel;
	}

	/**
	 * 设置是佛拦截事件
	 *
	 * @param isTouchOutSideCancel
	 */
	public void setTouchOutSideCancel(boolean isTouchOutSideCancel) {
		this.isTouchOutSideCancel = isTouchOutSideCancel;
	}

	public NoTouchLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		return super.dispatchTouchEvent(event);
	}

	/**
	 * 重写 拦截触摸事件消费
	 *
	 * @param event
	 * @return
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isTouchOutSideCancel) {
            setVisibility(GONE);
            if(touchOutSideListener!=null){
                touchOutSideListener.onTouchOutSideListener();
            }
			return true;
		} else {
			return super.onTouchEvent(event);
		}
	}

	/**
	 * 外部触摸事件的监听
	 */
	public interface TouchOutSideListener {
		void onTouchOutSideListener();
	}

	private String TAG = this.getClass().getSimpleName();
}
