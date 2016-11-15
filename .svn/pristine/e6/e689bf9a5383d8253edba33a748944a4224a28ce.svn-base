package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.yishanxiu.yishang.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/25.
 */
public class ShopIntroductAdapter extends MyBaseAdapter {


	public ShopIntroductAdapter(Context context) {
		super(context);
	}

	public ShopIntroductAdapter(Context context, List<? extends Map<String, ?>> datas) {
		super(context, datas);
	}



	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.item_shop_introduct, viewGroup, false);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		return view;
	}

	private class ViewHolder {
		private TextView tv_shopintroduct_name;
		private TextView tv_shopintroduct_phone;
		private TextView tv_shopintroduct_address;

		public ViewHolder(View view) {
			ViewUtils.inject(this, view);
		}
	}

	private TelPhoneClick telPhoneClick;

	public void setTelPhoneClick(TelPhoneClick telPhoneClick) {
		this.telPhoneClick = telPhoneClick;
	}

	public interface TelPhoneClick{
		void telPhoneClick(String phone);
	}
}
