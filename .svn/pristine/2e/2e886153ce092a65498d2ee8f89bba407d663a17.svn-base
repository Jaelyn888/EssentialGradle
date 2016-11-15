/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */
package com.yishanxiu.yishang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.*;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.PaymentListAdapter;
import com.yishanxiu.yishang.getdata.*;
import com.yishanxiu.yishang.model.PayResult;
import com.yishanxiu.yishang.utils.*;

import net.tsz.afinal.annotation.view.ViewInject;
import com.yishanxiu.yishang.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;
import net.tsz.afinal.network.NetWorkHelper;

/**
 * @author tanghuan
 * @author FangDongzhang
 * @ClassName: ShoppingZhifuPeisongWayActivity
 * @Description: TODO(支付与配送方式类)
 * @date 2014年12月3日 下午7:53:40
 * <p>
 * Updata 2016/7/1
 */
public class PayMethodActivity extends BaseUIActivity {
	/**
	 * 支付方式
	 */
	@ViewInject(id = R.id.payTypeLv)
	private ListView gvns_payment;
	//支付方式 配送方式的选择的数据源
	private List<JsonMap<String, Object>> data_peisong = new ArrayList<JsonMap<String, Object>>();
	/**
	 * 应付金额
	 */
	@ViewInject(id = R.id.totalMoneyTv)
	private TextView totalMoneyTv;
	/**
	 * 提交订单
	 */
	@ViewInject(id = R.id.payOrderTv, click = "payOrderTvClick")
	private TextView payOrderTv;
	private PaymentListAdapter adapter;


	//选中的支付方式
	private int payId = Constant.ALIPAY_PAYID;
	private JsonMap<String, Object> orderdata;
	private String orderId, orderNum, productName;
	private double totalPrice;
	private int type; //0:单订单 ，1 多订单
	//微信数据
	private PayReq req;
	private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
	private String prepayId;

	public static final int PLUGIN_VALID = 0;
	public static final int PLUGIN_NOT_INSTALLED = -1;
	public static final int PLUGIN_NEED_UPGRADE = 2;

	//mMode参数解释："00" - 启动银联正式环境 "01" - 连接银联测试环境
	private String mMode = "00";
	private int fromType = 0; //跳转来源，0：订单，1：提交订单

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_zhifu_peisong_way);
		setCenter_title(R.string.shopping_zhifu_peisong_way_text_payway1);
		gvns_payment.setOnItemClickListener(onItemClickListener);
		setBtn_backListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (fromType == 1) {
					sendPayResult(1);
					if (type == 1) {
						jumpTo(MyOrderActivity2.class, true);
					} else {
						Intent intentSeeLogistics = new Intent(mContext, MyOrderDetailActivity.class);
						intentSeeLogistics.putExtra(Constant.ORDER_ID, orderId);
						jumpTo(intentSeeLogistics, true);
					}
				} else {
					finish();
				}
			}
		});

		msgApi.registerApp(ShareSocialUtils.WEIXIN_APP_ID);
		req = new PayReq();
		try {
			fromType = getIntent().getIntExtra(ExtraKeys.Key_Msg1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String orderMap = getIntent().getStringExtra(ExtraKeys.Key_Msg2);
		orderdata = JsonParseHelper.getJsonMap(orderMap);
		type = orderdata.getInt("type");
		orderId = orderdata.getStringNoNull("orderId");
		orderNum = orderdata.getStringNoNull("externalOrderNumber");
		totalPrice = orderdata.getDouble("actualPayPrice", 0d);
		payId = orderdata.getInt("payId", Constant.ALIPAY_PAYID);
		String totle_money_formart = String.format(getString(R.string.totle_money), StringUtils.getFormatMoneyWithSignMust(totalPrice));
		totalMoneyTv.setText(totle_money_formart);
		initData();
		// test
		//productName = "奕赏订单";
//		orderNum=helper.getOutTradeNo();
//		orderId="184";

	}

	/**
	 * 选择支付方式
	 */
	private void gopaytype() {
		if (TextUtils.isEmpty(orderNum) || totalPrice < 0.01) {
			toast.showToast(R.string.pay_info_error);
			payfailure();
			return;
		}
		switch (payId) {
			case Constant.ALIPAY_PAYID:
				getAlipyConfig();
				break;
			case Constant.WEICHAT_PAYID:
				IntentFilter myIntentFilter = new IntentFilter();
				myIntentFilter.addAction(Constant.ACT_WEIXINPAYCODE);
				registerReceiver(mpayBroadcastReceiver, myIntentFilter);
				getData_GetWeChatPrepayId();
				break;
			case Constant.UNION_PAYID:
				//步骤1：从网络开始,获取交易流水号即TN
				getData_GetYinLianOrderTradeNum();
				break;
			default:
				break;
		}
	}

	private void getAlipyConfig() {
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_GetConfigInfo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("orderNum", orderNum);
		queue.setParams(map);
		queue.setCallBack(callBack2);
		queue.setWhat(GetDataConfing.What_GetConfigInfo);
		getDataUtil.getData(queue);
	}


	/**
	 * 获取微信prepayId
	 */
	private void getData_GetWeChatPrepayId() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNum", orderNum);
		params.put("orderId", orderId);
		params.put("amount", (int) (totalPrice * 100) + "");
		params.put("createIp", NetWorkHelper.getIP(mContext));
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_GetWeiXinPrePayNum);
		queue.setParamsNoJson(params);
		queue.setWhat(what_GetWeiXinPrePayNum);
		queue.setCallBack(callBack2);
		getDataUtil.getData(queue);

	}

	/**
	 * 获取银联流水号
	 */
	private void getData_GetYinLianOrderTradeNum() {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNum", orderNum);
		params.put("orderId", orderId);
		params.put("amount", (int) (totalPrice * 100) + "");
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_GetYinLianOrderPrePayNum);
		queue.setParamsNoJson(params);
		queue.setWhat(what_GetYinLianOrderPrePayNum);
		queue.setCallBack(callBack2);
		getDataUtil.getData(queue);

	}

	private final int what_GetYinLianOrderPrePayNum = 100;
	private final int what_GetWeiXinPrePayNum = 101;
	/**
	 * 加载数据结束时的回调
	 */
	private IGetServicesDataCallBack callBack2 = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			if (entity.isOk()) {
				if (ShowGetDataError.isCodeSuccess(mContext, entity.getInfo())) {
					if (what_GetYinLianOrderPrePayNum == entity.what) {
						JsonMap<String, Object> data = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
								JsonKeys.Key_Info);
						if (data != null & data.size() > 0) {
							prepayId = data.getStringNoNull("tn");
							LogUtil.e("tn", prepayId);
							startYinLanPlugin();
						}
					} else if (what_GetWeiXinPrePayNum == entity.what) {
						JsonMap<String, Object> data = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
								JsonKeys.Key_Info);
						if (data != null & data.size() > 0) {
							sendPayReq(data);
						}
					} else if (entity.what == GetDataConfing.What_GetConfigInfo) {
						JsonMap<String, Object> info = JsonParseHelper
								.getJsonMap_JsonMap(entity.getInfo(), JsonKeys.Key_Info);
						initAlipyPayData(info.getStringNoNull("signContent"));
					}
				} else {
					payfailure();
				}

			} else {
				ShowGetDataError.showNetError(mContext);
				payfailure();
			}
			loadingToast.dismiss();
		}
	};

	private static final int RQF_PAY = 1;

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case RQF_PAY:
					PayResult payResult = new PayResult((String) msg.obj);
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						paysuccess();
					} else {
						// 判断resultStatus 为非"9000"则代表可能支付失败
						// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						//if (TextUtils.equals(resultStatus, "8000")) {
						//Toast.makeText(UsedMobileSecurePayHelper.this.context, "支付结果确认中", Toast.LENGTH_SHORT).show();

						//} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						//Toast.makeText(UsedMobileSecurePayHelper.this.context, "支付失败", Toast.LENGTH_SHORT).show();

						//}
						payfailure();
					}
					break;
				default:
					break;
			}
		}
	};

	private void initAlipyPayData(final String url) {
		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(mContext);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(url, true);
				Message msg = new Message();
				msg.what = RQF_PAY;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	private void startYinLanPlugin() {
		if (TextUtils.isEmpty(prepayId)) {
			toast.showToast("网络连接失败异常");
			payfailure();
		} else {
			//步骤2：通过银联工具类启动支付插件
			doStartUnionPayPlugin(prepayId, mMode);
		}
	}

	@SuppressLint("NewApi")
	public void doStartUnionPayPlugin(String tn, String mode) {
		int ret = UPPayAssistEx.startPay(this, null, null, tn, mode);
		if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
			// 需要重新安装控件
			AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.lightdialog2);
			builder.setTitle("提示");
			builder.setMessage("完成购买需要安装银联支付控件，是否安装？");
			builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					UPPayAssistEx.installUPPayPlugin(mContext);

				}
			});
			builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					payfailure();
				}
			});
			builder.create().show();
		}
	}

	//"paySignKey":null,"notifyUrl":"WeiXinPayNotify.jsp"}}}
	private void sendPayReq(JsonMap<String, Object> data) {
		prepayId = data.getStringNoNull("preNumber");
		JsonMap map = data.getJsonMap("weChatCofig");
		WeixiPay.WeiXinPartnerId = map.getString("partnerId");
		WeixiPay.WeiXinPartnerKey = map.getStringNoNull("partnerKey");
		if (TextUtils.isEmpty(prepayId) || TextUtils.isEmpty(WeixiPay.WeiXinPartnerId) || TextUtils.isEmpty(WeixiPay.WeiXinPartnerKey)) {
			payfailure();
			toast.showToast(R.string.pay_info_error);
			return;
		}
		req = WeixiPay.genPayReq(req, prepayId);
		msgApi.registerApp(ShareSocialUtils.WEIXIN_APP_ID);
		msgApi.sendReq(req);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		//步骤3：处理银联手机支付控件返回的支付结果
		if (data == null) {
			payfailure();
			return;
		}
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			paysuccess();
		} else {
			payfailure();
		}
	}

	/**
	 * 微信支付结果
	 */
	protected BroadcastReceiver mpayBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Constant.ACT_WEIXINPAYCODE.equalsIgnoreCase(action)) {
				int result = intent.getIntExtra("result", 0);
				if (result == 0) {
					paysuccess();
				} else {
					payfailure();
				}
			}
		}
	};

	/**
	 * 支付成功
	 */
	private void paysuccess() {
		sendPayResult(0);
		Intent intent = new Intent(mContext, OrderResultActivity.class);
		intent.putExtra(ExtraKeys.Key_Msg1, orderdata.toJson());
		intent.putExtra(ExtraKeys.Key_Msg2, fromType);
		setResult(RESULT_OK);
		jumpTo(intent, true);

	}

	/**
	 * 支付失败
	 */
	private void payfailure() {
		if (fromType == 1) {
			sendPayResult(1);
			if (type == 1) {
				jumpTo(MyOrderActivity2.class, true);
			} else {
				Intent intentSeeLogistics = new Intent(mContext, MyOrderDetailActivity.class);
				intentSeeLogistics.putExtra(Constant.ORDER_ID, orderId);
				jumpTo(intentSeeLogistics, true);
			}
		}
	}

	/**
	 * 发送支付结果的通知
	 *
	 * @param i
	 */
	private void sendPayResult(int i) {
		sendBroadcast(new Intent(Constant.ACT_PAY_OVER).putExtra(ExtraKeys.Key_Msg1, i));
	}


	/**
	 * @Title: list_mlv
	 * @Description: TODO(方法的作用：列表点击事件)
	 */
	AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			payId = data_peisong.get(position).getInt("payId");
			adapter.setSelected(position);
			adapter.notifyDataSetChanged();
		}
	};


	/**
	 * 获取服务器支付和配送新
	 */
	private void initData() {
		JsonMap<String, Object> jsonMap = new JsonMap<String, Object>();
		jsonMap.put("payName", getString(R.string.pay_alipay));
		jsonMap.put("payId", Constant.ALIPAY_PAYID);
		jsonMap.put("payLogo", R.drawable.pay_zhifubao);
		data_peisong.add(jsonMap);
		jsonMap = new JsonMap<String, Object>();
		jsonMap.put("payName", getString(R.string.pay_wechat));
		jsonMap.put("payId", Constant.WEICHAT_PAYID);
		jsonMap.put("payLogo", R.drawable.pay_weichat);
		data_peisong.add(jsonMap);
		jsonMap = new JsonMap<String, Object>();
		jsonMap.put("payName", getString(R.string.pay_unionpay));
		jsonMap.put("payId", Constant.UNION_PAYID);
		jsonMap.put("payLogo", R.drawable.pay_union);
		data_peisong.add(jsonMap);
		/*jsonMap = new JsonMap<String,Object>();
		jsonMap.put("payName", getString(R.string.pay_cash));
        jsonMap.put("payId", Constant.CASH_PAYID);
        jsonMap.put("payLogo", R.drawable.pay_zhifubao);
        data_peisong.add(jsonMap);*/

		adapter = new PaymentListAdapter(this, data_peisong);
		switch (payId) {
			case Constant.ALIPAY_PAYID:
				adapter.setSelected(0);
				break;
			case Constant.WEICHAT_PAYID:
				adapter.setSelected(1);
				break;
			case Constant.UNION_PAYID:
				adapter.setSelected(2);
				break;
			default:
				break;
		}
		gvns_payment.setAdapter(adapter);
	}


	public void payOrderTvClick(View v) {
		gopaytype();
	}

	@Override
	public void onBackPressed() {
		if (fromType == 1) {
			sendPayResult(1);
			if (type == 1) {
				jumpTo(MyOrderActivity2.class, true);
			} else {
				Intent intentSeeLogistics = new Intent(mContext, MyOrderDetailActivity.class);
				intentSeeLogistics.putExtra(Constant.ORDER_ID, orderId);
				jumpTo(intentSeeLogistics, true);
			}
		} else {
			super.onBackPressed();
		}
	}
}
