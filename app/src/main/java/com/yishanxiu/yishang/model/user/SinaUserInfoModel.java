package com.yishanxiu.yishang.model.user;

import com.yishanxiu.yishang.model.BaseModel;

/**
 * Created by Jaelyn on 2016/9/22.
 * 第三方登录  新浪信息
 */
public class SinaUserInfoModel extends BaseModel {
	private String idstr;
	private String avatar_hd;
	private String screen_name;

	public String getIdstr() {
		return idstr;
	}

	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	public String getAvatar_hd() {
		return avatar_hd;
	}

	public void setAvatar_hd(String avatar_hd) {
		this.avatar_hd = avatar_hd;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
}
