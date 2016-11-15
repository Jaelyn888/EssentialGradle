package com.yishanxiu.yishang.model.shopmall;

import com.yishanxiu.yishang.model.BaseModel;

import java.util.List;

/**
 * Created by Jaelyn on 2016/9/20.
 * 商品规格信息
 */
public class ProductSpecificationModel extends BaseModel {
	/**
	 * 规格名称
	 */
	private String specificationName;
	private String specificationId;
	/**
	 * 规格值
	 */
	private List<SpecificationValueModel>   specificationValueVos;

	public String getSpecificationName() {
		return specificationName;
	}

	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}

	public String getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}

	public List<SpecificationValueModel> getSpecificationValueVos() {
		return specificationValueVos;
	}

	public void setSpecificationValueVos(List<SpecificationValueModel> specificationValueVos) {
		this.specificationValueVos = specificationValueVos;
	}
}
