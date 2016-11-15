package com.yishanxiu.yishang.model.shopmall;

import com.yishanxiu.yishang.model.BaseModel;

/**
 * Created by Jaelyn on 2016/9/19.
 * 商品小分类
 */
public class ProductTypeModel extends BaseModel {
	private String productypeName;
	private  String productTypeId;

	public String getProductypeName() {
		return productypeName;
	}

	public void setProductypeName(String productypeName) {
		this.productypeName = productypeName;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
}
