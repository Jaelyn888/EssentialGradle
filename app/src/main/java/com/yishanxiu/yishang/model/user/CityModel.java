package com.yishanxiu.yishang.model.user;

import java.util.List;

public class CityModel {
	private String cityId;

	private String name;

	private String parentId;

	private List<DistrictModel> districtVoList;

	public List<DistrictModel> getDistrictVoList() {
		return districtVoList;
	}

	public void setDistrictVoList(List<DistrictModel> districtVoList) {
		this.districtVoList = districtVoList;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}