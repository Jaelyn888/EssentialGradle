package com.yishanxiu.yishang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.utils.ModleUtils;
import net.tsz.afinal.annotation.view.ViewInject;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.getdata.*;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/22.
 * 用户反馈
 */
public class UserFeedBackActivity extends BaseUIActivity {
	/**
	 * 意见描述
	 */
	@ViewInject(id = R.id.et_feedback)
	private EditText et_feedback;

	/**
	 * 留下手机号码
	 *
	 * @param savedInstanceState
	 */
	@ViewInject(id = R.id.et_leave_phone)
	private EditText et_leave_phone;

	/**
	 * 确定
	 *
	 * @param savedInstanceState
	 */
	@ViewInject(id = R.id.btn_feed_back_confirm, click = "feed_back_confirm_click")
	private Button btn_feed_back_confirm;
	private String feed_back_msg; //获取输入的反馈信息
	private String leave_phone;   //获取输入的手机号码

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_feed_back);
		setCenter_title(R.string.my_feedback);
	}

	public void feed_back_confirm_click(View view) {
		feed_back_msg = et_feedback.getText().toString();
		leave_phone = et_leave_phone.getText().toString();
		if (TextUtils.isEmpty(feed_back_msg)) {
			toast.showToast(R.string.input_feedback_msg);
			return;
		}
		if (!TextUtils.isEmpty(leave_phone)) {
			if (!StringUtils.isMobileNO(leave_phone)) {
				toast.showToast(R.string.phone_formal_error);
				return;
			}
		}
		getData_FeedBack();

	}

	public void getData_FeedBack() {
		loadingToast.show(R.string.text_uploaddata);
		Map<String, Object> params = new HashMap<>();
		params.put("createUser", Utils.getUserId(this));
		params.put("opinionContent", feed_back_msg);
		if (TextUtils.isEmpty(leave_phone)) {
		} else {
			params.put("phone", leave_phone);
		}
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.GetAction_AddUserOpinion);
		queue.setWhat(GetDataConfing.what_AddUserOpinion);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 数据回调
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {

			if (entity.getWhat() == GetDataConfing.what_AddUserOpinion) {
				TypeToken<BaseResponse> typeToken=new TypeToken<BaseResponse>(){};
				BaseResponse baseResponse=new ModleUtils().mapTo(entity,typeToken);
				if(ShowGetDataError.isCodeSuccess(mContext,baseResponse)) {
					toast.showToast(baseResponse.getMsg());
					finish();
				}
			}
			loadingToast.dismiss();
		}
	};
}
