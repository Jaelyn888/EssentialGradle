package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
 * 我的订单详情列表==商品列表适配器 2016/7/6
 *
 * @author FangDongzhang
 */
public class MyOrderCommentsAdapter extends MyBaseAdapter {

	/**
	 * 父类的位置
	 */
	private int parentPosition = -1;
	private Integer index = -1;

	public int getParentPosition() {
		return parentPosition;
	}

	public void setParentPosition(int parentPosition) {
		this.parentPosition = parentPosition;
	}

	public MyOrderCommentsAdapter(Context context) {
		super(context);
	}

	public MyOrderCommentsAdapter(Context context, List<? extends Map<String, ?>> datas) {
		super(context, datas);
	}

	public void setDataOrderStatus(int orderStatus) {
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.item_my_order_comments, viewGroup, false);
			shopCartAdapterViewHolder = new ShopAddOrderGoodsAdapterViewHolder();
			ViewUtils.inject(shopCartAdapterViewHolder, view);
			view.setTag(shopCartAdapterViewHolder);
		} else {
			shopCartAdapterViewHolder = (ShopAddOrderGoodsAdapterViewHolder) view.getTag();
		}
		MyTextWatcher textWatcher = new MyTextWatcher(shopCartAdapterViewHolder);
		shopCartAdapterViewHolder.editText_comments.addTextChangedListener(textWatcher);
		bindDatas(i, shopCartAdapterViewHolder);
		setItemTag(i, shopCartAdapterViewHolder);
//		shopCartAdapterViewHolder.editText_comments.clearFocus();
//		if (index != -1 && index == i) {
//			shopCartAdapterViewHolder.editText_comments.requestFocus();
//		}
		return view;
	}

	private void bindDatas(int i, ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder) {
		JsonMap<String, Object> jsonMap = (JsonMap<String, Object>) datas.get(i);
		shopCartAdapterViewHolder.i_s_a_o_tv_productname.setText(jsonMap.getStringNoNull("productName"));
		shopCartAdapterViewHolder.i_s_a_o_tv_productnum.setText(
				context.getString(R.string.shopping_tv_text_productnum_qz) + jsonMap.getStringNoNull("productCount"));
		shopCartAdapterViewHolder.productSpecification.setText(jsonMap.getStringNoNull("productSpecification"));
//		shopCartAdapterViewHolder.goods_price_tv
//				.setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("productPrice")));

		if(jsonMap.getStringNoNull("productActivityPrice").equals("") || jsonMap.getStringNoNull("productActivityPrice").equals(jsonMap.getStringNoNull("productPrice"))){
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
		BitmapHelper.loadImage(context, str,
				shopCartAdapterViewHolder.i_s_pl_aiv_pic1, BitmapHelper.LoadImgOption.BrandLogo, true);
	}

	private void setItemTag(int i, ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder) {
		shopCartAdapterViewHolder.goods_discribe_tv.setTag(i);
		shopCartAdapterViewHolder.editText_comments.setTag(i);
	}

	class ShopAddOrderGoodsAdapterViewHolder {
		@ViewInject(R.id.i_s_a_o_tv_productname)
		TextView i_s_a_o_tv_productname;

		@ViewInject(R.id.editText_comments)
		EditText editText_comments;
		@ViewInject(R.id.i_s_a_o_tv_productnum)
		TextView i_s_a_o_tv_productnum;
		@ViewInject(R.id.productBrand_tv)
		TextView goods_discribe_tv;

		@OnClick(R.id.productBrand_tv)
		public void returnRefund(View v) {
			if (itemCompountClickListener != null) {
				itemCompountClickListener.itemCompountClick(parentPosition,
						Constant.ShopCartItemCompontType.RETURN_REFUND, (Integer) v.getTag());
			}
		}

		@ViewInject(R.id.productSpecification)
		TextView productSpecification;
		@ViewInject(R.id.i_s_pl_aiv_pic1)
		ImageView i_s_pl_aiv_pic1;
		@ViewInject(R.id.goods_price_tv)
		TextView goods_price_tv;
		@ViewInject(R.id.product_price_tv)
		private TextView product_price_tv; // 原价
	}

	class MyTextWatcher implements TextWatcher {
		ShopAddOrderGoodsAdapterViewHolder adapterViewHolder;

		public MyTextWatcher(ShopAddOrderGoodsAdapterViewHolder shopAddOrderGoodsAdapterViewHolder) {
			this.adapterViewHolder = shopAddOrderGoodsAdapterViewHolder;
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s != null && !"".equals(s.toString())) {
				int position = (Integer) adapterViewHolder.editText_comments.getTag();
				JsonMap<String, Object> jsonMap = (JsonMap<String, Object>) datas.get(position);
				jsonMap.put("list_item_inputvalue", s.toString());// 当EditText数据发生改变的时候存到data变量中
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
		 * @param itemPosition 第几个条目
		 * @param type         类型
		 */
		void itemCompountClick(int itemPosition, Constant.ShopCartItemCompontType type, int childIndex);
	}
}
