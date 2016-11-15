package com.yishanxiu.yishang.activity;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.utils.PreferenceManager;
import com.hyphenate.exceptions.HyphenateException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.user.SinaUserInfoModel;
import com.yishanxiu.yishang.model.user.UserInfoModel;
import com.yishanxiu.yishang.utils.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by Jaelyn on 2015/8/17 0017.
 * <p/>
 * Update @author FangDongzhang 2016/7/18
 */
public class LoginActivity extends BaseUIActivity {

	/**
	 * 第三方denglu
	 */
	@ViewInject(id = R.id.third_login_layout)
	private LinearLayout third_login_layout;
	@ViewInject(id = R.id.login_qq_iv, click = "qqLogin")
	private TextView login_qq_iv;
	@ViewInject(id = R.id.login_wechat_iv, click = "weixinLogin")
	private TextView login_wechat_iv;
	@ViewInject(id = R.id.login_weibo_iv, click = "sinaLogin")
	private TextView login_weibo_iv;


	@ViewInject(id = R.id.user_account_ed)
	private EditText user_account_ed;
	/**
	 * 密码
	 */
	@ViewInject(id = R.id.user_pwd_ed)
	private EditText user_pwd_ed;

	@ViewInject(id = R.id.forget_pwd_tv, click = "forget_pwd_tv_click")
	private TextView forget_pwd_tv;

	@ViewInject(id = R.id.login_bt, click = "login_bt_click")
	private TextView login_bt;
	private String userPhone;
	private String userPassword;

	private int type = 0;

	// 注册，忘记密码requestCode
	private int requestRegCode = 100;
	private int requestForgetPwd = 101;
	private UMShareAPI mShareAPI = null;

	private int loginStyle = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		setCenter_title(R.string.login);
		setBtn_menuText(R.string.register, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, RegisterActivity.class);
				startActivityForResult(intent, requestRegCode);
			}
		});
		Intent intent = getIntent();
		if (intent != null) {
			type = intent.getIntExtra(ExtraKeys.Key_Msg1, 0);
		}
		loginStyle = Constant.LOGINSTYLE_PHONE;
		user_account_ed.setText(Utils.getUserAccount(mContext));
		mShareAPI = UMShareAPI.get(mContext);
	}

	/**
	 * 登录按钮
	 */
	public void login_bt_click(View view) {
		if (userLoginJudge()) {
			getData_UserLogin();
		}
	}

	public void forget_pwd_tv_click(View view) {
		Intent intent = new Intent(mContext, ForgetPasswordFirstActivity.class);
		intent.putExtra(ExtraKeys.Key_Msg1, 1);
		jumpTo(intent);
	}

	/**
	 * 获取登录的接口
	 */
	private void getData_UserLogin() {
		if (Utils.isFastClick()) {
			return;
		}
		loadingToast.show();
		loginStyle = Constant.LOGINSTYLE_PHONE;
		Map<String, Object> params = new HashMap<>();
		params.put("userName", userPhone);
		params.put("userPwd", userPassword);
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.GetAction_CheckLogin);
		queue.setWhat(GetDataConfing.what_CheckLogin);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}


	// 获取APPID及appkey，并且将APPID及appkey绑定在友盟主站后台，
	// 同时注意在应用审核通过前必须添加测试账号，否则会出现110406报错。
	public void qqLogin(View view) {
		// toast.showToast("等待正式签名");
		mShareAPI.doOauthVerify(mContext, SHARE_MEDIA.QQ, thirdLoginListener);
		// thirdLoginToServer(1,
		// "5602395594","Jaelyn","http://tva4.sinaimg.cn/crop.0.0.100.100.50/0067951gjw8esfy7565ngj302s02sdfn.jpg");
		// //test
	}

	public void sinaLogin(View view) {
		mShareAPI.doOauthVerify(mContext, SHARE_MEDIA.SINA, thirdLoginListener);
		// thirdLoginToServer(3, "5548517447"); //test
	}

	public void weixinLogin(View view) {
		mShareAPI.doOauthVerify(mContext, SHARE_MEDIA.WEIXIN, thirdLoginListener);
		// {sex=1, nickname=Jaelyn, unionid=ohzmBw3OE1sqqr9c2kyiq04CaAsg,
		// province=上海, openid=oCDJUwd93nw7KfNL2E8e8XmR1VCU, language=zh_CN,
		// headimgurl=http://wx.qlogo.cn/mmopen/W0wicJgsFSLE4Pz1zwiajeUmaYOTdJ40PUooLrTERgKG3PcfXaJnfzvMzwC5RPE3bXQc8lfjk9YA2aFYU1P3Fzkur4VE9y5zNz/0,
		// country=中国, city=}
		// mLoginController.doOauthVerify(this, SHARE_MEDIA.WEIXIN,
		// thirdLoginListener );
	}

	/**
	 * 授权监听
	 */
	private UMAuthListener thirdLoginListener = new UMAuthListener() {
		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			toast.showToast(R.string.authorize_third_login_error);
			Log.d(TAG, t.toString());
		}

		@Override
		public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
			LogUtil.e(data.toString());
			mShareAPI.getPlatformInfo(mContext, platform, thirdPlatformInfoListener);
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			toast.showToast(R.string.cancel_third_login);
		}
	};
	// 获取用户信息
	private UMAuthListener thirdPlatformInfoListener = new UMAuthListener() {
		@Override
		public void onError(SHARE_MEDIA platform, int action, Throwable t) {
			toast.showToast(R.string.authorize_third_login_error);
			Log.d(TAG, t.toString());
		}

		@Override
		public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
			LogUtil.e(data.toString());
			getThirdPlatforamInfo(platform, data);
		}

		@Override
		public void onCancel(SHARE_MEDIA platform, int action) {
			toast.showToast(R.string.cancel_third_login);
		}
	};

	/**
	 * 第三方登陆 { "userName":"777777777", "userIcon":"weqwe",
	 * "userNickname":"Jaelyn", "loginType":1}
	 *
	 * @param ordinal        平台
	 * @param account        openId o
	 * @param nickName       手机类型
	 * @param thirdUserPhoto
	 */
	private void thirdLoginToServer(int ordinal, String account, String nickName, String thirdUserPhoto) {
		loadingToast.show();
		loginStyle = Constant.LOGINSTYLE_THIRDPLATFORM;
		// toast.showToast(ordinal+" "+account+" "+nickName+" "+thirdUserPhoto);
		Map<String, Object> params = new HashMap<>();
		params.put("platform", ordinal);
		params.put("tag", account);
		params.put("userNickname", nickName);
		params.put("userIcon", thirdUserPhoto);
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_ThirdPlatformLogin);
		queue.setParamsNoJson(params);
		queue.setMediaType(GetDataQueue.MediaType.JSON);
		queue.setWhat(GetDataConfing.What_ThirdPlatformLogin);
		queue.setCallBack(callBack);
		getDataUtil.getData(queue);
	}

	// 获取相关授权信息
	private void getThirdPlatforamInfo(final SHARE_MEDIA platform, Map<String, String> info) {
		try {
			if (platform == SHARE_MEDIA.QQ) {
				String thirdNickName = info.get("screen_name");
				String thirdUserPhoto = info.get("profile_image_url");
				thirdLoginToServer(1, info.get("openid"), thirdNickName, thirdUserPhoto);
			} else if (platform == SHARE_MEDIA.WEIXIN) {
				String thirdNickName = info.get("nickname");
				String thirdUserPhoto = info.get("headimgurl");
				thirdLoginToServer(2, info.get("openid"), thirdNickName, thirdUserPhoto);
			} else if (platform == SHARE_MEDIA.SINA) {
				SinaUserInfoModel sinaUserInfoModel = new Gson().fromJson(info.get("result"), SinaUserInfoModel.class);

				String thirdNickName = sinaUserInfoModel.getScreen_name();
				String thirdUserPhoto = sinaUserInfoModel.getAvatar_hd();
				thirdLoginToServer(3, sinaUserInfoModel.getIdstr(), thirdNickName, thirdUserPhoto);
			}
		} catch (Exception e) {
			toast.showToast("发生错误：" + e.toString());
		}
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.what == GetDataConfing.what_CheckLogin
					|| entity.what == GetDataConfing.What_ThirdPlatformLogin) {
				TypeToken<BaseResponse<UserInfoModel>> typeToken = new TypeToken<BaseResponse<UserInfoModel>>() {};
				BaseResponse<UserInfoModel> baseResponse = new ModleUtils().mapTo(entity, typeToken);

				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					toast.showToast(R.string.login_success);
					//UserProfilePreferenceManager.getInstance().setUserLoginStyle(loginStyle);
					//UserProfilePreferenceManager.getInstance().saveUserInfos(baseResponse.getInfo());

					Utils.setUserLoginStyle(mContext, loginStyle);
					Utils.saveUserInfos(mContext, baseResponse.getInfo());
					// 获取服务器端存储的数据
					bindUser(mContext);
					initHX();
					if (type == 1) {
						jumpTo(MainActivity2.class, true);
					} else {
						finish();
					}
				}
			}
		}
	};


	//init环信
	private void initHX() {
		if (Utils.isHasLogin(mContext)) {
			String chatId = Utils.getChatId(mContext);
			String chatPwd = Utils.getChatPwd(mContext);
			if (TextUtils.isEmpty(chatId) || TextUtils.isEmpty(chatPwd)) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String userId = Utils.getUserId(mContext);
						try {
							EMClient.getInstance().createAccount(userId, "Essential" + userId);
							modifyInfo("8", userId, "Essential" + userId);
						} catch (HyphenateException e) {
							e.printStackTrace();
						}
						loginHX(userId, "Essential" + userId);
						Utils.setChatIdAndPwd(mContext, userId, "Essential" + userId);

					}
				}).start();
			} else {
				loginHX(chatId, chatPwd);
			}
		}
	}

	//登录环信
	private void loginHX(String chatUserName, String chatPwd) {
		EMClient.getInstance().login(chatUserName, chatPwd, new EMCallBack() {// 回调
			@Override
			public void onSuccess() {
				EMClient.getInstance().groupManager().loadAllGroups();
				EMClient.getInstance().chatManager().loadAllConversations();
			}

			@Override
			public void onProgress(int progress, String status) {

			}

			@Override
			public void onError(int code, String message) {
				LogUtil.d(TAG, "登录聊天服务器失败！");
			}
		});
		PreferenceManager.getInstance().setCurrentUserName(chatUserName);
		PreferenceManager.getInstance().setCurrentUserNick(Utils.getUserNickName(mContext));
		PreferenceManager.getInstance().setCurrentUserAvatar(Utils.getUserPic(mContext));
	}

	private void modifyInfo(String requestType, String chatId, String chatPwd) {
		Map<String, Object> params = new HashMap<>();
		params.put("requestType", requestType);
		params.put("userId", Utils.getUserId(mContext));
		params.put("chatId", chatId);
		params.put("chatPwd", chatPwd);

		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(updateInfoCallBack);
		queue.setActionName(GetDataConfing.UserInfo_ModUserBaseInfo);
		queue.setWhat(GetDataConfing.What_UserInfo_ModUserBaseInfo);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 获取服务器数据的返回信息
	 */
	private IGetServicesDataCallBack updateInfoCallBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			LogUtil.d(TAG, entity.getParams());
		}
	};

	private void bindUser(Context context) {
		Set<String> tagSet = new LinkedHashSet<>();
		tagSet.add(Utils.getUserLevel(context) + "");
		JPushInterface.setAlias(context, Utils.getUserId(context).toString(), new TagAliasCallback() {
			@Override
			public void gotResult(int i, String s, Set<String> set) {
				LogUtil.d(TAG, "" + s);
			}
		});
		JPushInterface.setTags(context, tagSet, new TagAliasCallback() {
			@Override
			public void gotResult(int i, String s, Set<String> set) {
				LogUtil.d(TAG, "" + s);
			}
		});
	}

	public boolean judgeMobileNo() {
		boolean isjudge = true;
		userPhone = user_account_ed.getText().toString();
		if (userPhone == null || "".equals(userPhone)) {
			toast.showToast(R.string.register_name_null);
			isjudge = false;
		} else if (userPhone.length() < 11) {
			toast.showToast(R.string.phone_formal_error);
			isjudge = false;
		}
		return isjudge;
	}

	/**
	 * 密码判断
	 */
	public boolean isPasswordAuthentication(String password) {
		if (!StringUtils.passwordAuthenticationLength(password)) {
			toast.showToast(R.string.register_passwordRule);
			return false;
		} else if (!StringUtils.passwordAuthentication(password)) {
			toast.showToast(R.string.password_format_error);
			return false;
		}
		return true;
	}

	/**
	 * 登录判断
	 */
	public boolean userLoginJudge() {
		userPassword = user_pwd_ed.getText().toString();
		if (TextUtils.isEmpty(userPassword)) {
			toast.showToast(R.string.register_password_null);
			return false;
		} else {
			if (isPasswordAuthentication(userPassword)) {
				return judgeMobileNo();
			} else {
				return false;
			}
		}
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 *
	 * @see {@link android.app.Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		mShareAPI.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestRegCode == requestCode) {
				if (type == 1) {
					jumpTo(MainActivity2.class, true);
				} else {
					finish();
				}
			} else {

			}
		}
	}
}