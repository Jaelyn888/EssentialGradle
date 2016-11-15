package com.yishanxiu.yishang.model.shopmall;

import java.util.List;

/**
 * Created by Jaelyn on 2016/9/18.
 * 品牌列表页 返回数据model
 */
public class AllBrandListResponse {
	List<BrandSortModel> totalBrandList;
	List<BrandModel> hotBrandList;

	public List<BrandSortModel> getTotalBrandList() {
		return totalBrandList;
	}

	public void setTotalBrandList(List<BrandSortModel> totalBrandList) {
		this.totalBrandList = totalBrandList;
	}

	public List<BrandModel> getHotBrandList() {
		return hotBrandList;
	}

	public void setHotBrandList(List<BrandModel> hotBrandList) {
		this.hotBrandList = hotBrandList;
	}
}
