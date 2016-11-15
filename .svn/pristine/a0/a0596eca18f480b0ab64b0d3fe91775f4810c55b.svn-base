package com.yishanxiu.yishang.view;

import java.util.ArrayList;
import java.util.List;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.WheelPicker.OnItemSelectedListener;
import com.yishanxiu.yishang.R;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 省市区PopupWindows
 * 
 * @author FangDongzhang
 *
 * @date 2016/7/26
 */
public class WheelpickerLinkagePopupWindows extends PopupWindow {

	private List<String> province = new ArrayList<String>();
	private List<String> city = new ArrayList<String>();
	private List<String> districtVo = new ArrayList<String>();
	private WheelPicker wheel_province;
	private WheelPicker wheel_city;
	private WheelPicker wheel_region;
	private int position;
	private int arg0;

	public WheelpickerLinkagePopupWindows(Context mContext, View parent, List<String> p, List<String> s,
			List<String> v) {
		View view = null;
		view = View.inflate(mContext, R.layout.item_popupwindows_wheelpicker_linkage, null);
		this.province = p;
		this.city = s;
		this.districtVo = v;

		view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
		LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		update();

		init(view);
		initDatas(position, arg0);
		initlistener();
	}

	public void setDatas(List<String> p, List<String> s, List<String> v, int position, int arg0) {
		this.province = p;
		this.city = s;
		this.districtVo = v;
		this.position = position;
		this.arg0 = arg0;
		initDatas(position, arg0);
	}

	private void initlistener() {
		wheel_province.setOnItemSelectedListener(itemSelectedListener);
		wheel_city.setOnItemSelectedListener(itemSelectedcity);

	}

	private void initDatas(int position, int arg0) {
		wheel_province.setData(this.province);
		wheel_province.setCyclic(false);
		wheel_province.setSelectedItemPosition(position);

		wheel_city.setData(this.city);
		wheel_city.setCyclic(false);
		wheel_city.setSelectedItemPosition(arg0);

		wheel_region.setOnItemSelectedListener(itemSelecteddistrict);
		wheel_region.setData(this.districtVo);
		wheel_region.setCyclic(false);
		wheel_region.setSelectedItemPosition(0);
	}

	private void init(View view) {
		TextView fixed = (TextView) view.findViewById(R.id.fixed);
		wheel_province = (WheelPicker) view.findViewById(R.id.wheel_province);
		wheel_city = (WheelPicker) view.findViewById(R.id.wheel_city);
		wheel_region = (WheelPicker) view.findViewById(R.id.wheel_region);

		fixed.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
				// modifyInfo("4", "provinceName", provinceName);
			}
		});

		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rlt_pop);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		TextView cancel = (TextView) view.findViewById(R.id.cancel);

		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

	}

	/**
	 * WheelPicker滑动结束监听器
	 */
	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(WheelPicker picker, Object data, int position) {
			// toast.showToast(position + "");
			// setDatas(province, city.get(position),
			// districtVo.get(position).get(0), position, 0);
			// arg = position;
			if (itemSelectedListen != null) {
				itemSelectedListen.onItemSelect(1, position);
			}
		}
	};
	/**
	 * WheelPicker滑动结束监听器
	 */
	private OnItemSelectedListener itemSelectedcity = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(WheelPicker picker, Object data, int position) {
			// setDatas(provinces, citys.get(arg),
			// districtVos.get(arg).get(position), arg, position);
			if (itemSelectedListen != null) {
				itemSelectedListen.onItemSelect(2, position);
			}
		}
	};
	/**
	 * WheelPicker滑动结束监听器
	 */
	private OnItemSelectedListener itemSelecteddistrict = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(WheelPicker picker, Object data, int position) {
			// setDatas(provinces, citys.get(arg),
			// districtVos.get(arg).get(position), arg, position);
			if (itemSelectedListen != null) {
				itemSelectedListen.onItemSelect(3, position);
			}
		}
	};

	/**
	 * 接口
	 */
	private OnItemSelectedListen itemSelectedListen = null;

	public interface OnItemSelectedListen {
		/**
		 * type 省：1；市2
		 * 
		 * @param position
		 */
		void onItemSelect(int type, int position);

	}

	public void setOnItemSelectedListener(OnItemSelectedListen onItemSelectedListen) {
		this.itemSelectedListen = onItemSelectedListen;
	}
}