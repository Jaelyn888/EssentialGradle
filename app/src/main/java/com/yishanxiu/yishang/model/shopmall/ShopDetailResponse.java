package com.yishanxiu.yishang.model.shopmall;

import java.util.List;

/**
 * Created by Jaelyn on 2016/9/18.
 *
 */
public class ShopDetailResponse {
	private BrandSortModel brand;
	private List<ProductInfoModel> productList;
	//是否收藏
	private int collectionFlag;

	public int getCollectionFlag() {
		return collectionFlag;
	}

	public void setCollectionFlag(int collectionFlag) {
		this.collectionFlag = collectionFlag;
	}

	public BrandSortModel getBrand() {
		return brand;
	}

	public void setBrand(BrandSortModel brand) {
		this.brand = brand;
	}

	public List<ProductInfoModel> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductInfoModel> productList) {
		this.productList = productList;
	}
}
