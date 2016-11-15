package com.yishanxiu.yishang.model.shopmall;

import com.yishanxiu.yishang.model.BaseModel;

/**
 * Created by Jaelyn on 2016/9/18.
 * 品牌基本信息
 */
public class BrandModel extends BaseModel {

	private String brandId;

	//品牌名称
	private String brandName;

	//品牌logo
	private String logoPath;

	//品牌短描述简介  j
	private String pageDesc;

	/**
	 * 主图
	 */
	private String picPath;
	/**
	 * 描述
	 */
	private String detailedDesc;

	/**
	 * 网址链接
	 */
	private String remark1;

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getDetailedDesc() {
		return detailedDesc;
	}

	public void setDetailedDesc(String detailedDesc) {
		this.detailedDesc = detailedDesc;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getPageDesc() {
		return pageDesc;
	}

	public void setPageDesc(String pageDesc) {
		this.pageDesc = pageDesc;
	}

}
