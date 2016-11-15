package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.utils.Utils;

/**
 * Created by Jaelyn on 2015/9/9 0009.
 */
public class RelationProductAdapter2 extends CommonBaseAdapter<ProductInfoModel> {

	/**
	 * 是否显示描述字段 默认不显示
	 */
	private boolean showDiscri = false;

	public RelationProductAdapter2(Context context) {
		super(context, R.layout.relationgoods_item);
	}

	/**
	 * 是否显示描述信息
	 *
	 * @return
	 */
	public boolean isShowDiscri() {
		return showDiscri;
	}

	public void setShowDiscri(boolean isShowDiscri) {
		this.showDiscri = isShowDiscri;
	}

	public RelationProductAdapter2(Context context, int itemLayoutId) {
		super(context, itemLayoutId);
	}



	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, ProductInfoModel productInfoModel) {
		ImageView self_product_iv;// 自营标记
		TextView product_name_tv; // 商品名字
		TextView brand_name_tv; // 描述
		TextView product_price_tv; // 原价
		TextView product_price_discount_tv; // 折扣价
		ImageView relation_goods_iv;

		relation_goods_iv = viewHolderHelper.getImageView(R.id.relation_goods_iv);
		self_product_iv = viewHolderHelper.getImageView(R.id.self_product_iv);
		product_name_tv = viewHolderHelper.getTextView(R.id.product_name_tv);
		brand_name_tv = viewHolderHelper.getTextView(R.id.brand_name_tv);
		product_price_tv = viewHolderHelper.getTextView(R.id.product_price_tv);
		product_price_discount_tv = viewHolderHelper.getTextView(R.id.product_price_discount_tv);

		String picPath = productInfoModel.getPath();
		BitmapHelper.loadImage(mContext, picPath, relation_goods_iv, BitmapHelper.LoadImgOption.SquareMiddle, true);
		product_name_tv.setText(productInfoModel.getProductName());

		int isSelfProduct = productInfoModel.getLinkFrom();
		if (isSelfProduct>0) {
			self_product_iv.setVisibility(View.VISIBLE);
		} else {
			self_product_iv.setVisibility(View.GONE);
		}

		double marketPrice = productInfoModel.getCostPrice();
		int isDiscount = productInfoModel.getIsDiscount();

		if (isDiscount>0) {
			double discountPrice = productInfoModel.getDiscountPrice();
			product_price_discount_tv.setText(StringUtils.getFormatMoneyWithSign(discountPrice));
			product_price_tv.setText(StringUtils.getFormatMoneyWithSign(marketPrice));
			product_price_tv.setVisibility(View.VISIBLE);
			Utils.addDeleteLine(product_price_tv);
			product_price_tv.setSelected(true);
		} else {
			product_price_tv.setVisibility(View.GONE);
			product_price_discount_tv.setText(StringUtils.getFormatMoneyWithSign(marketPrice));
		}
		brand_name_tv.setText(productInfoModel.getBrandName());
	}

//	private class RelationGoodsAdapterViewHolder extends BGAViewHolderHelper {
//		private ImageView self_product_iv;// 自营标记
//		public TextView product_name_tv; // 商品名字
//		public TextView brand_name_tv; // 描述
//		public TextView product_price_tv; // 原价
//		public TextView product_price_discount_tv; // 折扣价
//		private CustomImageView relation_goods_iv;
//
//		public RelationGoodsAdapterViewHolder(ViewGroup adapterView, View convertView) {
//			super(adapterView, convertView);
//			intChildView(convertView);
//		}
//
//		public RelationGoodsAdapterViewHolder(RecyclerView recyclerView, View convertView) {
//			super(recyclerView, convertView);
//			intChildView(convertView);
//		}
//
//		private void intChildView(View view) {
//			relation_goods_iv = (CustomImageView) view.findViewById(R.id.relation_goods_iv);
//			self_product_iv = (ImageView) view.findViewById(R.id.self_product_iv);
//			product_name_tv = (TextView) view.findViewById(R.id.product_name_tv);
//			brand_name_tv = (TextView) view.findViewById(R.id.brand_name_tv);
//			product_price_tv = (TextView) view.findViewById(R.id.product_price_tv);
//			product_price_discount_tv = (TextView) view.findViewById(R.id.product_price_discount_tv);
//		}
//	}

}
