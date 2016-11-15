package com.shizhefei.view.indicator.transition;

import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author LuckyJayce
 *自定义tab布局   textview id  必须是系统的  text1
 */
public class MyOnTransitionTextListener extends OnTransitionTextListener {

	/**
	 * 如果tabItemView 不是目标的TextView，那么你可以重写该方法返回实际要变化的TextView
	 * 
	 * @param tabItemView
	 *            Indicator的每�?项的view
	 * @param position
	 *            view在Indicator的位置索�?
	 * @return
	 */
	public TextView getTextView(View tabItemView, int position) {
		if(tabItemView==null){
			return null;
		}
		return (TextView) tabItemView.findViewById(android.R.id.text1);
	}


}
