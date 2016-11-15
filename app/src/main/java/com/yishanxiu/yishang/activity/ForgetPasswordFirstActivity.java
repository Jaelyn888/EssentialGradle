package com.yishanxiu.yishang.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.utils.ModleUtils;
import com.yishanxiu.yishang.utils.Utils;
import net.tsz.afinal.annotation.view.ViewInject;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.getdata.*;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.StringUtils;


/**
 * Created by Administrator on 2015/9/26.
 */
public class ForgetPasswordFirstActivity extends BaseUIActivity {

	/**
	 * 手机号或邮箱
	 */
	@ViewInject(id = R.id.user_account_ed)
	private EditText user_account_ed;
	@ViewInject(id = R.id.user_code_ed)
	private EditText user_code_ed;

	/**
	 * 下一步
	 *
	 * @param savedInstanceState
	 */
	@ViewInject(id = R.id.btn_forgetpassword_first_next, click = "forgetpassword_first_next_click")
	private TextView btn_forgetpassword_first_next;
	/**
	 * 发送验证码
	 */
	@ViewInject(id = R.id.btn_send_code, click = "send_code_click")
	private TextView btn_send_code;
	private String userPhone;
	private String code;  //验证码
	private TimeCount time; //定时器
	/**
	 * 修改或忘记密码 0修改密码，1：忘记密码
	 */
	private int type = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password_first);
		type = getIntent().getIntExtra(ExtraKeys.Key_Msg1, 0);
		if (type == 1) {
			setCenter_title(R.string.forget_pwd);
		} else {
			setCenter_title(R.string.modify_pwd);
			userPhone = Utils.getUserAccount(mContext);
			user_account_ed.setText(userPhone);
			user_account_ed.setEnabled(false);

		}
		time = new TimeCount(60000, 1000);
	}

	/**
	 * 获取验证码（防止网络延时发送多条验证码）
	 */
	public void send_code_click(View view) {
		if (judgeMobileNo()) {
			getData_UserSendCode();
		}
	}

	/**
	 * 调用发送验证码接口    { "userPhone":18516696739, "userTypeId":1, "serviceType":0 }
	 */
	private void getData_UserSendCode() {
		if (Utils.isFastClick()) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userPhone", userPhone);
		params.put("userTypeId", String.valueOf(0));
		params.put("serviceType", String.valueOf(1));
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.GetAction_GetCode);
		queue.setWhat(GetDataConfing.What_GetCode);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 校验验证码   {
	 * "userPhone":18516696739,
	 * "userTypeId":1,
	 * "serviceType":0,
	 * "smsCode":"873378"
	 * }
	 *
	 * @param view
	 */
	public void forgetpassword_first_next_click(View view) {
		userPhone = user_account_ed.getText().toString();
		code = user_code_ed.getText().toString();
		boolean bool = StringUtils.isSmsCode(code);
		if (!bool) {
			toast.showToast(R.string.code_fault);
			return;
		}

		if (StringUtils.isMobileNO(userPhone)) {
			if (Utils.isFastClick()) {
				return;
			}
			loadingToast.show();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userPhone", userPhone);
			params.put("userTypeId", String.valueOf(0));
			params.put("serviceType", String.valueOf(1));
			params.put("smsCode", code);
			GetDataQueue queue = new GetDataQueue();
			queue.setActionName(GetDataConfing.Action_querySmsValidate);
			queue.setWhat(GetDataConfing.What_querySmsValidate);
			queue.setParamsNoJson(params);
			queue.setCallBack(callBack);
			getDataUtil.getData(queue);
		} else {
			toast.showToast(R.string.phone_or_email_style_error);
		}
	}

	/**
	 * 判断手机号码
	 */
	public boolean judgeMobileNo() {
		userPhone = user_account_ed.getText().toString();
		if (TextUtils.isEmpty(userPhone)) {
			//手机号码为空的情况
			toast.showToast(R.string.register_name_null);
			return false;
		} else if (!StringUtils.isMobileNO(userPhone)) {
			toast.showToast(R.string.phone_formal_error);
			return false;
		}
		return true;
	}

	//验证码倒计时
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			//参数依次为总时长,和计时的时间间隔
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			//计时完毕时触发
			btn_send_code.setText(getString(R.string.revalidation));
			code = "";
			btn_send_code.setEnabled(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {//计时过程显示
			btn_send_code.setText(millisUntilFinished / 1000 + getString(R.string.second));
		}
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.getWhat() == GetDataConfing.What_GetCode) {
				BaseResponse baseResponse = new ModleUtils().mapTo(entity);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					toast.showToast(baseResponse.getMsg());
					time.start();
					btn_send_code.setEnabled(false);
				}
			} else if (entity.getWhat() == GetDataConfing.What_querySmsValidate) {
				BaseResponse baseResponse = new ModleUtils().mapTo(entity);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					Intent intent = new Intent(mContext, ForgetPasswordSecondActivity.class);
					intent.putExtra(ExtraKeys.Key_Msg1, userPhone);
					intent.putExtra(ExtraKeys.Key_Msg2, type);
					startActivityForResult(intent, 1);
				}
			}
			loadingToast.dismiss();
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			setResult(RESULT_OK);
			finish();
		}
	}
}