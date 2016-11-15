/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */
package com.yishanxiu.yishang.getdata;

import android.content.Context;
import com.google.gson.Gson;
import com.yishanxiu.yishang.model.BaseModel;
import com.yishanxiu.yishang.model.BaseResponse;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;
import net.tsz.afinal.toast.Toast;
import com.yishanxiu.yishang.R;


/**
 * 展示无获取数据相关的错误信息的提示
 * 
 * @author 亚明
 * 
 */
public class ShowGetDataError {
	private final static String TAG = ShowGetDataError.class.getSimpleName();
	
	/**
	 * 展示无网络访问提示信息
	 */
	public static void showNetError(Context c) {
		Toast.makeText(c, c.getString(R.string.neterror), Toast.LENGTH_SHORT)
				.show();
	}


	/**
	 * 判断code是否成功
	 * @param context
	 * @param jsonStr
	 * @return
	 */
	public static boolean isCodeSuccess(Context context,String jsonStr){
		BaseResponse baseResponse=new Gson().fromJson(jsonStr, BaseResponse.class);
		return isCodeSuccess(context,baseResponse);
	}

	/**
	 * 判断code是否成功
	 * @param context
	 * @param jsonStr
	 * @return
	 */
	public static boolean isCodeSuccess(Context context, BaseResponse response){
		if (response.getCode() == 1) {
			return true;
		} else {
			Toast.makeText(context,response.getMsg(),
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}
