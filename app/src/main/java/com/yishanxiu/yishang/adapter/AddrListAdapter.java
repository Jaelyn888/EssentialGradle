package com.yishanxiu.yishang.adapter;

import com.yishanxiu.yishang.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.utils.Constant;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;

/**
 * @author FangDongzhang 2016/7/25 收获地址列表显示
 */
public class AddrListAdapter extends MyBaseAdapter {
	/**
	 * 父类的位置
	 */
	private int parentPosition = -1;
	/**
	 * 类型 0：选择地址，1：管理地址
	 */
	private int type = 1;

	private int selectID;

	// 自定义的选中方法
	public void setSelectID(int position) {
		selectID = position;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public AddrListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		GoodsListAdapterViewHolder goodsListAdapterViewHolder;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.addr_list_item, viewGroup, false);
			goodsListAdapterViewHolder = new GoodsListAdapterViewHolder();
			ViewUtils.inject(goodsListAdapterViewHolder, view);
			view.setTag(goodsListAdapterViewHolder);
			if (type == 0) {
				goodsListAdapterViewHolder.rllt_addr.setVisibility(View.GONE);
			} else {
				goodsListAdapterViewHolder.rllt_addr.setVisibility(View.VISIBLE);
			}
		} else {
			goodsListAdapterViewHolder = (GoodsListAdapterViewHolder) view.getTag();
		}
		setItemTag(i, goodsListAdapterViewHolder);
		bindDatas(i, goodsListAdapterViewHolder);
		return view;
	}

	private void bindDatas(int i, GoodsListAdapterViewHolder goodsListAdapterViewHolder) {
		JsonMap<String, Object> jsonMap = (JsonMap<String, Object>) datas.get(i);
		goodsListAdapterViewHolder.select_addr_cb.setSelected(jsonMap.getBoolean("isdefault"));
		goodsListAdapterViewHolder.tv_shouhuren.setText(jsonMap.getStringNoNull("consignee"));
		goodsListAdapterViewHolder.tv_shouhuren_dianhua.setText(jsonMap.getStringNoNull("phone"));
		goodsListAdapterViewHolder.tv_shouhuren_dizhi.setText(jsonMap.getStringNoNull("consigneeAddress"));

		// 核心方法，判断单选按钮被按下的位置与之前的位置是否相等，然后做相应的操作。
		if (selectID == -1) {
			goodsListAdapterViewHolder.check.setVisibility(View.GONE);
		} else if (selectID == i) {
			goodsListAdapterViewHolder.check.setVisibility(View.VISIBLE);
		} else {
			goodsListAdapterViewHolder.check.setVisibility(View.INVISIBLE);
		}

	}

	private void setItemTag(int i, GoodsListAdapterViewHolder goodsListAdapterViewHolder) {
		goodsListAdapterViewHolder.select_addr_cb.setTag(i);
		goodsListAdapterViewHolder.tv_shouhuren.setTag(i);
		goodsListAdapterViewHolder.tv_shouhuren_dianhua.setTag(i);
		goodsListAdapterViewHolder.tv_shouhuren_dizhi.setTag(i);
		goodsListAdapterViewHolder.rllt_addr.setTag(i);
		goodsListAdapterViewHolder.edit_addr.setTag(i);
		goodsListAdapterViewHolder.delete_addr.setTag(i);
	}

	private class GoodsListAdapterViewHolder {
		/**
		 * 收货人
		 */
		@ViewInject(R.id.s_a_o_tv_shouhuoren)
		private TextView tv_shouhuren;
		/**
		 * 收货人电话
		 */
		@ViewInject(R.id.s_a_o_tv_phone)
		private TextView tv_shouhuren_dianhua;
		/**
		 * 收货人地址
		 */
		@ViewInject(R.id.s_a_o_tv_address)
		private TextView tv_shouhuren_dizhi;
		/**
		 * 收货人地址
		 */
		@ViewInject(R.id.rllt_addr)
		RelativeLayout rllt_addr;
		/**
		 * 是否选中
		 */
		@ViewInject(R.id.select_addr_cb)
		TextView select_addr_cb;
		/**
		 * 是否选中
		 */
		@ViewInject(R.id.check)
		ImageView check;

		@OnClick(R.id.select_addr_cb)
		public void select_goods_cb_click(View view) {
			if (itemCompountClickListener != null) {
				int index = (Integer) view.getTag();
				JsonMap<String, Object> dataJsonMap = (JsonMap<String, Object>) datas.get(index);
				boolean isDefault = dataJsonMap.getBoolean("isdefault");
				if (isDefault) {
				} else {
					view.setSelected(!isDefault);
					itemCompountClickListener.itemCompountClick(parentPosition, Constant.UserRecipientType.SET_DEFAULT,
							index);
				}

			}
		}

		/**
		 * 编辑
		 */
		@ViewInject(R.id.edit_addr)
		TextView edit_addr;

		@OnClick(R.id.edit_addr)
		public void edit_addr_click(View view) {
			if (itemCompountClickListener != null) {
				itemCompountClickListener.itemCompountClick(parentPosition, Constant.UserRecipientType.EDIT_ADDR,
						(Integer) view.getTag());
			}
		}

		/**
		 * 删除
		 */
		@ViewInject(R.id.delete_addr)
		TextView delete_addr;

		@OnClick(R.id.delete_addr)
		public void delete_addr_click(View view) {
			if (itemCompountClickListener != null) {
				itemCompountClickListener.itemCompountClick(parentPosition, Constant.UserRecipientType.DELETE_ADDR,
						(Integer) view.getTag());
			}
		}

	}

	private ItemCompountClickListener itemCompountClickListener;

	/**
	 * 设置控件的点击事件
	 *
	 * @param itemCompountClickListener
	 */
	public void setItemCompountClickListener(ItemCompountClickListener itemCompountClickListener) {
		this.itemCompountClickListener = itemCompountClickListener;
	}

	public interface ItemCompountClickListener {
		/**
		 * 点击事件的回调接口
		 *
		 * @param itemPosition
		 *            第几个条目
		 * @param type
		 *            类型
		 */
		void itemCompountClick(int itemPosition, Constant.UserRecipientType type, int childIndex);
	}

}
