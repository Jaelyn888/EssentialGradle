package com.yishanxiu.yishang.model.shopmall;

/**
 * Created by Jaelyn on 2016/9/18.
 */
public class BrandCollectionModel extends BrandModel {
	private String createTime;
	private int isCollected;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(int isCollected) {
		this.isCollected = isCollected;
	}
}