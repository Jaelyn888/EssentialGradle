package com.yishanxiu.yishang.getdata;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.request.PostRequest;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.utils.LogUtil;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Jaelyn on 2015/12/3 0003.
 * <p/>
 * 网络请求队列
 */
public class GetServicesDataUtil {
	private String TAG = "GetServicesDataUtil";
	private static GetServicesDataUtil getServicesDataUtil;

	public static GetServicesDataUtil init() {
		if (getServicesDataUtil == null) {
			getServicesDataUtil = new GetServicesDataUtil();
		}
		return getServicesDataUtil;
	}


	private GetServicesDataUtil() {
	}

	/**
	 * 请求网络数据
	 * @param dataRequest
	 */
	public void getData(GetDataQueue dataRequest) {
		Object jsonMap = dataRequest.getParams();
		if (jsonMap == null) {
			jsonMap = new HashMap();
		}
		String url = dataRequest.getIp() + dataRequest.getActionName();
		String tag = "" + dataRequest.getWhat();
		LogUtil.i(TAG, jsonMap.toString());
		GetDataQueue.MediaType mediaType = dataRequest.getMediaType();
		try {
			if (mediaType == GetDataQueue.MediaType.URLENCODEFORM) {
				OkHttpUtils.post(url)
						.params((Map<String, String>) jsonMap)//
						.tag(tag)
						.execute(new MyStringCallback(dataRequest));
			} else if (mediaType == GetDataQueue.MediaType.JSON) {
				String jsonStr = new Gson().toJson(jsonMap);
				OkHttpUtils.post(url).upJson(jsonStr).tag(tag)
						.execute(new MyStringCallback(dataRequest));
			} else if (mediaType == GetDataQueue.MediaType.MUTILPART) {
				PostRequest postRequest = OkHttpUtils.post(url);
				postRequest.params((Map<String, String>) jsonMap);
				Map<String, Object> fileMap = dataRequest.getFileMap();
				postRequest.addFileParams(fileMap.get("key").toString(), (List<File>) fileMap.get("fileList"));
				postRequest.tag(tag).execute(new MyStringCallback(dataRequest));
			}

		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.d("req data err", e.getMessage());
			dataRequest.setResponseData("{\"msg\":\"请求参数异常\",\"code\":\"0\",\"info\":null}");
			dataRequest.getCallBack().onLoaded(dataRequest);
		}
	}

	/**
	 * 取消网络请求 测试  不用
	 */
	public void cancleRequest(Object ...tag){
		for (Object object:tag) {
			OkHttpUtils.getInstance().cancelTag(object);
		}
	}

	public class MyStringCallback extends StringCallback {
		private GetDataQueue dataRequest;

		public MyStringCallback() {
			super();
		}

		@Override
		public void onSuccess(String s, Call call, Response response) {
			LogUtil.d("resp data:", s);
			try {
				BaseResponse baseResponse=new Gson().fromJson(s, BaseResponse.class);
				dataRequest.setResponseData(s);
			} catch (Exception e) {
				e.printStackTrace();
				dataRequest.setResponseData("{\"msg\":\"传递信息过大\",\"code\":\"0\",\"info\":null}");
			}

			dataRequest.getCallBack().onLoaded(dataRequest);
		}

		@Override
		public void onError(Call call, Response response, Exception e) {
			super.onError(call, response, e);
			if (null == response) {
				dataRequest.setResponseData("{\"msg\":\"请求超时，请检查网络\",\"code\":\"0\",\"info\":null}");
			} else {
				int code = response.code();
				if (code == 315) {
					dataRequest.setResponseData("{\"msg\":\"传递信息过大\",\"code\":\"0\",\"info\":null}");
				} else if (code == 415) {
					dataRequest.setResponseData("{\"msg\":\"请求数据类型不支持\",\"code\":\"0\",\"info\":null}");
				} else if (code == 404) {
					dataRequest.setResponseData("{\"msg\":\"找不到请求路径\",\"code\":\"0\",\"info\":null}");
				} else if (code == 401) {
					dataRequest.setResponseData("{\"msg\":\"请重新登录\",\"code\":\"0\",\"info\":null}");
				} else if (code >= 500) {
					dataRequest.setResponseData("{\"msg\":\"服务器异常\",\"code\":\"0\",\"info\":null}");
				} else {
					dataRequest.setResponseData("{\"msg\":\"请求超时，请检查网络\",\"code\":\"0\",\"info\":null}");
				}
			}
			dataRequest.getCallBack().onLoaded(dataRequest);
		}

		@Override
		public void onAfter(@Nullable String s, @Nullable Exception e) {
			super.onAfter(s, e);
		}

		public MyStringCallback(GetDataQueue dataRequest) {
			super();
			this.dataRequest = dataRequest;
		}


	}
}
