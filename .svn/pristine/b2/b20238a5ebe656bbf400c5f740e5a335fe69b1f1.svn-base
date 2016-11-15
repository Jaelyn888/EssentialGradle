package com.yishanxiu.yishang.adapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.utils.BitmapUtils;
import com.yishanxiu.yishang.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.BatteryManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;

/**
 * 查询物流适配器
 * 
 * @author FangDongzhang 2016/7/14
 */
public class LogisticAdapter extends MyBaseAdapter {

	private JsonMap<String, Object> jsonMap = null;

	public LogisticAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.logistic_item, viewGroup, false);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		bindDatas(i, viewHolder);
		setTag(i, viewHolder);
		return view;

	}

	private void bindDatas(int i, ViewHolder viewHolder) {
		jsonMap = (JsonMap<String, Object>) datas.get(i);
		viewHolder.logistic_time.setText(jsonMap.getStringNoNull("time"));
		viewHolder.logistic_context.setText(jsonMap.getStringNoNull("context"));
		if (i == 0) {
			viewHolder.view_top.setVisibility(View.INVISIBLE);
			viewHolder.view_bottom.setVisibility(View.VISIBLE);
			viewHolder.view_divider.setVisibility(View.VISIBLE);
			viewHolder.logistic_context.setTextColor(ContextCompat.getColor(context, R.color.TextColorBlack));

			// Spannable sp1 = (Spannable)
			// viewHolder.logistic_context.getText();
			// sp1.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0,
			// viewHolder.logistic_context.length(),
			// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// Spannable sp = (Spannable) viewHolder.circle.getText();
			// sp.setSpan(new AbsoluteSizeSpan(24), 0,
			// viewHolder.circle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// viewHolder.circle.setspa(60);
			// viewHolder.circle.setHeight(60);
			viewHolder.circle.setBackgroundResource(R.drawable.circle_black);
			// Picasso.with(context).load(R.drawable.circle_black).into(viewHolder.circle);
			viewHolder.logistic_time.setTextColor(ContextCompat.getColor(context, R.color.TextColorBlack));
		} else {
			viewHolder.circle.setBackgroundResource(R.drawable.circle_gray);
		}
		if (i == datas.size() - 1) {
			viewHolder.view_divider.setVisibility(View.INVISIBLE);
			viewHolder.view_bottom.setVisibility(View.INVISIBLE);
		}
	}

	private void setTag(int i, ViewHolder viewHolder) {

	}

	private class ViewHolder {
		@ViewInject(R.id.logistic_time)
		private TextView logistic_time;

		@ViewInject(R.id.circle)
		private ImageView circle;

		@ViewInject(R.id.logistic_context)
		private TextView logistic_context;

		@ViewInject(R.id.view_top)
		private View view_top;

		@ViewInject(R.id.view_bottom)
		private View view_bottom;

		@ViewInject(R.id.view_divider)
		private View view_divider;
	}
}
