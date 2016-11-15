/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */
package com.yishanxiu.yishang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.ConfirmOrderAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.LogUtil;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.utils.Utils;
import com.yishanxiu.yishang.view.ListViewNoScroll;

/**
 * @author tanghuan
 * @author FangDongzhang 确认订单
 * @ClassName: ShoppingAddOrderActivity
 * @Description: TODO(填写订单类)
 * @date 2014年12月3日 下午7:51:44
 * @updata 2016/6/30
 */
public class ShopAddOrderActivity extends BaseUIActivity {
	/**
	 * 收货人布局
	 */
	@ViewInject(id = R.id.s_a_o_ll_shouhuorenxinxi, click = "selectShouHuoRan")
	private LinearLayout ll_shouhuoren;
	@ViewInject(id = R.id.recepientLayout)
	private LinearLayout recepientLayout;
	@ViewInject(id = R.id.noAddressTv)
	private TextView noAddressTv;
	/**
	 * 收货人
	 */
	@ViewInject(id = R.id.s_a_o_tv_shouhuoren)
	private TextView tv_shouhuren;
	/**
	 * 收货人电话
	 */
	@ViewInject(id = R.id.s_a_o_tv_phone)
	private TextView tv_shouhuren_dianhua;
	/**
	 * 收货人地址
	 */
	@ViewInject(id = R.id.s_a_o_tv_address)
	private TextView tv_shouhuren_dizhi;

	@ViewInject(id = R.id.right_image)
	private ImageView right_image;

	/**
	 * 提交订单
	 */
	@ViewInject(id = R.id.submitOrderTv, click = "submitOrderTvClick")
	private TextView submitOrderTv;

	/**
	 * 返回购物车修改
	 */
	@ViewInject(id = R.id.productInfoLayout)
	private ListViewNoScroll productInfoLayout;

	/**
	 * 应付金额
	 */
	@ViewInject(id = R.id.totalMoneyTv)
	private TextView totalMoneyTv;


	// 应付金额
	private double actualPayPrice;
	private double productTotalActivityPrice;
	private double totalFreight;
	/**
	 * 由购物车传入的订单商品信息 json格式
	 */
	private String selectProJson;
	/**
	 * 由购物车传入的订单商品信息
	 */
	private List<JsonMap<String, Object>> selectProduct;
	private ConfirmOrderAdapter shopOrderProductAdapter;
	/**
	 * 本次订单的收货信息的id 支付信息的id
	 */
	private JsonMap<String, Object> userReceiptAddress;
	private String addressId = "";

	private int payId = Constant.ALIPAY_PAYID;
	private boolean isSuccess=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_add_order);
		setCenter_title(R.string.shopping_add_order_title);
		right_image.setVisibility(View.VISIBLE);
		getData_getDefInfo();
		//totalMoneyTv.setText(StringUtils.getFormatMoneyWithSignMust(0));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == request_changeAddress) {
				String userReceiptAddressStr = data.getStringExtra(ExtraKeys.Key_Msg1);
				userReceiptAddress = JsonParseHelper.getJsonMap(userReceiptAddressStr);
				flushShouHuoRen(userReceiptAddress);
				changeAddressRequest();
			}
		}

	}

	/**
	 * 更改收货地址重新计算运费
	 * {
	 * "userId": "1",
	 * "provinceId": "116",
	 * "cityId": "1148",
	 * "districtId": "15571"
	 * }
	 */
	private void changeAddressRequest() {
		loadingToast.show();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", Utils.getUserId(this));
		params.put("provinceId", userReceiptAddress.getStringNoNull("provinceId"));
		params.put("cityId", userReceiptAddress.getStringNoNull("cityId"));
		params.put("districtId", userReceiptAddress.getStringNoNull("districtId"));
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_queryFreightByAddressId);
		queue.setCallBack(callBack);
		queue.setParamsNoJson(params);
		queue.setMediaType(GetDataQueue.MediaType.JSON);
		queue.setWhat(GetDataConfing.What_queryFreightByAddressId);
		getDataUtil.getData(queue);
	}

	private void flushBottomView(JsonMap<String, Object> jsonMap) {
		productTotalActivityPrice = jsonMap.getDouble("productTotalActivityPrice");
		totalFreight = jsonMap.getDouble("totalFreight", 0d);
		actualPayPrice = productTotalActivityPrice + totalFreight;
		String totle_money_formart = String.format(getString(R.string.totle_money), StringUtils.getFormatMoneyWithSignMust(actualPayPrice));
		totalMoneyTv.setText(totle_money_formart);
	}

	/**
	 * 刷新结算清单 取消优惠券
	 *
	 * @param data
	 */
	private void flushJieSuanqingdan(JsonMap<String, Object> data) {
		productTotalActivityPrice = data.getDouble("productTotalActivityPrice", 0d);
		totalMoneyTv.setText(StringUtils.getFormatMoneyWithSignMust(productTotalActivityPrice));
	}


	/**
	 * 刷新收货人信息
	 */
	private void flushShouHuoRen(JsonMap<String, Object> userReceiptAddress) {
		if(userReceiptAddress.size()>0) {
			noAddressTv.setVisibility(View.GONE);
			recepientLayout.setVisibility(View.VISIBLE);
			String shr = userReceiptAddress.getStringNoNull("consignee");
			String dh = userReceiptAddress.getStringNoNull("phone");
			String dz = userReceiptAddress.getStringNoNull("consigneeAddress");
			addressId = userReceiptAddress.getStringNoNull("addressId");
			tv_shouhuren.setText(shr);
			tv_shouhuren_dianhua.setText(dh);
			tv_shouhuren_dizhi.setText(dz);
		} else {
			noAddressTv.setVisibility(View.VISIBLE);
			recepientLayout.setVisibility(View.GONE);
		}
	}


	/**
	 * 去生成订单页面
	 */
	private void goCommited(JsonMap<String, Object> order) {
		order.put("payId", payId);
		order.put("actualPayPrice", actualPayPrice);
		order.put("userReceiptAddress", userReceiptAddress);
		order.put("shopCartVoList", selectProduct.get(0).getStringNoNull("skuShopCartVo"));
		Intent intent = new Intent(this, PayMethodActivity.class);
		intent.putExtra(ExtraKeys.Key_Msg1,1);
		intent.putExtra(ExtraKeys.Key_Msg2, order.toJson());
		jumpTo(intent, true);
	}

	/**
	 * 为已选定的商品设定数据源
	 */
	private void setAdapter_porList() {
		shopOrderProductAdapter = new ConfirmOrderAdapter(this, selectProduct);
		productInfoLayout.setAdapter(shopOrderProductAdapter);
	}

	/**
	 * 去选择收货信息
	 */
	public void selectShouHuoRan(View v) {
		Intent intent = new Intent(this, SelectRecipientActivity.class);
		startActivityForResult(intent, request_changeAddress);
	}

	int request_changeAddress = 1;

	/**
	 * 提交订单
	 */
	public void submitOrderTvClick(View v) {
		if (TextUtils.isEmpty(addressId)) {
			toast.showToast(R.string.shopping_add_order_payisnull);
			return;
		}
		getData_tijiao();
	}

	/**
	 * 调用服务器提交订单   buyerNote = "";
	 * shopFreight = "0.01";
	 * shopId = 1;
	 * skuShopCartVos =             (
	 * {
	 * shopcartId = 114;
	 * }
	 */
	private void getData_tijiao() {
		loadingToast.show();
		ArrayList<HashMap<String, Object>> shopList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < selectProduct.size(); i++) {
			JsonMap mapTmp = selectProduct.get(i);
			JsonMap<String, Object> shopProductMap = new JsonMap<String, Object>();
			View view = (View) productInfoLayout.getChildAt(i);
			ConfirmOrderAdapter.ShopCartAdapterViewHolder shopCartAdapterViewHolder = (ConfirmOrderAdapter.ShopCartAdapterViewHolder) view.getTag();
			String noteStr = shopCartAdapterViewHolder.note_ed.getText().toString();
			shopProductMap.put("buyerNote", noteStr);

			shopProductMap.put("shopFreight", mapTmp.getStringNoNull("shopFreight"));
			shopProductMap.put("shopId", mapTmp.getStringNoNull("shopId"));
			ArrayList<JsonMap<String, Object>> skuShopCartVos = new ArrayList<JsonMap<String, Object>>();
			ArrayList<JsonMap<String, Object>> skuShopCartVo = (ArrayList<JsonMap<String, Object>>) mapTmp.getList_JsonMap("skuShopCartVo");
			for (JsonMap<String, Object> map : skuShopCartVo) {
				JsonMap<String, Object> productMap = new JsonMap<String, Object>();
				productMap.put("shopcartId", map.getStringNoNull("shopcartId"));
				skuShopCartVos.add(productMap);
			}
			shopProductMap.put("skuShopCartVos", skuShopCartVos);
			shopList.add(shopProductMap);
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", Utils.getUserId(mContext));
		params.put("addressId", addressId);
		params.put("payType", payId + "");
		params.put("shopList", shopList);

		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_shoppingAddOrderInformation);
		queue.setMediaType(GetDataQueue.MediaType.JSON);
		queue.setCallBack(callBack);
		queue.setParamsNoJson(params);
		queue.setWhat(GetDataConfing.What_shoppingAddOrderInformation);
		getDataUtil.getData(queue);
	}


	/**
	 * 获取订单信息
	 */
	private void getData_getDefInfo() {
		loadingToast.show();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", Utils.getUserId(this));
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_shoppingEditOrderInformation);
		queue.setCallBack(callBack);
		queue.setParamsNoJson(params);
		queue.setWhat(GetDataConfing.What_shoppingEditOrderInformation);
		getDataUtil.getData(queue);
	}

	/**
	 * 与服务器通信的回调
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {

		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.isOk()) {
				if (ShowGetDataError.isCodeSuccess(mContext, entity.getInfo())) {
					if (GetDataConfing.What_shoppingEditOrderInformation == entity.what) {
						JsonMap<String, Object> orderdata = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
								JsonKeys.Key_Info);
						userReceiptAddress = orderdata.getJsonMap("userReceiptAddress");
						flushShouHuoRen(userReceiptAddress);
						flushBottomView(orderdata);
						selectProduct = orderdata.getList_JsonMap("shopCartVoList");
						setAdapter_porList();

					} else if (GetDataConfing.What_shoppingAddOrderInformation == entity.what) {
						JsonMap<String, Object> orderdata = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
								JsonKeys.Key_Info);
						goCommited(orderdata);
					} else if (GetDataConfing.What_queryFreightByAddressId == entity.getWhat()) {
						JsonMap<String, Object> info = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
								JsonKeys.Key_Info);
						flushFreight(info);
					}
				} else {
					 if (GetDataConfing.What_queryFreightByAddressId == entity.getWhat()) {
						isSuccess=false;
					}
				}
			} else {
				ShowGetDataError.showNetError(mContext);
			}


		}
	};

	/**
	 * 更改运费 重新计算总价
	 * @param info  {"shopFreight":{"1":0.01},"totleFreight":0.01}
	 */
	private void flushFreight(JsonMap<String, Object> info) {
		LogUtil.d(TAG,info.toJson());
		totalFreight=info.getDouble("totleFreight",0d);
		actualPayPrice = productTotalActivityPrice + totalFreight;
		String totle_money_formart = String.format(getString(R.string.totle_money), StringUtils.getFormatMoneyWithSignMust(actualPayPrice));
		totalMoneyTv.setText(totle_money_formart);
		JsonMap<String,Object> shopFreightMap= info.getJsonMap("shopFreight");
		for (int i = 0; i < selectProduct.size(); i++) {
			JsonMap mapTmp = selectProduct.get(i);
			String shopId=mapTmp.getStringNoNull("shopId");
			double shopFreight=shopFreightMap.getDouble(shopId);
			mapTmp.put("shopFreight",shopFreight);
		}
		shopOrderProductAdapter.notifyDataSetChanged();
	}

}
