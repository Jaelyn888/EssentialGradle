package com.yishanxiu.yishang.model;

/**
 * Created by Jaelyn on 2016/9/18.
 */
public class BaseResponse<T> extends BaseModel {

	private int code;
	private String msg;
	private T info;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}
}
