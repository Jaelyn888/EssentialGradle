package com.yishanxiu.yishang.model.shopmall;

import com.yishanxiu.yishang.model.BaseModel;

/**
 * Created by Jaelyn on 2016/9/18.
 * 商品基本信息
 */
public class ProductInfoModel extends BaseModel {
	private String productId;
	/**
	 * 商品图
	 */
	private String path;
	/**
	 * 商品名字
	 */
	private String productName;
	/**
	 * 是否自营
	 */
	private int linkFrom;
	private double costPrice;
	/**
	 * 原价
	 */
	private double discountPrice;
	/**
	 * 是否打折
	 */
	private int isDiscount;
	/**
	 * 品牌名族
	 */
	private String brandName;

	private String createTime;


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getLinkFrom() {
		return linkFrom;
	}

	public void setLinkFrom(int linkFrom) {
		this.linkFrom = linkFrom;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(int isDiscount) {
		this.isDiscount = isDiscount;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
