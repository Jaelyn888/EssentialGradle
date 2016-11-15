package com.yishanxiu.yishang.model.shopmall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jaelyn on 2016/9/19.
 * 商品详情信息
 */
public class ProductDetailModel extends ProductInfoModel {
	private String shopId;

	private String brandId;
	private String pageDesc;
	private String logoPath;

	private String words;
	private String salesName;


	private ArrayList<ProductPicModel> picList;
	private int SaleStatus;
	private int spuStock;

	private int isPromotion;
	private String promotinEndTime;

	private String promotionName;
	private int commentCount;
	private ProductCommentModel recommendComment;
	private int isCollection;

	/**
	 * 原价格区间
	 */
	private double minPriceOrigin;
	private double maxPriceOrigin;

	/**
	 * 折扣价格区间
	 */
	private double discountMinPrice;
	private double discountMaxPrice;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getPageDesc() {
		return pageDesc;
	}

	public void setPageDesc(String pageDesc) {
		this.pageDesc = pageDesc;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public ArrayList<ProductPicModel> getPicList() {
		return picList;
	}

	public void setPicList(ArrayList<ProductPicModel> picList) {
		this.picList = picList;
	}

	public int getSaleStatus() {
		return SaleStatus;
	}

	public void setSaleStatus(int saleStatus) {
		SaleStatus = saleStatus;
	}

	public int getSpuStock() {
		return spuStock;
	}

	public void setSpuStock(int spuStock) {
		this.spuStock = spuStock;
	}

	public int getIsPromotion() {
		return isPromotion;
	}

	public void setIsPromotion(int isPromotion) {
		this.isPromotion = isPromotion;
	}

	public String getPromotinEndTime() {
		return promotinEndTime;
	}

	public void setPromotinEndTime(String promotinEndTime) {
		this.promotinEndTime = promotinEndTime;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public ProductCommentModel getRecommendComment() {
		return recommendComment;
	}

	public void setRecommendComment(ProductCommentModel recommendComment) {
		this.recommendComment = recommendComment;
	}

	public int getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(int isCollection) {
		this.isCollection = isCollection;
	}

	public double getMinPriceOrigin() {
		return minPriceOrigin;
	}

	public void setMinPriceOrigin(double minPriceOrigin) {
		this.minPriceOrigin = minPriceOrigin;
	}

	public double getMaxPriceOrigin() {
		return maxPriceOrigin;
	}

	public void setMaxPriceOrigin(double maxPriceOrigin) {
		this.maxPriceOrigin = maxPriceOrigin;
	}

	public double getDiscountMinPrice() {
		return discountMinPrice;
	}

	public void setDiscountMinPrice(double discountMinPrice) {
		this.discountMinPrice = discountMinPrice;
	}

	public double getDiscountMaxPrice() {
		return discountMaxPrice;
	}

	public void setDiscountMaxPrice(double discountMaxPrice) {
		this.discountMaxPrice = discountMaxPrice;
	}
}
