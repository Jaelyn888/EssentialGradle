package com.yishanxiu.yishang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridViewNoScroll extends GridView {

	public GridViewNoScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GridViewNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridViewNoScroll(Context context) {
		super(context);
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
