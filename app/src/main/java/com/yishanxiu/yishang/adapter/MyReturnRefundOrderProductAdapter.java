package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.utils.Utils;

import net.tsz.afinal.json.JsonMap;

import java.util.List;
import java.util.Map;

/**
 * 我的退货订单列表==商品列表适配器 2016/9/23
 * 
 * @author FangDongzhang
 */
public class MyReturnRefundOrderProductAdapter extends MyBaseAdapter {

	/**
	 * 父类的位置
	 */
	private int parentPosition = -1;

	private int pronum;

	public int getParentPosition() {
		return parentPosition;
	}

	public void setParentPosition(int parentPosition) {
		this.parentPosition = parentPosition;
	}

	public MyReturnRefundOrderProductAdapter(Context context) {
		super(context);
	}

	public MyReturnRefundOrderProductAdapter(Context context, List<? extends Map<String, ?>> datas) {
		super(context, datas);
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.item_shopping_addrder, viewGroup, false);
			shopCartAdapterViewHolder = new ShopAddOrderGoodsAdapterViewHolder();
			ViewUtils.inject(shopCartAdapterViewHolder, view);
			view.setTag(shopCartAdapterViewHolder);
		} else {
			shopCartAdapterViewHolder = (ShopAddOrderGoodsAdapterViewHolder) view.getTag();
		}
		bindDatas(i, shopCartAdapterViewHolder);
		setItemTag(i, shopCartAdapterViewHolder);
		return view;
	}

	private void bindDatas(int i, ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder) {
		JsonMap<String, Object> jsonMap = (JsonMap<String, Object>) datas.get(i);
		shopCartAdapterViewHolder.i_s_a_o_tv_productname.setText(jsonMap.getStringNoNull("productName"));
		shopCartAdapterViewHolder.i_s_a_o_tv_productnum.setText(
				context.getString(R.string.shopping_tv_text_productnum_qz) + jsonMap.getStringNoNull("productCount"));
		shopCartAdapterViewHolder.productSpecification.setText(jsonMap.getStringNoNull("productSpecification"));

		if((jsonMap.getStringNoNull("productActivityPrice").equals("")) ||jsonMap.getStringNoNull("productActivityPrice").equals(jsonMap.getStringNoNull("productPrice"))){
			shopCartAdapterViewHolder.goods_price_tv
					.setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("productPrice")));
		}else{
			double marketPrice = jsonMap.getDouble("productActivityPrice");

			shopCartAdapterViewHolder.goods_price_tv
					.setText(StringUtils.getFormatMoneyWithSign(marketPrice));
			shopCartAdapterViewHolder.product_price_tv.setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("productPrice")));
			shopCartAdapterViewHolder.product_price_tv.setVisibility(View.VISIBLE);
			Utils.addDeleteLine(shopCartAdapterViewHolder.product_price_tv);
			shopCartAdapterViewHolder.product_price_tv.setSelected(true);

		}

		String str = jsonMap.getStringNoNull("productPic");
		BitmapHelper.loadImage(context, str, shopCartAdapterViewHolder.i_s_pl_aiv_pic1,
				BitmapHelper.LoadImgOption.BrandLogo);
		shopCartAdapterViewHolder.goods_discribe_tv.setText("");
		int returnRefundStatus = jsonMap.getInt("returnRefundLatestStatus");
		int returnRefundType = jsonMap.getInt("returnRefundType");
		switch (returnRefundStatus) {
			case 1:
			{
				if (returnRefundType ==1) {
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退货待商家审核");
				}else if (returnRefundType ==2){
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退款待商家审核");
				}
			}
			break;
			case 2:
			{
				if (returnRefundType ==1) {
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退货商家驳回");
				}else if (returnRefundType ==2){
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退款商家驳回");
				}
			}
			break;
			case 3:
			{
				if (returnRefundType ==1) {
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退货平台审核中");
				}else if (returnRefundType ==2){
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退款平台审核中");
				}
			}
			break;
			case 4:
			{
				if (returnRefundType ==1) {
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退货平台处理中");
				}else if (returnRefundType ==2){
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退款平台处理中");
				}
			}
			break;
			case 5:
			{
				if (returnRefundType ==1) {
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退货平台驳回");
				}else if (returnRefundType ==2){
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退款平台驳回");
				}
			}
			break;
			case 6:
			{
				if (returnRefundType ==1) {
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退货完成");
				}else if (returnRefundType ==2){
					shopCartAdapterViewHolder.goods_discribe_tv.setText("退款完成");
				}
			}
			break;
			case 7:
			{
				shopCartAdapterViewHolder.goods_discribe_tv.setText("待买家发货");
			}
			break;
			case 8:
			{
				shopCartAdapterViewHolder.goods_discribe_tv.setText("待商家收货");
			}
			break;
			case 9:
			{
				shopCartAdapterViewHolder.goods_discribe_tv.setText("商品驳回");
			}
			break;
			default:
				break;
		}
	}

	private void setItemTag(int i, ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder) {
		shopCartAdapterViewHolder.goods_discribe_tv.setTag(i);
	}

	class ShopAddOrderGoodsAdapterViewHolder {
		@ViewInject(R.id.i_s_a_o_tv_productname)
		TextView i_s_a_o_tv_productname;
		@ViewInject(R.id.i_s_a_o_tv_productnum)
		TextView i_s_a_o_tv_productnum;
		@ViewInject(R.id.productBrand_tv)
		TextView goods_discribe_tv;
		@ViewInject(R.id.product_price_tv)
		private TextView product_price_tv; // 原价

		@OnClick(R.id.productBrand_tv)
		public void returnRefund(View v) {/*
			if (itemCompountClickListener != null && goods_discribe_tv.getText() != null) {
				itemCompountClickListener.itemCompountClick(parentPosition,
						Constant.ShopCartItemCompontType.RETURN_REFUND, (Integer) v.getTag());
			}
		*/}

		@ViewInject(R.id.productSpecification)
		TextView productSpecification;
		@ViewInject(R.id.i_s_pl_aiv_pic1)
		ImageView i_s_pl_aiv_pic1;
		@ViewInject(R.id.goods_price_tv)
		TextView goods_price_tv;
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
		void itemCompountClick(int itemPosition, Constant.ShopCartItemCompontType type, int childIndex);
	}
}
