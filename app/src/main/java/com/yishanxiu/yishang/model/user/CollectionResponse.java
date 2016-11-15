package com.yishanxiu.yishang.model.user;

import com.yishanxiu.yishang.model.BaseModel;

/**
 * Created by Jaelyn on 2016/9/18.
 */
public class CollectionResponse extends BaseModel {
	private String contentId;
	private int isCollected;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public int isCollected() {
		return isCollected;
	}

	public void setCollected(int collected) {
		isCollected = collected;
	}
}
