package com.yishanxiu.yishang.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.LogisticActivity;
import com.yishanxiu.yishang.activity.MyOrderActivity2;
import com.yishanxiu.yishang.activity.MyOrderComments;
import com.yishanxiu.yishang.activity.MyOrderDetailActivity;
import com.yishanxiu.yishang.activity.PayMethodActivity;
import com.yishanxiu.yishang.activity.QueryOrderCommentActivity;
import com.yishanxiu.yishang.activity.QueryProgressActivity;
import com.yishanxiu.yishang.activity.ReturnActivity;
import com.yishanxiu.yishang.activity.ReturnRefundActivity;
import com.yishanxiu.yishang.adapter.MyOrderAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.Constant.ShopCartItemCompontType;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonMapOrListJsonMap2JsonUtil;
import net.tsz.afinal.json.JsonParseHelper;

/**
 * 用户订单
 *
 * @author FangDongzhang 2016/7/6
 */
public class MyOrderFragment extends LazyFragment {

	@ViewInject(R.id.find_page_lv)
	private PullToRefreshListView xListView;

	private String order_status;

	private MyOrderAdapter myOrderAdapter;
	private List<JsonMap<String, Object>> dataList = new ArrayList<JsonMap<String, Object>>();
	private int nPage = 1;
	private boolean isSearch;
	private String searchKey;

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.my_order_fragment);
		ViewUtils.inject(this, getContentView());
		//xListView.getRefreshableView().setDivider(getResources().getDrawable(R.drawable.hor_line_l));
//		xListView.getRefreshableView().setHeaderDividersEnabled(false);
//		xListView.getRefreshableView().setFooterDividersEnabled(false);
//		xListView.forceLayout();
		myOrderAdapter = new MyOrderAdapter(activity);
		myOrderAdapter.setShowViewDivider(true);
		// userOrderAdapter.setItemCompontClickListener(blogItemCompontClickListener);
		xListView.setAdapter(myOrderAdapter);
		xListView.setMode(PullToRefreshBase.Mode.BOTH);
		xListView.setOnRefreshListener(loadMoreListener);
		myOrderAdapter.setOnItemClickListener(onItemClickListener);
		Bundle bundle = getArguments();
		isSearch = bundle.getBoolean("isSearch");
		if (isSearch) {

		} else {
			order_status = bundle.getString("orderStatus");
			if (order_status != "5") {
				activity.loadingToast.show();
				dataList.clear();
				getServerData(true, 0);
			} else if (order_status.equals("5")) {
				activity.loadingToast.show();
				dataList.clear();
				refundReturnsList(true, 0);
			}
		}
		IntentFilter intentFilter = new IntentFilter(Constant.ACT_PAY_OVER);
		intentFilter.addAction(Constant.ORDER_STATUS_CHANGE);
		activity.registerReceiver(broadcastReceiver, intentFilter);
	}

	@Override
	protected void onDestroyViewLazy() {
		activity.unregisterReceiver(broadcastReceiver);
		super.onDestroyViewLazy();
	}

	/**
	 * 监听订单状态变化
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (isSearch) {
				getServerData(true, 0);
			} else 	if (order_status.equals("5")) {
				refundReturnsList(true, 0);
			} else {
				getServerData(true, 0);
			}
		}
	};


	private PullToRefreshBase.OnRefreshListener2<ListView> loadMoreListener = new PullToRefreshBase.OnRefreshListener2<ListView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			if (isSearch) {
				getServerData(true, 0);
			} else if (order_status.equals("5")) {
				refundReturnsList(true, 1);
			} else {
				getServerData(true, 1);
			}
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			if (isSearch) {
				getServerData(true, 0);
			} else if (order_status.equals("5")) {
				refundReturnsList(false, 2);
			} else {
				getServerData(false, 2);
			}
		}
	};

	/**
	 * set 数据
	 *
	 * @return
	 */
	private JsonMap<String, Object> selectProduct;
	private String orderId;
	private String orderId_confirm;
	private String returnRefundStatus;
	private String orderStatus;
	private String isComment;
	private String address;
	private String totalPrice;
	private MyOrderAdapter.OnItemClickListener onItemClickListener = new MyOrderAdapter.OnItemClickListener() {

		@Override
		public void onItemClick(int position, ShopCartItemCompontType shopCartitemType, int index) {
			if (shopCartitemType == Constant.ShopCartItemCompontType.CLICK_GOODS_ITEM) {
				selectProduct = dataList.get(position);
				if (!selectProduct.containsKey("returnRefundOrderNumber")) {
					orderDetailActivity(selectProduct);
				}
			} else if (shopCartitemType == Constant.ShopCartItemCompontType.CANCEL_ORDER) {
				JsonMap<String, Object> orderJsonMap = dataList.get(position);
				orderId = orderJsonMap.getStringNoNull("orderId");
				orderStatus = orderJsonMap.getStringNoNull("orderStatus");
				if (orderJsonMap.containsKey("returnRefundOrderNumber")) {
					if (orderJsonMap.getInt("returnRefundLatestStatus") == 7) {
						QueryProgress(orderJsonMap);
					}
				} else {
					switch (Integer.valueOf(orderStatus)) {
						case 1:
							activity.getData_Cancel_Order(orderId,callBack);
							break;
						case 3:
							Logisitic(orderJsonMap);
							break;
						case 4:
							Logisitic(orderJsonMap);
							break;

						default:
							break;
					}
				}

			} else if (shopCartitemType == Constant.ShopCartItemCompontType.PAYMENT) {
				JsonMap<String, Object> orderJsonMap = dataList.get(position);
				isComment = orderJsonMap.getStringNoNull("isComment");
				orderStatus = orderJsonMap.getStringNoNull("orderStatus");
				address = orderJsonMap.getStringNoNull("address");
				totalPrice = orderJsonMap.getStringNoNull("totalPrice");

				if (orderJsonMap.containsKey("returnRefundOrderNumber")) {
					if (orderJsonMap.getInt("returnRefundLatestStatus") == 7) {
						returns(orderJsonMap);
					} else {
						QueryProgress(orderJsonMap);
					}
				} else {
					switch (Integer.valueOf(orderStatus)) {
						case 1:
							goCommited(position);
							break;
						case 3:
							// if (isComment.equals("0")) {
							// comments(orderJsonMap);
							// } else {
							orderId = orderJsonMap.getStringNoNull("orderId");
							activity.goodsReceiveConfirm(orderId,callBack);
							break;
						case 4:
							if (isComment.equals("0") && orderStatus.equals("4")) {
								comments(orderJsonMap);
							} else {
								queryOrderComments(orderJsonMap);
							}
							break;

						default:
							break;
					}
				}
			} else if (shopCartitemType == Constant.ShopCartItemCompontType.RETURN_REFUND) {
				JsonMap<String, Object> orderJsonMap = dataList.get(position);
				orderId = orderJsonMap.getStringNoNull("orderId");
				orderStatus = orderJsonMap.getStringNoNull("orderStatus");
				JsonMap<String, Object> productJsonMap = orderJsonMap.getList_JsonMap("orderProducts").get(index);
				returnRefundStatus = productJsonMap.getStringNoNull("returnRefundStatus");
				String orderProductId = productJsonMap.getStringNoNull("orderProductId");
				String productPrice = productJsonMap.getStringNoNull("productPrice");
				returnRefund(orderId, orderProductId, productPrice, returnRefundStatus, orderStatus);
			}
		}
	};

	/**
	 * 去生成订单页面
	 */
	private void goCommited(int position) {
		JsonMap<String, Object> order = new JsonMap<String, Object>();
		JsonMap<String, Object> userReceiptAddress = new JsonMap<String, Object>();
		JsonMap<String, Object> jsonMap = dataList.get(position);
		userReceiptAddress.put("consignee", jsonMap.getStringNoNull("addresse"));
		userReceiptAddress.put("consigneeAddress", jsonMap.getStringNoNull("address"));
		userReceiptAddress.put("phone", jsonMap.getStringNoNull("userPhone"));
		order.put("userReceiptAddress", userReceiptAddress);
		order.put("actualPayPrice", jsonMap.getStringNoNull("finalPrice"));
		order.put("orderId", jsonMap.getStringNoNull("orderId"));
		order.put("externalOrderNumber", jsonMap.getStringNoNull("externalOrderNumber"));
		order.put("shopCartVoList", jsonMap.getList_JsonMap("orderProducts"));
		order.put("payType", jsonMap.getList_JsonMap("payType"));
		Intent intent = new Intent(activity, PayMethodActivity.class);
		intent.putExtra(ExtraKeys.Key_Msg2, order.toJson());
		activity.jumpTo(intent, false);
	}

	/**
	 * 跳转到评论
	 */
	private void comments(JsonMap<String, Object> selectProduct) {
		Intent intent = new Intent(activity, MyOrderComments.class);
		JsonMapOrListJsonMap2JsonUtil<String, Object> utils = new JsonMapOrListJsonMap2JsonUtil<String, Object>();
		intent.putExtra("selectProduct", utils.map2Json(selectProduct));
		startActivityForResult(intent, 0);
	}

	/**
	 * 跳转到查看评论
	 */
	private void queryOrderComments(JsonMap<String, Object> selectProduct) {
		Intent intent = new Intent(activity, QueryOrderCommentActivity.class);
		JsonMapOrListJsonMap2JsonUtil<String, Object> utils = new JsonMapOrListJsonMap2JsonUtil<String, Object>();
		intent.putExtra("orderId", selectProduct.getStringNoNull("orderId"));
		startActivity(intent);
	}

	/**
	 * 跳转到退货
	 */
	private void returns(JsonMap<String, Object> jsonMap) {
		int orderStatusInt = jsonMap.getInt("orderStatus");
		if (orderStatusInt >= 2 && orderStatusInt <= 4) {
			Intent intent = new Intent(activity, ReturnActivity.class);
			intent.putExtra("shopId", jsonMap.getStringNoNull("shopId"));
			intent.putExtra("returnRefundOrderId", jsonMap.getStringNoNull("returnRefundOrderId"));
			// intent.putExtra("zipcode", jsonMap.getStringNoNull("zipcode"));
			// intent.putExtra("address", jsonMap.getStringNoNull("address"));
			startActivityForResult(intent, 0);
		}
	}

	/**
	 * 跳转查看物流
	 */
	private void Logisitic(JsonMap<String, Object> jsonMap) {
		Intent intent = new Intent();
		intent.setClass(activity, LogisticActivity.class);
		intent.putExtra("logisticNumber", jsonMap.getStringNoNull("logisticNumber"));
		intent.putExtra("logistic", jsonMap.getStringNoNull("logistic"));
		startActivityForResult(intent, 0);
	}

	/**
	 * 跳转到查询进度
	 */
	private void QueryProgress(JsonMap<String, Object> jsonMap) {
		Intent intent = new Intent();
		intent.setClass(activity, QueryProgressActivity.class);
		intent.putExtra("returnRefundOrderId", jsonMap.getStringNoNull("returnRefundOrderId"));
		startActivityForResult(intent, 0);
	}

	/**
	 * 跳转到订单详情页
	 */
	private void orderDetailActivity(JsonMap<String, Object> selectProduct) {
		Intent intent = new Intent(activity, MyOrderDetailActivity.class);
		JsonMapOrListJsonMap2JsonUtil<String, Object> utils = new JsonMapOrListJsonMap2JsonUtil<String, Object>();
		intent.putExtra("selectProduct", utils.map2Json(selectProduct));
		startActivityForResult(intent, 0);
	}

	/**
	 * 跳转到申请售后
	 */
	private void returnRefund(String orderId, String orderProductId, String productPrice, String returnRefundStatus,
	                          String orderStatus) {
		int orderStatusInt = Integer.valueOf(orderStatus);
		if (orderStatusInt >= 2 && orderStatusInt <= 4) {
			Intent intent = new Intent(activity, ReturnRefundActivity.class);
			intent.putExtra("orderStatus", Integer.parseInt(orderStatus));
			intent.putExtra("returnRefundStatus", Integer.parseInt(returnRefundStatus));
			intent.putExtra("productPrice", productPrice);
			intent.putExtra("orderId", orderId);
			intent.putExtra("orderProductId", orderProductId);
			startActivityForResult(intent, 0);
		}
	}



	/**
	 * 退货退款
	 */
	private void refundReturns(String orderId, String orderProductId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderProductId", orderProductId);
		params.put("orderId", orderId);
		params.put("userId", Utils.getUserId(activity));
		params.put("returnRefundType", "1");
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.GetAction_GetProdctInfo);
		queue.setWhat(GetDataConfing.what_GetProdctInfo);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 退款退货列表
	 */
	private void refundReturnsList(boolean chearAllData, int requestType) {
		if (chearAllData) {
			nPage = 1;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", Utils.getUserId(activity));
		params.put("pageCount", "10");
		params.put("pageNo", String.valueOf(nPage));
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setTag(requestType);
		queue.setActionName(GetDataConfing.Action_ReturnFundByOrderList);
		queue.setWhat(GetDataConfing.What_ReturnFundByOrderList);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 获得全部订单
	 *
	 * @param chearAllData
	 */
	private void getServerData(boolean chearAllData, int requestType) {
		if (chearAllData) {
			nPage = 1;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", Utils.getUserId(activity));
		if (isSearch) {
			params.put("productName", searchKey);
		} else {
			params.put("orderStatus", order_status);
			if (order_status.equals("4")) {
				params.put("isComment", "0");
			} else {
				params.put("isComment", "");
			}
		}
		params.put("pageCount", "10");

		params.put("pageNo", String.valueOf(nPage));
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setTag(requestType);
		queue.setActionName(GetDataConfing.Action_GetOrderByOrderStatus);
		queue.setWhat(GetDataConfing.What_GetOrderByOrderStatus);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 获取服务器数据的返回信息
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			activity.loadingToast.dismiss();
			xListView.onRefreshComplete();

			if (entity.isOk()) {
				if (ShowGetDataError.isCodeSuccess(activity, entity.getInfo())) {
					if (entity.what == GetDataConfing.What_GetOrderByOrderStatus) {
						List<JsonMap<String, Object>> datas = JsonParseHelper.getJsonMap_List_JsonMap(entity.getInfo(), JsonKeys.Key_Info);
						setAdapterData(datas, (Integer) entity.getTag());

					} else if (entity.what == GetDataConfing.What_ReturnFundByOrderList) {
						List<JsonMap<String, Object>> datas = JsonParseHelper.getJsonMap_List_JsonMap(entity.getInfo(), JsonKeys.Key_Info);
						setAdapterData(datas, (Integer) entity.getTag());

					} else if (entity.what == GetDataConfing.What_CancelOrderbyOrderNum) {
						activity.sendBroadcast(new Intent(Constant.ORDER_STATUS_CHANGE));
						JsonMap<String, Object> info = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
								JsonKeys.Key_Info);
						activity.toast.showToast(R.string.cancel_s);
						((MyOrderActivity2)activity).getServrData();
					} else if (entity.what == GetDataConfing.What_BuyOrderUpdateOrderStatus) {
						activity.sendBroadcast(new Intent(Constant.ORDER_STATUS_CHANGE));
						JsonMap<String, Object> info = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
								JsonKeys.Key_Info);
						activity.toast.showToast("收货成功");
						((MyOrderActivity2)activity).getServrData();
					}
				} else if (entity.what == GetDataConfing.what_GetProdctInfo) {
					activity.sendBroadcast(new Intent(Constant.ORDER_STATUS_CHANGE));
					JsonMap<String, Object> info = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
							JsonKeys.Key_Info);
					activity.toast.showToast(info.getStringNoNull(JsonKeys.Key_Msg));
					((MyOrderActivity2)activity).getServrData();
				}
			} else {
				ShowGetDataError.showNetError(activity);
			}
		}
	};

	private void setAdapterData(List<JsonMap<String, Object>> temp_data, int requestType) {
		if (requestType == 0) {
			this.dataList = temp_data;
			myOrderAdapter.setDatas(dataList);
			myOrderAdapter.notifyDataSetChanged();
			nPage++;
		} else if (requestType == 1) {
			dataList = temp_data;
			myOrderAdapter.setDatas(dataList);
			myOrderAdapter.notifyDataSetChanged();
			nPage++;
		} else if (requestType == 2) {
			if (temp_data.size() == 0) {

			} else {
				dataList.addAll(temp_data);
				myOrderAdapter.setDatas(dataList);
				myOrderAdapter.notifyDataSetChanged();
			}
			nPage++;
		}
	}


	/**
	 * 搜索接口
	 *
	 * @param searchKey
	 * @param isClearData
	 */
	public void startSearch(String searchKey, boolean isClearData) {
		this.isSearch = true;
		this.searchKey = searchKey;
		getServerData(isClearData, 0);
	}

}