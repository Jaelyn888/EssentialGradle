/*
Copyright 2014 shizhefei（LuckyJayce�?

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.shizhefei.view.indicator.slidebar;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.ScrollIndicatorView;

/**
 * tab的文本多宽scrollbar就显示多宽，实现新浪个人首页的tab的scrollbar效果
 * 
 * LuckyJayce
 *
 */
public class MyTextWidthColorBar extends TextWidthColorBar {
	private Indicator indicator;

	public MyTextWidthColorBar(Context context, Indicator indicator, int color, int height) {
		super(context, indicator,color, height);
		this.indicator = indicator;
	}

	/**
	 * 如果tabItemView 不是目标的TextView，那么你可以重写该方法返回实际要变化的TextView
	 *
	 * @param position
	 * @return
	 */
	protected TextView getTextView(int position) {
		View view=indicator.getItemView(position);
		if(view==null){
			return null;
		}
		return (TextView) view.findViewById(android.R.id.text1);
	}

}
