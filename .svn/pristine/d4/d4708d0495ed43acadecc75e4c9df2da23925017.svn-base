package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.model.shopmall.LinkProductModel;
import com.yishanxiu.yishang.utils.Utils;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.R;


/**
 * Created by Jaelyn on 16/2/29.
 * 文章相关商品  含有外链
 */
public class LinkProductAdapter extends CommonBaseAdapter<LinkProductModel> {
	public LinkProductAdapter(Context context) {
		super(context, R.layout.link_relation_goods_item);
	}

	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, LinkProductModel model) {

		ImageView productPicIv;
		TextView productBrandTv;
		TextView productNameTv;
		TextView productSourceTv;
		TextView productPriceTv; // 原价
		TextView productDiscountPricetv; // 折扣价

		productPicIv = viewHolderHelper.getImageView(R.id.product_logo);
		productBrandTv = viewHolderHelper.getTextView(R.id.productBrand_tv);
		productNameTv = viewHolderHelper.getTextView(R.id.product_name_tv);
		productSourceTv = viewHolderHelper.getTextView(R.id.product_source_tv);
		productPriceTv = viewHolderHelper.getTextView(R.id.product_price_tv);
		productDiscountPricetv = viewHolderHelper.getTextView(R.id.product_price_discount_tv);

		String picPath = model.getPath();
		String scalePic = StringUtils.get_MImage(picPath);
		BitmapHelper.loadImage(mContext, scalePic, productPicIv, BitmapHelper.LoadImgOption.Square);

		productBrandTv.setText(model.getBrandName());
		productNameTv.setText(model.getProductName());
		productSourceTv.setText(model.getLinkFrom());
		double marketPrice = model.getCostPrice();
		int isDiscount = model.getIsDiscount();
		if (isDiscount>0) {
			double discountPrice = model.getDiscountPrice();
			productDiscountPricetv.setText(StringUtils.getFormatMoneyWithSign(discountPrice));
			productPriceTv.setText(StringUtils.getFormatMoneyWithSign(marketPrice));
			productPriceTv.setVisibility(View.VISIBLE);
			Utils.addDeleteLine(productPriceTv);
			productPriceTv.setSelected(true);
		} else {
			productPriceTv.setVisibility(View.GONE);
			productDiscountPricetv.setText(StringUtils.getFormatMoneyWithSign(marketPrice));
		}
	}


	/*public class LinkProductAdapterViewHolder {
		public ImageView productPicIv;

		*//**
		 * 品牌
		 *//*
		public TextView productBrandTv;

		*//**
		 * 商品名字
		 *//*
		public TextView productNameTv;
		*//**
		 * 来源
		 *//*
		public TextView productSourceTv;

		public TextView productPriceTv; // 原价

		public TextView productDiscountPricetv; // 折扣价

		public LinkProductAdapterViewHolder(View view) {
			productPicIv = (ImageView) view.findViewById(R.id.product_logo);
			productBrandTv = (TextView) view.findViewById(R.id.productBrand_tv);
			productNameTv = (TextView) view.findViewById(R.id.product_name_tv);
			productSourceTv = (TextView) view.findViewById(R.id.product_source_tv);
			productPriceTv = (TextView) view.findViewById(R.id.product_price_tv);
			productDiscountPricetv = (TextView) view.findViewById(R.id.product_price_discount_tv);


		}
	}*/

}
