package com.yishanxiu.yishang.adapter;

import java.util.List;
import java.util.Map;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.view.ListViewNoScroll;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;

/**
 * 2016/7/6 系统消息的适配器
 * 
 * @author FangDongzhang
 */
public class SystemAdapter extends MyBaseAdapter implements MyOrderProductAdapter.ItemCompountClickListener {

	public SystemAdapter(Context context) {
		super(context);
	}

	public SystemAdapter(Context context, List<? extends Map<String, ?>> datas) {
		super(context, datas);
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ShopCartAdapterViewHolder shopCartAdapterViewHolder;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.system_msg_item, viewGroup, false);
			shopCartAdapterViewHolder = new ShopCartAdapterViewHolder();
			ViewUtils.inject(shopCartAdapterViewHolder, view);
			view.setTag(shopCartAdapterViewHolder);
		} else {
			shopCartAdapterViewHolder = (ShopCartAdapterViewHolder) view.getTag();
		}
		setTags(i, shopCartAdapterViewHolder);
		bindDatas(i, shopCartAdapterViewHolder);
		return view;
	}

	private void setTags(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder) {
		shopCartAdapterViewHolder.lv_noScrool.setTag(i);
		shopCartAdapterViewHolder.createTime.setTag(i);
		shopCartAdapterViewHolder.r_detail.setTag(i);
	}

	private List<JsonMap<String, Object>> jsonMaps;
	private JsonMap<String, Object> jsonMap;
	private SystemOrderAdapter goodsListAdapter;

	private void bindDatas(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder) {
		jsonMap = (JsonMap<String, Object>) datas.get(i);
		shopCartAdapterViewHolder.createTime.setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("createTime")));

		bindGoodsList(i, shopCartAdapterViewHolder, jsonMap);
	}

	private void bindGoodsList(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder,
			JsonMap<String, Object> jsonMap) {
		goodsListAdapter = new SystemOrderAdapter(context);
		// goodsListAdapter.setParentPosition(i);
		jsonMaps = jsonMap.getList_JsonMap("data");
		goodsListAdapter.setDatas(jsonMaps);
		// goodsListAdapter.setItemCompountClickListener(this);
		shopCartAdapterViewHolder.lv_noScrool.setAdapter(goodsListAdapter);

	}

	@Override
	public void itemCompountClick(int itemPosition, Constant.ShopCartItemCompontType type, int childIndex) {
		if (itemClickListener != null) {
			itemClickListener.onItemClick(itemPosition, type, childIndex);
		}
	}

	private class ShopCartAdapterViewHolder {

		@ViewInject(R.id.createTime)
		TextView createTime;

		@ViewInject(R.id.r_detail)
		TextView r_detail;

		@OnClick(R.id.r_detail)
		public void brandName_tv_click(View view) {
			if (itemClickListener != null) {
				itemClickListener.onItemClick((Integer) lv_noScrool.getTag(),
						Constant.ShopCartItemCompontType.CLICK_GOODS_ITEM, -1);
			}
		}

		@ViewInject(R.id.lv_noScrool)
		ListViewNoScroll lv_noScrool;

		@OnItemClick(R.id.lv_noScrool)
		public void lv_noScrool_item_click(AdapterView<?> adapterView, View view, int i, long l) {
			if (itemClickListener != null) {
				itemClickListener.onItemClick((Integer) lv_noScrool.getTag(),
						Constant.ShopCartItemCompontType.CLICK_GOODS_ITEM, i);
			}
		}

	}

	private OnItemClickListener itemClickListener;

	public interface OnItemClickListener {
		/**
		 *
		 * @param position
		 *            根位置
		 * @param shopCartitemType
		 *            类别
		 * @param index
		 *            点击具体的商品位置
		 */
		void onItemClick(int position, Constant.ShopCartItemCompontType shopCartitemType, int index);

	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.itemClickListener = listener;
	}
}
