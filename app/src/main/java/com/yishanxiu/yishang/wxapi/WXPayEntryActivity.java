package com.yishanxiu.yishang.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.LogUtil;
import com.yishanxiu.yishang.utils.ShareSocialUtils;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, ShareSocialUtils.WEIXIN_APP_ID);

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp resp) {
		LogUtil.d(TAG,"onResp:"+resp.getType()+"  onResp:"+resp.errStr+"  onResp:"+resp.errCode);
		//toast.showToast("onResp:"+resp.getType()+"  onResp:"+resp.errStr+"  onResp:"+resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg,
//					resp.errStr + ";code=" + String.valueOf(resp.errCode)));
//			builder.show();
			int result = resp.errCode;
			// Bundle data2 = new Bundle();
			// data2.putString("result", app.getmQueryProgramId());
			Intent mIntent2 = new Intent(Constant.ACT_WEIXINPAYCODE);
			mIntent2.putExtra("result", result);
			// 发送广播
			sendBroadcast(mIntent2);
            finish();
		}else if (resp.getType()==ConstantsAPI.COMMAND_LAUNCH_BY_WX){
//			int result = resp.errCode;
//			// Bundle data2 = new Bundle();
//			// data2.putString("result", app.getmQueryProgramId());
//			Intent mIntent2 = new Intent(Constant.ACT_WEIXINPAYCODE);
//			mIntent2.putExtra("result", result);
//			// 发送广播
//			sendBroadcast(mIntent2);
//			finish();
		}
	}

}
