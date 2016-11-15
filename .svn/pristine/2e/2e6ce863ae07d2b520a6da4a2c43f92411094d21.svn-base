package com.yishanxiu.yishang.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.model.BaseResponse;


/**
 * Created by Jaelyn on 2016/9/18.
 */
public class ModleUtils {


	public void ModleUtils(){
	}
	public  static <T> BaseResponse<T>  mapTo(GetDataQueue entity, Class<T> targetClass) {
		TypeToken<BaseResponse<T>> typeToken=new TypeToken<BaseResponse<T>>(){};
		return new Gson().fromJson(entity.getResponseData(),typeToken.getType());
	}

	public  static  BaseResponse  mapTo(GetDataQueue entity) {
		TypeToken<BaseResponse> typeToken=new TypeToken<BaseResponse>(){};
		return new Gson().fromJson(entity.getResponseData(),typeToken.getType());
	}

	public  BaseResponse mapTo(GetDataQueue entity,TypeToken typeToken) {
		return new Gson().fromJson(entity.getResponseData(), typeToken.getType());
	}
}
