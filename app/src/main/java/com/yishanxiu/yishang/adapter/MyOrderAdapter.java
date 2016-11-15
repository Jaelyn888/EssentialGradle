package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.view.ListViewNoScroll;

import net.tsz.afinal.json.JsonMap;

import java.util.List;
import java.util.Map;

/**
 * 2016/7/6 我的订单的适配器
 *
 * @author FangDongzhang
 */
public class MyOrderAdapter extends MyBaseAdapter implements MyOrderProductAdapter.ItemCompountClickListener {

	/**
	 * 显示分割线
	 */
	private boolean isShowViewDivider;

	public void setShowViewDivider(boolean showViewDivider) {
		isShowViewDivider = showViewDivider;
	}

	public MyOrderAdapter(Context context) {
		super(context);
	}

	public MyOrderAdapter(Context context, List<? extends Map<String, ?>> datas) {
		super(context, datas);
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ShopCartAdapterViewHolder shopCartAdapterViewHolder;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.my_order_item, viewGroup, false);
			shopCartAdapterViewHolder = new ShopCartAdapterViewHolder();
			ViewUtils.inject(shopCartAdapterViewHolder, view);
			view.setTag(shopCartAdapterViewHolder);
		} else {
			shopCartAdapterViewHolder = (ShopCartAdapterViewHolder) view.getTag();
		}

		if (isShowViewDivider) {
			if (i == getCount() - 1) {
				shopCartAdapterViewHolder.view_divider.setVisibility(View.GONE);
			} else {
				shopCartAdapterViewHolder.view_divider.setVisibility(View.VISIBLE);
			}
		} else {
			shopCartAdapterViewHolder.view_divider.setVisibility(View.GONE);
		}
		setTags(i, shopCartAdapterViewHolder);
		bindDatas(i, shopCartAdapterViewHolder);
		return view;
	}

	private void setTags(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder) {
		shopCartAdapterViewHolder.lv_noScrool.setTag(i);
		shopCartAdapterViewHolder.brandName_tv.setTag(i);
		shopCartAdapterViewHolder.cancel_order.setTag(i);
		shopCartAdapterViewHolder.pro_count.setTag(i);
		shopCartAdapterViewHolder.payment.setTag(i);
		shopCartAdapterViewHolder.llt_cancelorder_payment.setTag(i);
	}

	private List<JsonMap<String, Object>> jsonMaps;
	private JsonMap<String, Object> jsonMap;
	private MyOrderProductAdapter goodsListAdapter;
	private MyReturnRefundOrderProductAdapter myReturnRefundOrderProductAdapter;

	private void bindDatas(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder) {
		if (datas.size() > 0) {
			jsonMap = (JsonMap<String, Object>) datas.get(i);
			if (jsonMap.size() > 0 && jsonMap.getList_JsonMap("orderProducts").size() > 0) {
				BitmapHelper.loadImage(context, jsonMap.getStringNoNull("logoPath"),
						shopCartAdapterViewHolder.brand_logo, BitmapHelper.LoadImgOption.BrandLogo, true);

				shopCartAdapterViewHolder.brandName_tv.setText(jsonMap.getStringNoNull("name"));
				shopCartAdapterViewHolder.llt_pro_count.setVisibility(View.VISIBLE);
				String tmpStr = context.getString(R.string.totle_pro_count);
				tmpStr = String.format(tmpStr, jsonMap.getStringNoNull("orderProductCount"));
				shopCartAdapterViewHolder.pro_count.setText(tmpStr);

				shopCartAdapterViewHolder.brand_totle_price
						.setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("finalPrice")));

				if (jsonMap.containsKey("returnRefundOrderNumber")) {
					shopCartAdapterViewHolder.llt_pro_count.setVisibility(View.GONE);
					shopCartAdapterViewHolder.order_status
							.setText("退款编号：" + jsonMap.getStringNoNull("returnRefundOrderNumber"));

					if (jsonMap.getInt("returnRefundLatestStatus") == 7) {
						shopCartAdapterViewHolder.cancel_order.setVisibility(View.VISIBLE);
						shopCartAdapterViewHolder.payment.setBackgroundResource(R.drawable.cus_black_bt);
						shopCartAdapterViewHolder.payment.setTextColor(ContextCompat.getColor(context, R.color.white));
						shopCartAdapterViewHolder.payment.setText(R.string.do_goods_return);
						shopCartAdapterViewHolder.cancel_order.setText(R.string.read_progress);
					} else {
						shopCartAdapterViewHolder.payment.setText(R.string.read_progress);
						shopCartAdapterViewHolder.payment
								.setTextColor(ContextCompat.getColor(context, R.color.TextColorBlack));
						shopCartAdapterViewHolder.payment.setBackgroundResource(R.drawable.cus_black_white_bt);
						shopCartAdapterViewHolder.cancel_order.setVisibility(View.GONE);
					}

				} else {
					switch (jsonMap.getInt("orderStatus")) {
					case 0:
						shopCartAdapterViewHolder.order_status.setText(R.string.trading_close);
						shopCartAdapterViewHolder.llt_cancelorder_payment.setVisibility(View.GONE);
						break;
					case 1:
						shopCartAdapterViewHolder.order_status
								.setText(context.getResources().getString(R.string.pending_payment));
						shopCartAdapterViewHolder.llt_cancelorder_payment.setVisibility(View.VISIBLE);
						shopCartAdapterViewHolder.cancel_order.setText(R.string.cancel_order);
						shopCartAdapterViewHolder.payment.setText(R.string.pay);
						break;
					case 2:
						shopCartAdapterViewHolder.order_status
								.setText(context.getResources().getString(R.string.delivering));
						shopCartAdapterViewHolder.llt_cancelorder_payment.setVisibility(View.GONE);
						break;
					case 3:
						shopCartAdapterViewHolder.order_status
								.setText(context.getResources().getString(R.string.inbound));
						shopCartAdapterViewHolder.llt_cancelorder_payment.setVisibility(View.VISIBLE);
						shopCartAdapterViewHolder.cancel_order.setText(R.string.read_logist);
						shopCartAdapterViewHolder.payment.setText(R.string.confrim_receipt);
						break;
					case 4:
						shopCartAdapterViewHolder.llt_cancelorder_payment.setVisibility(View.VISIBLE);
						shopCartAdapterViewHolder.cancel_order.setText(R.string.read_logist);
						if (jsonMap.getInt("isComment") == 0) {
							shopCartAdapterViewHolder.order_status
									.setText(context.getResources().getString(R.string.pending_reviews));
							shopCartAdapterViewHolder.payment.setText(R.string.commens_t);
						} else {
							shopCartAdapterViewHolder.order_status
									.setText(context.getResources().getString(R.string.trasaction_complete));
							shopCartAdapterViewHolder.payment.setText(R.string.read_commens);

						}
						break;
					case 5:
						shopCartAdapterViewHolder.order_status.setText(R.string.trading_close);
						shopCartAdapterViewHolder.llt_cancelorder_payment.setVisibility(View.GONE);
						break;
					default:
						break;
					}

				}
				bindGoodsList(i, shopCartAdapterViewHolder, jsonMap);
			}
		}
	}

	private void bindGoodsList(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder,
			JsonMap<String, Object> jsonMap) {
		if (jsonMap.containsKey("returnRefundOrderNumber")) {
			myReturnRefundOrderProductAdapter = new MyReturnRefundOrderProductAdapter(context);
			myReturnRefundOrderProductAdapter.setParentPosition(i);
			jsonMaps = jsonMap.getList_JsonMap("orderProducts");
			jsonMaps.get(0).put("returnRefundType",jsonMap.getStringNoNull("returnRefundType"));
			jsonMaps.get(0).put("returnRefundLatestStatus",jsonMap.getStringNoNull("returnRefundLatestStatus"));
			myReturnRefundOrderProductAdapter.setDatas(jsonMaps);
//			myReturnRefundOrderProductAdapter.setItemCompountClickListener(this);
			shopCartAdapterViewHolder.lv_noScrool.setAdapter(myReturnRefundOrderProductAdapter);
		}else{
			goodsListAdapter = new MyOrderProductAdapter(context);
			goodsListAdapter.setParentPosition(i);
			jsonMaps = jsonMap.getList_JsonMap("orderProducts");
			goodsListAdapter.setDatas(jsonMaps);
			goodsListAdapter.setItemCompountClickListener(this);
			shopCartAdapterViewHolder.lv_noScrool.setAdapter(goodsListAdapter);
		}


	}

	@Override
	public void itemCompountClick(int itemPosition, Constant.ShopCartItemCompontType type, int childIndex) {
		if (itemClickListener != null) {
			itemClickListener.onItemClick(itemPosition, type, childIndex);
		}
	}

	private class ShopCartAdapterViewHolder {
		@ViewInject(R.id.brand_logo)
		ImageView brand_logo;

		@ViewInject(R.id.brandName_tv)
		TextView brandName_tv;

		@ViewInject(R.id.order_status)
		TextView order_status;

		@ViewInject(R.id.pro_count)
		TextView pro_count;

		@OnClick(R.id.brandName_tv)
		public void brandName_tv_click(View view) {
			if (itemClickListener != null) {
				itemClickListener.onItemClick((Integer) lv_noScrool.getTag(),
						Constant.ShopCartItemCompontType.CLICK_BRAND_NAME, -1);
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

		@ViewInject(R.id.llt_cancelorder_payment)
		LinearLayout llt_cancelorder_payment;

		@ViewInject(R.id.llt_pro_count)
		LinearLayout llt_pro_count;

		@ViewInject(R.id.cancel_order)
		TextView cancel_order;

		@OnClick(R.id.cancel_order)
		public void cancel_order_click(View view) {
			if (itemClickListener != null) {
				itemClickListener.onItemClick((Integer) lv_noScrool.getTag(),
						Constant.ShopCartItemCompontType.CANCEL_ORDER, (Integer) view.getTag());
			}
		}

		@ViewInject(R.id.payment)
		TextView payment;

		@OnClick(R.id.payment)
		public void payment_click(View view) {
			if (itemClickListener != null) {
				itemClickListener.onItemClick((Integer) lv_noScrool.getTag(), Constant.ShopCartItemCompontType.PAYMENT,
						(Integer) view.getTag());
			}
		}

		@ViewInject(R.id.brand_totle_price)
		TextView brand_totle_price;

		@ViewInject(R.id.view_divider)
		View view_divider;

	}

	private OnItemClickListener itemClickListener;

	public interface OnItemClickListener {
		/**
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
