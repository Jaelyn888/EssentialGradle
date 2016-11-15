package com.yishanxiu.yishang.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.tsz.afinal.annotation.view.ViewInject;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.utils.Utils;

/**
 * Created by Administrator on 2015/9/26.
 */
public class ForgetPasswordSecondActivity extends BaseUIActivity {

	/**
	 * 原密码
	 */
	@ViewInject(id = R.id.old_password_layout)
	private LinearLayout old_password_layout;
	@ViewInject(id = R.id.et_old_password)
	private EditText et_old_password;
	/**
	 * 输入密码
	 */
	@ViewInject(id = R.id.et_input_password)
	private EditText et_input_password;
	/**
	 * 确认密码
	 */
	@ViewInject(id = R.id.et_confirm_password)
	private EditText et_confirm_password;
	/**
	 * 确定按钮
	 *
	 * @param savedInstanceState
	 */
	@ViewInject(id = R.id.et_confirm, click = "et_confirm_click")
	private TextView et_confirm;

	private String userPwd;
	private String input_password; //获取输入密码输入框中密码
	private String input_confirm_password; //获取输入密码输入框中确认密码
	private String userPhone;

	/**
	 * 修改或忘记密码 0修改密码，1：忘记密码
	 */
	private int type = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password_second);
		userPhone = getIntent().getStringExtra(ExtraKeys.Key_Msg1);
		type = getIntent().getIntExtra(ExtraKeys.Key_Msg2, 0);
		if (type == 1) {
			old_password_layout.setVisibility(View.GONE);
			setCenter_title(R.string.forget_pwd);
		} else {
			setCenter_title(R.string.modify_pwd);
		}
	}


	public void et_confirm_click(View view) {
		input_password = et_input_password.getText().toString();
		input_confirm_password = et_confirm_password.getText().toString();
		userPwd = et_old_password.getText().toString();
		if (type == 0) {
			if (!isPasswordAuthentication(userPwd)) {
				return;
			}
		}
		if (!isPasswordAuthentication(input_password)) {
			return;
		}
		if (!input_confirm_password.equals(input_password)) {
			toast.showToast(R.string.password_differ);
			return;
		}
		getData_Forget_Password();

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
	 * "requestType":1,忘记密码
	 * "userName":18516696739,
	 * "newPwd":111111  "userPwd":123456,
	 */
	public void getData_Forget_Password() {
		if (Utils.isFastClick()) {
			return;
		}
		loadingToast.show();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userPhone);
		params.put("newPwd", input_password);
		if (type == 0) {
			params.put("userPwd", userPwd);
		}
		params.put("requestType", String.valueOf(type));
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_updateInfoByUserName);
		queue.setWhat(GetDataConfing.What_updateInfoByUserName);
		queue.setParamsNoJson(params);
		queue.setMediaType(GetDataQueue.MediaType.JSON);
		queue.setCallBack(callBack);
		getDataUtil.getData(queue);

	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (ShowGetDataError.isCodeSuccess(mContext, entity.getInfo())) {
				if (entity.getWhat() == GetDataConfing.What_updateInfoByUserName) {
					if (type == 0) {
						Utils.seUserPwd(mContext, input_password);
					}
					setResult(RESULT_OK);
					finish();
				}
			}

		}
	};
}
