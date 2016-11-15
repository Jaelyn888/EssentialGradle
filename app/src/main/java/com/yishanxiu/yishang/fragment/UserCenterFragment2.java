package com.yishanxiu.yishang.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.utils.PreferenceManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageCropActivity;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.CollectionBrandActivity;
import com.yishanxiu.yishang.activity.FansListActivity;
import com.yishanxiu.yishang.activity.ForgetPasswordFirstActivity;
import com.yishanxiu.yishang.activity.LoginActivity;
import com.yishanxiu.yishang.activity.MyCollectActivity2;
import com.yishanxiu.yishang.activity.MyCouponActivity;
import com.yishanxiu.yishang.activity.MyMsgActivity;
import com.yishanxiu.yishang.activity.MyOrderActivity2;
import com.yishanxiu.yishang.activity.PersonalInfoActivity;
import com.yishanxiu.yishang.activity.RecipientActivity;
import com.yishanxiu.yishang.activity.RegisterActivity;
import com.yishanxiu.yishang.activity.SetActivity;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.DisplayUtil;
import com.yishanxiu.yishang.utils.ModleUtils;
import com.yishanxiu.yishang.utils.Utils;
import com.yishanxiu.yishang.view.CircleImageView;
import com.yishanxiu.yishang.view.PullToZoomView;
import com.yishanxiu.yishang.view.WihteRoundCornersDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 我的
 * <p/>
 * update by FangDongzhang on 2016/4/14
 */

public class UserCenterFragment2 extends LazyFragment implements EasyPermissions.PermissionCallbacks {
	@ViewInject(R.id.pullToZoomViewLayout)
	private PullToZoomView pullToZoomViewLayout;
	@ViewInject(R.id.scrollview_topic_layout)
	private RelativeLayout relativeLayout;
	/**
	 * 我的消息
	 */
	@ViewInject(R.id.logo_msg_iv)
	private ImageView logo_msg_iv;
	/**
	 * 客服
	 */
	@ViewInject(R.id.custom_service_iv)
	private ImageView custom_service_iv;

	/**
	 * 上传头像
	 */
	@ViewInject(R.id.iv_taking_pictures)
	private CircleImageView iv_taking_pictures;

	/**
	 * 用户名
	 */
	@ViewInject(R.id.usernick_tv)
	private TextView usernick_tv;
	// @ViewInject(R.id.userNickLayout)
	// private RelativeLayout userNickLayout;

	/**
	 * 个人描述
	 */
	@ViewInject(R.id.user_dis_tv)
	private TextView user_dis_tv;
	// @ViewInject(R.id.user_dis_layout)
	// private RelativeLayout user_dis_layout;
	/**
	 * 日志
	 */
	@ViewInject(R.id.blog_layout)
	private LinearLayout blog_layout;
	@ViewInject(R.id.blog_num_tv)
	private TextView blog_num_tv;

	// 修改密码item
	@ViewInject(R.id.modify_pwd_layout)
	private LinearLayout modify_pwd_layout;

	/**
	 * 修改密码
	 */
	@ViewInject(R.id.modify_pwd)
	private TextView modify_pwd;
	/**
	 * 修改收货地址
	 */
	@ViewInject(R.id.modify_addr)
	private TextView modify_addr;
	/**
	 * 关注
	 */
	@ViewInject(R.id.att_llt)
	private TextView focus_layout;
	/**
	 * 个人资料
	 */
	@ViewInject(R.id.myInfo_layout)
	private TextView myInfo_layout;
	/**
	 * 粉丝
	 */
	@ViewInject(R.id.fans_layout)
	private LinearLayout fans_layout;
	@ViewInject(R.id.fans_num_tv)
	private TextView fans_num_tv;

	/**
	 * 优惠券
	 */
	@ViewInject(R.id.coupon)
	private LinearLayout myCoupon;

	/**
	 * 订单
	 */
	@ViewInject(R.id.myOrdersLayout)
	private LinearLayout myOrdersLayout;
	@ViewInject(R.id.myOrdersNum_tv)
	private TextView myOrdersNum_tv;
	/**
	 * 我的收藏
	 */
	@ViewInject(R.id.myCollectionLayout)
	private TextView myCollectionLayout;

	/**
	 * 我的账户
	 */
	@ViewInject(R.id.myAccountLayout)
	private TextView myAccountLayout;

	/**
	 * 我的预约
	 */
	@ViewInject(R.id.myAppointmentLayout)
	private LinearLayout myAppointmentLayout;
	@ViewInject(R.id.myAppointmentNum_tv)
	private TextView myAppointmentNum_tv;
	/**
	 * 系统设置
	 */
	@ViewInject(R.id.settingLayout)
	private TextView settingLayout;
	/**
	 *
	 */
	@ViewInject(R.id.llt_list)
	private LinearLayout llt_list;
	@ViewInject(R.id.llt_center)
	private LinearLayout llt_center;
	/**
	 * 未登录
	 */
	@ViewInject(R.id.nologin_layout)
	private LinearLayout nologin_layout;

	@ViewInject(R.id.login_bt)
	private TextView login;
	@ViewInject(R.id.register_bt)
	private TextView register_bt;

	/**
	 * 标题
	 */
	@ViewInject(R.id.text_center_name)
	private TextView text_center_name;
	private ImagePicker imagePicker;

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.user_center_layout);
		initView(getContentView());

		LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(DisplayUtil.screenWidth,
				(int) (9.5F * (DisplayUtil.screenWidth / 16.0F)));
		pullToZoomViewLayout.setHeaderLayoutParams(localObject);
		imagePicker = ImagePicker.getInstance();
	}

	@Override
	protected void onFragmentStartLazy() {
		super.onFragmentStartLazy();
		relativeLayout.getBackground().setAlpha(0);
		// zoom_scroll_view.set(new OnScrollChangedListener() {
		//
		// @SuppressLint("UseValueOf")
		// @Override
		// public void onScrollChanged(ScrollView who, int l, int t, int oldl,
		// int oldt) {
		//
		// int height = 480;
		// if (t < height) {
		// text_center_name.setVisibility(View.GONE);
		// int alpha = (int) (new Float(t) / new Float(height) * 255);
		// relativeLayout.getBackground().setAlpha(alpha);
		// } else {
		// text_center_name.setVisibility(View.VISIBLE);
		// text_center_name.setText(Utils.getUserNickName(activity));
		// relativeLayout.getBackground().setAlpha(255);
		// }
		// }
		// });
		refreshUserPhotoAndStatue();
		setVisibilityUI();
	}

	/**
	 * 找Id
	 */
	private void initView(View view) {
		ViewUtils.inject(this, view);
	}

	/**
	 * 我的消息
	 *
	 * @param view
	 */
	@OnClick(R.id.logo_msg_iv)
	public void logo_msg_iv_click(View view) {
		if (Utils.isFastClick()) {
			return;
		}
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(MyMsgActivity.class);
		} else {
			activity.gotoLogin();
		}
	}
	/**
	 * 联系客服
	 *
	 * @param view
	 */
	private WihteRoundCornersDialog builder;

	@OnClick(R.id.custom_service_iv)
	public void custom_service_iv_click(View view) {
		popupWindows = new PopupWindows(activity, view, 1);
	}

	private WihteRoundCornersDialog.DialogCallBack callBackdialog1 = new WihteRoundCornersDialog.DialogCallBack() {

		@Override
		public void bttonclick(int index) {
			builder.dismiss();
			switch (index) {
				case 1:

					break;
				case 2:
					activity.startTelPhone(Constant.Tel_Number);
					break;
				default:
					break;
			}
		}

		@Override
		public void bttonclick(int index, int what) {

		}

	};
	/**
	 * 头像
	 *
	 * @param view
	 */
	private PopupWindows popupWindows;

	@OnClick(R.id.iv_taking_pictures)
	public void iv_taking_pictures_click(View view) {
		if (Utils.isHasLogin(activity)) {
			popupWindows = new PopupWindows(activity, view, 0);
		} else {
			activity.gotoLogin();
		}
	}

	/**
	 * 日志
	 *
	 * @param view
	 */
	@OnClick(R.id.blog_layout)
	public void blog_layout_click(View view) {
		if (Utils.isHasLogin(activity)) {
			// activity.jumpTo(MyDailyActivity.class);
		} else {
			activity.gotoLogin();
		}
	}

	// /**
	// * 话题
	// *
	// * @param view
	// */
	// @OnClick(R.id.topic_layout)
	// public void topic_layout_click(View view) {
	// Intent personData = new Intent(activity, UserDataActivity.class);
	// startActivityForResult(personData, personcenter);
	// }

	/**
	 * 关注
	 *
	 * @param view
	 */
	@OnClick(R.id.att_llt)
	public void focus_layout_click(View view) {
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(CollectionBrandActivity.class);
		} else {
			activity.gotoLogin();
		}
	}

	/**
	 * 粉丝
	 *
	 * @param view
	 */
	@OnClick(R.id.fans_layout)
	public void fans_layout_click(View view) {
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(FansListActivity.class);
		} else {
			activity.gotoLogin();
		}
	}

	/**
	 * 个人资料
	 *
	 * @param view
	 */
	@OnClick(R.id.myInfo_layout)
	public void myInfo_layout_click(View view) {
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(PersonalInfoActivity.class);
		} else {
			activity.gotoLogin();
		}

	}

	/**
	 * 个人简介
	 *
	 * @param view
	 */
	@OnClick(R.id.user_dis_tv)
	public void user_dis_tv_click(View view) {
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(PersonalInfoActivity.class);
		} else {
			activity.gotoLogin();
		}

	}

	/**
	 * 修改密码
	 *
	 * @param view
	 */
	@OnClick(R.id.modify_pwd)
	public void modify_pwd_click(View view) {
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(ForgetPasswordFirstActivity.class);
		} else {
			activity.gotoLogin();
		}

	}

	/**
	 * 我的优惠券
	 *
	 * @param view
	 */
	@OnClick(R.id.coupon)
	public void couponClick(View view) {
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(MyCouponActivity.class);
		} else {
			activity.gotoLogin();
		}

	}

	/**
	 * 我的订单
	 *
	 * @param view
	 */
	@OnClick(R.id.myOrdersLayout)
	public void myOrdersLayout_click(View view) {
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(MyOrderActivity2.class);
		} else {
			activity.gotoLogin();
		}

	}

	/**
	 * 我的搜藏
	 *
	 * @param view
	 */
	@OnClick(R.id.myCollectionLayout)
	public void myCollectionLayout_click(View view) {
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(MyCollectActivity2.class);
		} else {
			activity.gotoLogin();
		}

	}

	/**
	 * 我的预约
	 *
	 * @param view
	 */
	@OnClick(R.id.myAppointmentLayout)
	public void myAppointmentLayout_click(View view) {
		if (Utils.isHasLogin(activity)) {
			// activity.jumpTo(MyAppointmentActivity.class);
		} else {
			activity.gotoLogin();
		}

	}

	/**
	 * 修改收货地址
	 *
	 * @param view
	 */
	@OnClick(R.id.modify_addr)
	public void modify_addr_click(View view) {
		if (Utils.isHasLogin(activity)) {
			activity.jumpTo(RecipientActivity.class);
		} else {
			activity.gotoLogin();
		}

	}

	/**
	 * 我的账户
	 *
	 * @param view
	 */
	@OnClick(R.id.myAccountLayout)
	public void myAccountLayout_click(View view) {
		if (Utils.isHasLogin(activity)) {
		} else {
			activity.gotoLogin();
		}

	}

	/**
	 * 系统设置
	 *
	 * @param view
	 */
	@OnClick(R.id.settingLayout)
	public void settingLayout_click(View view) {
		activity.jumpTo(SetActivity.class);
	}

	/**
	 * 刷新头像和信息
	 */
	private void refreshUserPhotoAndStatue() {
		if (Utils.isHasLogin(activity)) {
			BitmapHelper.loadImage(activity, Utils.getUserPic(activity), iv_taking_pictures,
					BitmapHelper.LoadImgOption.Photo);
		} else {
			iv_taking_pictures.setImageResource(R.drawable.user_center_1);
		}
		usernick_tv.setText(Utils.getUserNickName(activity));
		if (Utils.getUserDiscri(activity).equals("")) {
			user_dis_tv.setText(R.string.edit_discri);
		} else {
			user_dis_tv.setText(Utils.getUserDiscri(activity));
		}
	}

	private TextView telephone;
	private TextView custom_service;
	private TextView select_photo_camera_bt;
	private TextView select_photo_local_bt;
	private TextView cancleTv;

	private final int select_local_requestcode = 1;
	private final int select_camera_requestcode = 3;
	private final int camera_permission_requestcode = 100;
	private String[] camera_permissions = {Manifest.permission.CAMERA};
	/**
	 * 存放照片的文件
	 */
	private String headerPicPath;

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent, int type) {
			View view = null;
			if (type == 0) {
				view = View.inflate(mContext, R.layout.item_popupwindows, null);
			} else if (type == 1) {
				view = View.inflate(mContext, R.layout.custom_service, null);

			}
			view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(view, Gravity.BOTTOM, 0, 0);
			update();
			if (type == 0) {
				select_photo_camera_bt = (TextView) view.findViewById(R.id.select_photo_camera_bt);
				select_photo_local_bt = (TextView) view.findViewById(R.id.select_photo_local_bt);
				cancleTv = (TextView) view.findViewById(R.id.item_popupwindows_cancel);
				RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rlt_pop);
				layout.setOnClickListener(cameraClick);
				select_photo_local_bt.setOnClickListener(cameraClick);
				select_photo_camera_bt.setOnClickListener(cameraClick);
				cancleTv.setOnClickListener(cameraClick);
			} else if (type == 1) {
				telephone = (TextView) view.findViewById(R.id.telephone);
				telephone.setOnClickListener(phoneClick);
				custom_service = (TextView) view.findViewById(R.id.custom_service);
				custom_service.setOnClickListener(phoneClick);
			}
			RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rlt_pop);
			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		}
	}

	private OnClickListener cameraClick = new OnClickListener() {
		@Override
		public void onClick(View view) {
			int id = view.getId();
			if (id == R.id.select_photo_local_bt) {
				startSelectLocalPhoto();
			} else if (id == R.id.select_photo_camera_bt) {
				checkCameraPermission();
			}
			popupWindows.dismiss();
		}
	};


	private OnClickListener phoneClick = new OnClickListener() {
		@Override
		public void onClick(View view) {
			int id = view.getId();
			if (id == R.id.custom_service) {
				activity.contactCustomService(Constant.IM, Constant.CHATTYPE_SINGLE);

			} else if (id == R.id.telephone) {
				builder = new WihteRoundCornersDialog(getContext(), R.style.ExitDialogStyle, 2, callBackdialog1);
				builder.setTitletext(R.string.promotion_tips);
				builder.setMessagetext(Constant.Tel_Number);
				builder.setButtonText(R.string.cancel, R.string.call_t);
				builder.show();
			}
			popupWindows.dismiss();
		}
	};
	private void startCamera() {
		initImagePicker();
		ImagePicker.getInstance().takePicture(this, select_camera_requestcode);
	}

	private void initImagePicker() {
		imagePicker.setStyle(CropImageView.Style.RECTANGLE);
		imagePicker.setCrop(true);
		imagePicker.setMultiMode(false);
		Integer radius = getResources().getDimensionPixelOffset(R.dimen.circle_photo_radius);
		imagePicker.setFocusWidth(radius * 2);
		imagePicker.setFocusHeight(radius * 2);
		imagePicker.setOutPutX(500);
		imagePicker.setOutPutY(500);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}


	private void startSelectLocalPhoto() {
		initImagePicker();
		Intent intent = new Intent(activity, ImageGridActivity.class);
		startActivityForResult(intent, select_local_requestcode);
	}

	@AfterPermissionGranted(camera_permission_requestcode)
	private void checkCameraPermission() {
		if (EasyPermissions.hasPermissions(getContext(), camera_permissions)) {
			startCamera();
		} else {
			// Request one permission
			EasyPermissions.requestPermissions(this, getString(R.string.camera_permission_tips), camera_permission_requestcode, camera_permissions);
		}
	}

	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
		if (requestCode == camera_permission_requestcode) {
			if (perms.size() == camera_permissions.length) {
				startCamera();
			}
		}
	}

	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
		EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
				getString(R.string.camera_permission_tips),
				R.string.setting, R.string.cancel, perms);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String fileType = "jpg";
		if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
			if (data != null) {
				ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
				String thumnailPath = images.get(0).path;
				upLoadUserPic(thumnailPath, fileType);
			}
		} else if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
				case select_camera_requestcode:
					ImagePicker.galleryAddPic(activity, imagePicker.getTakeImageFile());
					ImageItem imageItem = new ImageItem();
					imageItem.path = imagePicker.getTakeImageFile().getAbsolutePath();
					imagePicker.clearSelectedImages();
					imagePicker.addSelectedImageItem(0, imageItem, true);
					Intent intent = new Intent(activity, ImageCropActivity.class);
					startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面

					//upLoadUserPic(thumnailPath, fileType);
					break;
				case EasyPermissions.SETTINGS_REQ_CODE:
					boolean hasSomePermissions = EasyPermissions.hasPermissions(activity, camera_permissions);
					if (hasSomePermissions) {
						startCamera();
					}
					break;

				default:
					break;
			}
		}
	}

	/**
	 * 上传用户头像 (调用接口)
	 */
	private void upLoadUserPic(String filePath, String fileType) {
		loadingToast.show();
		Map<String, Object> fileMap = new HashMap<>();
		fileMap.put("key", "file");
		ArrayList<File> fileArrayList = new ArrayList<>();
		File file = new File(filePath);
		fileArrayList.add(file);
		fileMap.put("fileList", fileArrayList);
		Map<String, Object> params = new HashMap<>();
		params.put("imgType", String.valueOf(2));
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_uploadPictureOrFile);
		queue.setParamsNoJson(params);
		queue.setFileMap(fileMap);
		queue.setMediaType(GetDataQueue.MediaType.MUTILPART);
		queue.setWhat(GetDataConfing.what_uploadPictureOrFile);
		queue.setCallBack(callBack);
		getDataUtil.getData(queue);
	}

	IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.getWhat() == GetDataConfing.what_uploadPictureOrFile) {
				TypeToken<BaseResponse<List<String>>> typeToken = new TypeToken<BaseResponse<List<String>>>() {
				};
				BaseResponse<List<String>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					headerPicPath = baseResponse.getInfo().get(0);
					modifyInfo("6", "userIcon", headerPicPath);
				}
			} else if (entity.what == GetDataConfing.What_UserInfo_ModUserBaseInfo) {
				BaseResponse baseResponse = new ModleUtils().mapTo(entity);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					toast.showToast(baseResponse.getMsg());
					BitmapHelper.loadImage(activity, headerPicPath, iv_taking_pictures, BitmapHelper.LoadImgOption.Photo);
					Utils.saveUserPic(activity, headerPicPath);
					PreferenceManager.getInstance().setCurrentUserAvatar(headerPicPath);
				}
			}
		}
	};

	private void modifyInfo(String requestType, String key, String value) {
		loadingToast.show();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("requestType", requestType);
		params.put("userId", Utils.getUserId(activity));
		params.put(key, value);

		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.UserInfo_ModUserBaseInfo);
		queue.setWhat(GetDataConfing.What_UserInfo_ModUserBaseInfo);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}


	/**
	 * 点击banck见
	 *
	 * @return
	 */
	@Override
	public boolean backKeyClick() {
		if (popupWindows != null && popupWindows.isShowing()) {
			popupWindows.dismiss();
			return true;
		} else {
			return false;
		}
	}

	@OnClick(R.id.login_bt)
	public void loginClick(View v) {
		Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
	}

	@OnClick(R.id.register_bt)
	public void registerClick(View v) {
		Intent intent = new Intent(activity, RegisterActivity.class);
		activity.startActivity(intent);
	}

	/**
	 * 判断登陆状态
	 */
	private void setVisibilityUI() {
		if (Utils.isHasLogin(activity)) {
			nologin_layout.setVisibility(View.GONE);
			usernick_tv.setVisibility(View.VISIBLE);
			user_dis_tv.setVisibility(View.VISIBLE);
			if (Utils.getUserLoginStyle(activity) == Constant.LOGINSTYLE_PHONE) {
				modify_pwd_layout.setVisibility(View.VISIBLE);
			} else {
				modify_pwd_layout.setVisibility(View.GONE);
			}

			int unReadMsgCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();
			if (unReadMsgCount > 0) {
				logo_msg_iv.setSelected(true);
			} else {
				logo_msg_iv.setSelected(false);
			}

		} else {
			nologin_layout.setVisibility(View.VISIBLE);
			usernick_tv.setVisibility(View.INVISIBLE);
			user_dis_tv.setVisibility(View.INVISIBLE);
			// llt_center.setVisibility(View.INVISIBLE);
		}
	}
}
