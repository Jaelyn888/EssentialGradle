package com.yishanxiu.yishang.utils;

import android.graphics.BitmapFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;
import com.yishanxiu.yishang.getdata.GetDataConfing;

import android.app.Activity;
import android.text.TextUtils;
import net.tsz.afinal.toast.Toast;
import com.yishanxiu.yishang.R;

/**
 * Created by Jaelyn on 2016/7/7.
 * 登录
 */
public class ShareSocialUtils {


	// 添加微信、微信朋友圈平台
	// 注意：在微信授权的时候，必须传递appSecret
	// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
	public static final String WEIXIN_APP_ID = "wx106efe31792496c6";
	public static final String WEIXIN_APP_SECRET = "87685f2ca9d54b2dceab8aad3450e989";
	// 公共 qq的id和key
	//public static final String QQ_APP_ID = "100424468";
	//public static final String QQ_APP_Secret = "c7394704798a158208a74ab60104f0ba";
	public static final String QQ_APP_ID = "1105512065";
	public static final String QQ_APP_Secret = "rvMMz8o1dgDLsHzH";

	//新浪微博
	public static final String SINA_APP_ID = "104974306";
	public static final String SINA_APP_Secret = "45d62bd12538cc30a6a990417b0758d0";

	public static void init() {
		PlatformConfig.setWeixin(WEIXIN_APP_ID, WEIXIN_APP_SECRET);
		PlatformConfig.setSinaWeibo(SINA_APP_ID, SINA_APP_Secret);
		PlatformConfig.setQQZone(QQ_APP_ID, QQ_APP_Secret);
		Config.IsToastTip = false;
		Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
	}

	public static void showShareCompont(final Activity activity, String imgUrl, String targetUrl, String shareTitle,
	                                    String content) {
		showShareCompont(activity, imgUrl, null, targetUrl, shareTitle, content);
	}

	public static void showShareCompont(final Activity activity, String imgUrl, String bigImgUrl, String targetUrl, String shareTitle, String content) {
		targetUrl = GetDataConfing.Url_Base + targetUrl;
		if (TextUtils.isEmpty(shareTitle)) {
			shareTitle = activity.getString(R.string.app_name);
		}
		if (TextUtils.isEmpty(content)) {
			content = shareTitle;
		}
		UMImage sinaImage;

		if (!TextUtils.isEmpty(bigImgUrl)) {
			sinaImage = new UMImage(activity, bigImgUrl);
		} else if (!TextUtils.isEmpty(imgUrl)) {
			sinaImage = new UMImage(activity, imgUrl);
		} else {
			sinaImage = new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.drawable.app_icon));
		}
		UMImage localImage;
		if (TextUtils.isEmpty(imgUrl)) {
			localImage = new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.drawable.app_icon));
		} else {
			imgUrl = StringUtils.getSSImage(imgUrl);
			localImage = new UMImage(activity, imgUrl);
		}
		// 设置其他默认分享内容
		// 微信分享必须设置targetURL，需要为http链接格式
		ShareContent defaultContent = new ShareContent();
		defaultContent.mText = content;
		defaultContent.mTitle = shareTitle;
		defaultContent.mMedia = localImage;
		defaultContent.mTargetUrl = targetUrl;

		ShareContent sinaContent = new ShareContent();
		sinaContent.mText = content;
		sinaContent.mTitle = shareTitle;
		sinaContent.mMedia = sinaImage;
		sinaContent.mTargetUrl = targetUrl;
		new ShareAction(activity).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ)
				.setContentList(defaultContent, defaultContent, sinaContent, defaultContent)
				.setListenerList(new UMShareListener() {
					@Override
					public void onResult(SHARE_MEDIA platform) {
						if (platform.name().equals("WEIXIN_FAVORITE")) {
							Toast.makeText(activity, "收藏成功", android.widget.Toast.LENGTH_SHORT).show();
						} else {

							String userName = "访客";
							UMPlatformData.GENDER gender = UMPlatformData.GENDER.MALE;
							if (Utils.isHasLogin(activity)) {
								userName = Utils.getUserAccount(activity);
								if (Utils.getUserSex(activity) == 2) {
									gender = UMPlatformData.GENDER.MALE;
								} else {
									gender = UMPlatformData.GENDER.FEMALE;
								}
							}
							UMPlatformData platformTmp;
							if (platform == SHARE_MEDIA.SINA) {
								platformTmp = new UMPlatformData(UMPlatformData.UMedia.SINA_WEIBO, userName);
							}
							if (platform == SHARE_MEDIA.QQ) {
								platformTmp = new UMPlatformData(UMPlatformData.UMedia.TENCENT_QQ, userName);
							} else if (platform == SHARE_MEDIA.QZONE) {
								platformTmp = new UMPlatformData(UMPlatformData.UMedia.TENCENT_QZONE, userName);
							} else if (platform == SHARE_MEDIA.WEIXIN) {
								platformTmp = new UMPlatformData(UMPlatformData.UMedia.WEIXIN_FRIENDS, userName);
							} else if (platform == SHARE_MEDIA.WEIXIN_CIRCLE) {
								platformTmp = new UMPlatformData(UMPlatformData.UMedia.WEIXIN_CIRCLE, userName);
							} else {
								platformTmp = new UMPlatformData(UMPlatformData.UMedia.WEIXIN_CIRCLE, userName);
							}

							platformTmp.setGender(gender); //optional
							MobclickAgent.onSocialEvent(activity, platformTmp);
							Toast.makeText(activity, "分享成功", android.widget.Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onError(SHARE_MEDIA platform, Throwable t) {
						Toast.makeText(activity, "分享失败", android.widget.Toast.LENGTH_SHORT).show();
						if (t != null) {
							LogUtil.d("throw", "throw:" + t.getMessage());
						}
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
						Toast.makeText(activity, "取消分享", android.widget.Toast.LENGTH_SHORT).show();
					}
				})
				.open();
	}

}
