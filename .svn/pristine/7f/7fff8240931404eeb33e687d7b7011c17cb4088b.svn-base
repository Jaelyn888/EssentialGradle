package com.yishanxiu.yishang.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;


import com.tencent.mm.sdk.modelpay.PayReq;

@SuppressWarnings("deprecation")
public class WeixiPay {

	/**
	 * 使用微信PartnerId
	 */
	public static String WeiXinPartnerId = "";
	/**
	 * 使用微信PartnerKey
	 */
	public static String WeiXinPartnerKey = "";
	/**
	 * 微信签名
	 * @param req
	 * @param prepayId 与支付订单号
	 * @return
	 */
	public static PayReq genPayReq(PayReq req,String prepayId) {
		StringBuffer sb=new StringBuffer();
		req.appId = ShareSocialUtils.WEIXIN_APP_ID;
		req.partnerId = WeiXinPartnerId;
		req.prepayId = prepayId;
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());


		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		

		req.sign = genAppSign(signParams);

		sb.append("sign\n"+req.sign+"\n\n");

//		show.setText(sb.toString());

		Log.e("orion", signParams.toString());
		return req;

	}
	
	private static String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	private static long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	private static String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(WeiXinPartnerKey);

//        this.sb.append("sign str\n"+sb.toString()+"\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",appSign);
		return appSign;
	}
	


}
