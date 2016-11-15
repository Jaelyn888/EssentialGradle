package com.yishanxiu.yishang.activity;

import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.google.gson.reflect.TypeToken;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.model.ApkInfo;
import com.hyphenate.chatuidemo.DemoHelper;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.receiver.CheckVersionReceiver;
import com.yishanxiu.yishang.utils.*;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.sdcard.SdcardHelper;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Jaelyn on 16/2/25. 设置
 */
public class SetActivity extends BaseUIActivity implements EasyPermissions.PermissionCallbacks {

	/**
	 * 账号管理
	 */
	@ViewInject(id = R.id.accountManagerLayout, click = "accountManagerLayoutClick")
	private TextView accountManagerLayout;
	/**
	 * 安全管理
	 */
	@ViewInject(id = R.id.securityLayout, click = "securityLayoutClick")
	private TextView securityLayout;
	/**
	 * 关于我们
	 */
	@ViewInject(id = R.id.tr_my_about_us, click = "tr_my_about_usClick")
	private TextView tr_my_about_us;
	/**
	 * 帮助中心
	 */
	@ViewInject(id = R.id.tr_my_help_center, click = "tr_my_help_centerClick")
	private TextView tr_my_help_center;
	/**
	 * 意见反馈
	 */
	@ViewInject(id = R.id.tr_my_feedback, click = "tr_my_feedbackClick")
	private TextView tr_my_feedback;
	/**
	 * 版本检测
	 */
	@ViewInject(id = R.id.tr_my_version_checking, click = "tr_my_version_checkingClick")
	private LinearLayout tr_my_version_checking;

	@ViewInject(id = R.id.my_the_current_version)
	private TextView my_the_current_version;
	/**
	 * 联系我们
	 */
	@ViewInject(id = R.id.bt_my_log_out, click = "bt_my_log_out")
	private Button bt_my_log_out;
	/**
	 * 退出登录
	 */
	@ViewInject(id = R.id.tr_my_contact_us, click = "tr_my_contact_usClick")
	private TextView tr_my_contact_us;


	private final int storage_permission_requestcode = 101;
	private String[] storage_permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_layout);
		setCenter_title(R.string.systemSet);
		my_the_current_version.setText("V " + Utils.getVersionName(mContext));
		if (Utils.isHasLogin(mContext)){
			bt_my_log_out.setVisibility(View.VISIBLE);
		} else {
			bt_my_log_out.setVisibility(View.GONE);
		}
//		Intent e = new Intent(this, DownloadService.class);
//		this.startService(e);
//		this.bindService(e, this, BIND_AUTO_CREATE);
	}

	public void accountManagerLayoutClick(View view) {

	}

	public void securityLayoutClick(View view) {

	}

	public void tr_my_about_usClick(View view) {
		Intent intent = new Intent(mContext, UrlWebviewActivity.class);
		intent.putExtra(Constant.INTENT_URL, GetDataConfing.ABOUTUS);
		intent.putExtra(Constant.Center_Title,R.string.my_about_us);
		jumpTo(intent, false);
	}

	public void tr_my_help_centerClick(View view) {
		Intent intent = new Intent(mContext, UrlWebviewActivity.class);
		intent.putExtra(Constant.INTENT_URL, GetDataConfing.help_url);
		intent.putExtra(Constant.Center_Title,R.string.my_help_center);
		jumpTo(intent, false);
	}

	public void tr_my_feedbackClick(View view) {
		jumpTo(UserFeedBackActivity.class);
	}

	public void tr_my_version_checkingClick(View view) {
		checkAppVersionPermission();
	}

	public void tr_my_contact_usClick(View view) {
	}


	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.what == GetDataConfing.What_selectAppVersionInfo) {
				TypeToken<BaseResponse<ApkInfo>> typeToken=new TypeToken<BaseResponse<ApkInfo>>(){};
				BaseResponse<ApkInfo> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					ApkInfo apkInfo = baseResponse.getInfo();
					String versionCode = apkInfo.getVersionNumber();
					String filePath = apkInfo.getVersionFilePath();
					String versionContent = apkInfo.getVersionContent();
					if (TextUtils.isEmpty(versionCode) || TextUtils.isEmpty(filePath)
							|| versionCode.equalsIgnoreCase(Utils.getVersionName(mContext))) {
						toast.showToast(R.string.app_version_newest);
					} else {
						showNewVersion(filePath, versionContent);
					}
				}
			}
		}
	};

	/**
	 * 提示用户有新版本
	 */
	private String apkDownLoadPath;
	private AlertDialog alertDialog;


	/**
	 * 检测版本
	 */
	@AfterPermissionGranted(storage_permission_requestcode)
	private void checkAppVersionPermission() {
		if (SdcardHelper.ExistSDCard()) {
			if (EasyPermissions.hasPermissions(mContext, storage_permissions)) {
				checkVersion();
			} else {
				EasyPermissions.requestPermissions(this, getString(R.string.extrastorage_permissions_tips), storage_permission_requestcode, storage_permissions);
			}
		} else {
			toast.showToast(R.string.nosdcard);
		}
	}

	private void checkVersion() {
		loadingToast.show();
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_selectAppVersionInfo);
		queue.setParams(new HashMap<String, Object>());
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_selectAppVersionInfo);
		getDataUtil.getData(queue);
	}

	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
		if (requestCode == storage_permission_requestcode) {
			if (perms.size() == storage_permissions.length) {
				checkVersion();
			}
		}
	}

	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
		EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
				getString(R.string.extrastorage_permissions_tips),
				R.string.setting, R.string.cancel, perms);
	}

	/**
	 * 提示用户有新版本
	 */
	private void showNewVersion(String filePath, String appUpdateDiscri) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.app_version_select);
		builder.setMessage(appUpdateDiscri);
		builder.setNegativeButton(R.string.app_version_iknow, dialogClickListener);
		builder.setPositiveButton(R.string.app_version_update, dialogClickListener);

		alertDialog = builder.show();
		apkDownLoadPath = filePath;

	}


	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			alertDialog.dismiss();
			if (which == AlertDialog.BUTTON_POSITIVE) {
				Intent newIntent = new Intent(CheckVersionReceiver.Essential_Download_APK_Action);
				ApkInfo apkInfo = new ApkInfo();
				apkInfo.setShowNotification(true);
				apkInfo.setAutoInstall(true);
				apkInfo.setVersionFilePath(apkDownLoadPath);
				newIntent.putExtra(ExtraKeys.Key_Msg1, apkInfo);
				sendBroadcast(newIntent);
			}
		}
	};


	/**
	 * 退出登陆
	 *
	 * @param v
	 */
	public void bt_my_log_out(View v) {
		showExitDialog();
	}


	private void showExitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle(R.string.logout);
		builder.setMessage(R.string.change_user);
		builder.setNegativeButton(R.string.cancel, exitDialogClickListener);
		builder.setPositiveButton(R.string.ensure, exitDialogClickListener);
		alertDialog = builder.show();

	}

	private DialogInterface.OnClickListener exitDialogClickListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			alertDialog.dismiss();
			if (which == AlertDialog.BUTTON_POSITIVE) {
				DemoHelper.getInstance().logout(false, null);
				Utils.clearUserInfoData(mContext);
				finish();
			}
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {

				case EasyPermissions.SETTINGS_REQ_CODE:
					boolean hasSomePermissions = EasyPermissions.hasPermissions(mContext, storage_permissions);
					if (hasSomePermissions) {
						checkVersion();
					}
					break;

				default:
					break;
			}
		}
	}
}