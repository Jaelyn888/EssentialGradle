package com.yishanxiu.yishang.model.recommend;


import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;

/**
 * Created by Jaelyn on 2016/9/23.
 */
public class RecommendProModel extends ProductInfoModel {
	//太原 推荐
	private int IsDiscount  ;
	private double DiscountPrice;

	@Override
	public int getIsDiscount() {
		return IsDiscount;
	}

	@Override
	public void setIsDiscount(int isDiscount) {
		IsDiscount = isDiscount;
	}

	@Override
	public double getDiscountPrice() {
		return DiscountPrice;
	}

	@Override
	public void setDiscountPrice(double discountPrice) {
		DiscountPrice = discountPrice;
	}
}
