package com.yishanxiu.yishang.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.yishanxiu.yishang.model.user.UserInfoModel;

/**
 * Created by Jaelyn on 2016/9/22.
 * 当前用户信息管理
 */
public class UserProfilePreferenceManager {
	private static final String SP_NAME = "essential";
	private static SharedPreferences mSharedPreferences;
	private static UserProfilePreferenceManager userProfilePreferenceManager;
	private static SharedPreferences.Editor editor;

	private  final String SP_USERNAME = "userName";
	private  final String SP_USER_PWD = "userPwd";
	private  final String SP_USER_NICKNAME = "userNickName";
	/**
	 * 用户级别
	 */
	private  final String SP_USER_LEVEL = "userLevelId";
	/**
	 * 用户id
	 */
	private  final String SP_KEY_USERID = "userId";
	/**
	 * 用户chatId
	 */
	private  final String SP_KEY_CHATID = "chatId";
	/**
	 * 环信密码
	 */
	private  final String SP_KEY_CHATPWD = "chatPwd";
	/**
	 * 用户描述
	 */
	private  final String SP_USER_DISCRI = "userDiscri";
	/**
	 * 用户手机号
	 */
	private  final String SP_UserPhone = "userPhone";
	/**
	 * 用户所在地
	 */
	private  final String SP_ProvinceName = "provinceName";
	private  final String SP_ProvinceId = "provinceId";
	/**
	 * 用户类型
	 */
	private  final String SP_USERTYPE = "userType";
	/***
	 * 用户级别
	 */
	public  final String SP_UserLevelId = "userLevelId";
	/**
	 * 用户邮箱
	 */
	private  final String SP_UserEmail = "userEmail";
	/**
	 * 用户真实姓名
	 */
	public  final String SP_USERREALNAME = "userRealName";
	/**
	 * 用户性别（0为男，1为女，2为保密）
	 */
	private  final String SP_USERSEX = "userSex";
	/**
	 * 用户图片
	 */
	private  final String Sp_USER_ICON = "userIcon";
	/**
	 * 用户生日
	 */
	private  final String Sp_USER_BIRTHDAY = "birthday";
	/**
	 * 用户注册时间
	 */
	private  final String Sp_UserRegisterTime = "createTime";
	/**
	 * 访客的Account
	 */
	private  final String SP_Visiter_Account = "visiteraccount";
	/**
	 * 用来判断用户是正式账户0  第三方来源1
	 */
	private  final String SP_UserLoginStyle = "isvisiter";

	private UserProfilePreferenceManager(Context cxt) {
		mSharedPreferences = cxt.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
	}

	public static synchronized void init(Context cxt){
		if(userProfilePreferenceManager == null){
			userProfilePreferenceManager = new UserProfilePreferenceManager(cxt);
		}
	}

	/**
	 * get instance of PreferenceManager
	 *
	 * @return
	 */
	public synchronized static UserProfilePreferenceManager getInstance() {
		if (userProfilePreferenceManager == null) {
			throw new RuntimeException("please init first!");
		}

		return userProfilePreferenceManager;
	}

	public  void saveUserInfos(UserInfoModel userInfoModel) {
		editor.putString(SP_KEY_USERID, userInfoModel.getUserId());
		editor.putString(SP_KEY_CHATID, userInfoModel.getChatId());
		editor.putString(SP_UserPhone, userInfoModel.getUserPhone());
		editor.putString(SP_USER_NICKNAME, userInfoModel.getUserNickname());
		editor.putString(Sp_USER_BIRTHDAY, userInfoModel.getBirthday());
		editor.putString(SP_ProvinceName, userInfoModel.getProvinceName());
		editor.putLong(SP_ProvinceId, userInfoModel.getProvinceId());
		editor.putString(Sp_UserRegisterTime, userInfoModel.getCreateTime());
		editor.putString(SP_UserEmail, userInfoModel.getUserEmail());
		editor.putInt(SP_USERSEX, userInfoModel.getUserSex());
		editor.putString(Sp_USER_ICON, userInfoModel.getUserIcon());
		editor.putString(SP_USERNAME, userInfoModel.getUserName());
		editor.putString(SP_USER_DISCRI,userInfoModel.getUserDiscri());
		editor.putInt(SP_USERTYPE, userInfoModel.getLoginType());
		editor.putInt(SP_UserLevelId, userInfoModel.getUserLevelId());
		editor.commit();
	}

	/**
	 * 是否已登录
	 * @return
	 */
	public  boolean isHasLogin() {
		String userId = getUserId();
		return !TextUtils.isEmpty(userId);
	}

	/**
	 * 获取用户账户
	 */
	public  String getUserAccount() {
		return mSharedPreferences.getString(SP_USERNAME, "");
	}

	public  void setUserAccount(String userAccout) {
		editor.putString(SP_USERNAME, userAccout);
		editor.commit();
	}

	/**
	 * 获取用户注册时间
	 */
	public  String getRegisterTime() {
		return mSharedPreferences.getString(Sp_UserRegisterTime, "");
	}


	/**
	 * 获取用户生日
	 */
	public  String getUserBrithday() {
		return mSharedPreferences.getString(Sp_USER_BIRTHDAY, "");
	}

	public  void setUserBrithday(String userBrithday) {
		editor.putString(Sp_USER_BIRTHDAY, userBrithday);
		editor.commit();
	}

	/**
	 * 获取密码
	 * @return
	 */
	public  String getUserPwd() {
		return mSharedPreferences.getString(SP_USER_PWD, "");
	}

	public  void seUserPwd(String userPwd) {
		editor.putString(SP_USER_PWD, userPwd);
		editor.commit();
	}

	/**
	 * 获取用户昵称
	 *
	 * @return
	 */
	public  String getUserNickName() {

		return mSharedPreferences.getString(SP_USER_NICKNAME, "昵称");

	}

	public  void setUserNickName( String userNickName) {
		editor.putString(SP_USER_NICKNAME, userNickName);
		editor.commit();
	}

	/**
	 * 获取用户描述
	 *
	 * @return
	 */
	public  String getUserDiscri() {
		return mSharedPreferences.getString(SP_USER_DISCRI, "");
	}

	public  void setUserDiscri( String userDiscri) {
		editor.putString(SP_USER_DISCRI, userDiscri);
		editor.commit();
	}

	/**
	 * 获取用户手机号码
	 */
	public  String getUserPhone() {
		return mSharedPreferences.getString(SP_UserPhone, "");
	}

	/**
	 * 获取用户邮箱
	 */
	public  String getUserEmail() {
		return mSharedPreferences.getString(SP_UserEmail, "");
	}

	/**
	 * 获取用户性别
	 */
	public  int getUserSex() {
		return mSharedPreferences.getInt(SP_USERSEX, 2);
	}

	public  void setUserSex( int userSex) {
		editor.putInt(SP_USERSEX, userSex);
		editor.commit();
	}

	/**
	 * 保存用户图片路径
	 */
	public  void saveUserPic( String picUrl) {
		editor.putString(Sp_USER_ICON, picUrl);
		editor.commit();
	}

	/**
	 * 保存用户邮箱
	 */
	public  void saveUserEmail( String email) {
		editor.putString(SP_UserEmail, email);
		editor.commit();
	}

	/**
	 * 保存用户昵称
	 */
	public  void saveUserNickName( String nickName) {
		editor.putString(SP_USER_NICKNAME, nickName);
		editor.commit();
	}

	/**
	 * 保存用户性别
	 */
	public  void saveUserSex( int sex) {
		editor.putInt(SP_USERSEX, sex);
		editor.commit();
	}

	/**
	 * 保存是访客登录还是注册登录
	 */
	public  void setUserLoginStyle( int isThirdPlatform) {
		editor.putInt(SP_UserLoginStyle, isThirdPlatform);
		editor.commit();
	}

	/**
	 * 获取是访客登录还是注册登录
	 */
	public  int getUserLoginStyle() {
		return mSharedPreferences.getInt(SP_UserLoginStyle, 0);
	}

	/**
	 * 保存用户真实姓名
	 */
	public  void saveUserRealName( String realName) {
		editor.putString(SP_USERREALNAME, realName);
		editor.commit();
	}


	/**
	 * 获取用户id
	 *
	 * @return
	 */
	public  String getUserId() {
		return mSharedPreferences.getString(SP_KEY_USERID, "");
	}

	/**
	 * 获取用户chatId
	 *
	 *
	 * @return
	 */
	public  String getChatId() {
		return mSharedPreferences.getString(SP_KEY_CHATID,  "");
	}

	/**
	 * 获取用户环信密码	 *
	 *
	 *
	 * @return
	 */
	public  String getChatPwd() {
		return mSharedPreferences.getString(SP_KEY_CHATPWD, "");
	}

	/**
	 * 获取用户chatId
	 *
	 *
	 * @return
	 */
	public  void setChatIdAndPwd( String chatId, String chatPwd) {
		editor.putString(SP_KEY_CHATID, chatId);
		editor.putString(SP_KEY_CHATPWD, chatPwd);
		editor.commit();
	}

	/**
	 * 获取用户
	 */
	public  String getUserRealName() {
		return mSharedPreferences.getString(SP_USERREALNAME, "");
	}

	/**
	 * 获取用户所在地
	 */
	public  String getProvinceName() {
		return mSharedPreferences.getString(SP_ProvinceName, "");
	}

	public  void setProvinceName( String ProvinceName) {
		editor.putString(SP_ProvinceName, ProvinceName);
		editor.commit();
	}

	/**
	 * 获取用户图片
	 */
	public  String getUserPic() {
		String userName = mSharedPreferences.getString(Sp_USER_ICON, "");
		return userName;
	}



	/**
	 * 退出登录 要保存
	 */
	public  void clearUserInfoData() {

		String userPhone = mSharedPreferences.getString(SP_USERNAME, "");
		editor.clear();
		if (StringUtils.isMobileNO(userPhone)) {
			editor.putString(SP_USERNAME, userPhone);
		}
		editor.commit();
	}

	/**
	 * 保存手机号
	 * @param phone
	 */
	public  void saveUserPhone(String phone) {
		editor.putString(SP_UserPhone, phone);
		editor.commit();
	}



	public  void setUserLevel( int userLevel) {
		editor.putInt(SP_USER_LEVEL, userLevel);
		editor.commit();
	}

	public  int getUserLevel() {
		int userLevel = mSharedPreferences.getInt(SP_USER_LEVEL, 0);
		return userLevel;
	}

}
