package com.yishanxiu.yishang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Jaelyn on 2015/10/4 0004.
 */
public class WebViewNoScroll extends WebView {
	public WebViewNoScroll(Context context) {
		super(context);
	}

	public WebViewNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WebViewNoScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	/**
	 * 设置不滚动
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
