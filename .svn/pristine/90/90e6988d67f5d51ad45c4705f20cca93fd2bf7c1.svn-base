/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */
package com.yishanxiu.yishang.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.MyorderdDetailAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.Constant.ShopCartItemCompontType;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.Utils;

import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonMapOrListJsonMap2JsonUtil;
import net.tsz.afinal.json.JsonParseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情
 *
 * @author FangDongzhang
 * @updata 2016/7/6
 */
public class MyOrderDetailActivity extends BaseUIActivity {
    /**
     * 收货人布局
     */
    @ViewInject(id = R.id.s_a_o_ll_shouhuorenxinxi)
    private LinearLayout ll_shouhuoren;
    /**
     * 收货人
     */
    @ViewInject(id = R.id.s_a_o_tv_shouhuoren)
    private TextView addresse;
    /**
     * 收货人电话
     */
    @ViewInject(id = R.id.s_a_o_tv_phone)
    private TextView user_phone;
    /**
     * 收货人地址
     */
    @ViewInject(id = R.id.s_a_o_tv_address)
    private TextView user_address;
    /**
     * 商品列表
     */
    @ViewInject(id = R.id.s_a_o_lvns_prolist)
    private ListView lvns_proList;
    /**
     * 由购物车传入的订单商品信息
     */
    private List<JsonMap<String, Object>> datas = new ArrayList<JsonMap<String, Object>>();

    private MyorderdDetailAdapter adapter;

    private int count;
    private String productPrice;
    private String isComment;
    private String orderId;
    private String orderId_confirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail);
        setCenter_title(getResources().getString(R.string.order_detail));
        // getData_getDefInfo();
        Intent intent = getIntent();
        // String total_price = intent.getStringExtra(ExtraKeys.Key_Msg1);
        orderId = intent.getStringExtra(Constant.ORDER_ID);
        if (TextUtils.isEmpty(orderId)) {
            String s = intent.getStringExtra("selectProduct");
            JsonMap<String, Object> jsonMap = JsonParseHelper.getJsonMap(s);
            orderId = jsonMap.getStringNoNull("orderId");
            datas.add(jsonMap);
            flushShouHuoRen(jsonMap.getStringNoNull("addresse"), jsonMap.getStringNoNull("userPhone"),
                    jsonMap.getStringNoNull("address"));
        } else {
            loadingToast.show();
            getServerData(orderId);
        }
        registerReceiver(broadcastReceiver, new IntentFilter(Constant.ACT_PAY_OVER));
        setAdapter_porList();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // orderId = intent.getStringExtra(Constant.ORDER_ID);
        // if (TextUtils.isEmpty(orderId)) {
        // String s = intent.getStringExtra("selectProduct");
        // JsonMap<String, Object> jsonMap = JsonParseHelper.getJsonMap(s);
        // orderId = jsonMap.getStringNoNull("orderId");
        // datas.add(jsonMap);
        // flushShouHuoRen(jsonMap.getStringNoNull("addresse"),
        // jsonMap.getStringNoNull("userPhone"),
        // jsonMap.getStringNoNull("address"));
        // } else {
        // loadingToast.show();
        // getServerData(orderId);
        // }
        // registerReceiver(broadcastReceiver, new
        // IntentFilter(Constant.ACT_PAY_OVER));
        // setAdapter_porList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadingToast.show();
        getServerData(orderId);
    }

    /**
     * 获得全部订单
     */
    private void getServerData(String orderId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", Utils.getUserId(mContext));
        params.put("orderId", orderId);
        // params.put("pageNo", String.valueOf(nPage));
        GetDataQueue queue = new GetDataQueue();
        queue.setCallBack(callBack);
        queue.setActionName(GetDataConfing.Action_GetOrderByOrderStatus);
        queue.setWhat(GetDataConfing.What_GetOrderByOrderStatus);
        queue.setParamsNoJson(params);
        getDataUtil.getData(queue);
    }

    private MyorderdDetailAdapter.OnItemClickListener onItemClickListener = new MyorderdDetailAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(int position, ShopCartItemCompontType shopCartitemType, int index) {
            if (shopCartitemType == Constant.ShopCartItemCompontType.CLICK_GOODS_ITEM) {
                JsonMap<String, Object> orderJsonMap = datas.get(position);
                List<JsonMap<String, Object>> orderProducts = orderJsonMap.getList_JsonMap("orderProducts");
                JsonMap<String,Object> productMap=orderProducts.get(index);
                String productId=productMap.getStringNoNull("productId");
                String productName=productMap.getStringNoNull("productName");
                String path=productMap.getStringNoNull("productPic");
                String brandName=productMap.getStringNoNull("brandName");

                ProductInfoModel productInfoModel=new ProductInfoModel();
                productInfoModel.setProductId(productId);
                productInfoModel.setProductName(productName);
                productInfoModel.setBrandName(brandName);
                productInfoModel.setPath(path);
                seeProductDetail(productInfoModel);
            } else if (shopCartitemType == Constant.ShopCartItemCompontType.CANCEL_ORDER) {
                JsonMap<String, Object> orderJsonMap = datas.get(position);
                String orderStatus = orderJsonMap.getStringNoNull("orderStatus");
                if (orderJsonMap.containsKey("returnRefundOrderNumber")) {
                    if (orderJsonMap.getInt("returnRefundLatestStatus") == 7) {
                        QueryProgress(orderJsonMap);
                    }
                } else {
                    switch (Integer.valueOf(orderStatus)) {
                        case 1:
                            getData_Cancel_Order(orderId,callBack);
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
                JsonMap<String, Object> orderJsonMap = datas.get(position);
                String isComment = orderJsonMap.getStringNoNull("isComment");
                String orderStatus = orderJsonMap.getStringNoNull("orderStatus");
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
                            goodsReceiveConfirm(orderId,callBack);
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
                count = 0;
                JsonMap<String, Object> orderJsonMap = datas.get(position);
                String orderId = orderJsonMap.getStringNoNull("orderId");
                int orderStatus = orderJsonMap.getInt("orderStatus");
                List<JsonMap<String, Object>> productJsonMaps = orderJsonMap.getList_JsonMap("orderProducts");
                int returnRefundStatus = productJsonMaps.get(index).getInt("returnRefundStatus");
                String orderProductId = productJsonMaps.get(index).getStringNoNull("orderProductId");

                for (int i = 0; i < productJsonMaps.size(); i++) {
                    int rrStatus = productJsonMaps.get(i).getInt("returnRefundStatus");
                    int Status = rrStatus % 10;
                    if (rrStatus > 0 && rrStatus < 999 && !(Status == 2 || Status == 5 || Status == 9)) {
                        count++;
                    }
                }


                if (count >= productJsonMaps.size() - 1) {
                    if (productJsonMaps.get(index).getStringNoNull("productActivityPrice").equals("") || productJsonMaps.get(index).getStringNoNull("productPrice")
                            .equals(productJsonMaps.get(index).getStringNoNull("productActivityPrice"))) {

                        productPrice = String.valueOf(productJsonMaps.get(index).getDouble("productPrice")
                                * productJsonMaps.get(index).getInt("productCount") + orderJsonMap.getDouble("freight"));

                    } else {
                        productPrice = String.valueOf(productJsonMaps.get(index).getDouble("productActivityPrice")
                                * productJsonMaps.get(index).getInt("productCount") + orderJsonMap.getDouble("freight"));
                    }

                } else {
                    if ((productJsonMaps.get(index).getStringNoNull("productActivityPrice").equals("")) || productJsonMaps.get(index).getStringNoNull("productActivityPrice").equals(productJsonMaps.get(index).getStringNoNull("productPrice"))) {
                        productPrice = String.valueOf(productJsonMaps.get(index).getDouble("productPrice")
                                * productJsonMaps.get(index).getInt("productCount"));

                    } else {
                        productPrice = String.valueOf(productJsonMaps.get(index).getDouble("productActivityPrice")
                                * productJsonMaps.get(index).getInt("productCount"));
                    }


                }
                returnRefund(orderId, orderProductId, productPrice, returnRefundStatus, orderStatus);
            }
        }
    };

    /**
     * 跳转到评论
     */
    private void comments(JsonMap<String, Object> selectProduct) {
        Intent intent = new Intent(mContext, MyOrderComments.class);
        JsonMapOrListJsonMap2JsonUtil<String, Object> utils = new JsonMapOrListJsonMap2JsonUtil<String, Object>();
        intent.putExtra("selectProduct", utils.map2Json(selectProduct));
        startActivityForResult(intent, 0);
    }

    /**
     * 跳转到查看评论
     */
    private void queryOrderComments(JsonMap<String, Object> selectProduct) {
        Intent intent = new Intent(mContext, QueryOrderCommentActivity.class);
        JsonMapOrListJsonMap2JsonUtil<String, Object> utils = new JsonMapOrListJsonMap2JsonUtil<String, Object>();
        intent.putExtra("orderId", selectProduct.getStringNoNull("orderId"));
        startActivity(intent);
    }

    /**
     * 去生成订单页面
     */
    private void goCommited(int position) {
        JsonMap<String, Object> order = new JsonMap<String, Object>();
        JsonMap<String, Object> userReceiptAddress = new JsonMap<String, Object>();
        JsonMap<String, Object> jsonMap = datas.get(position);
        userReceiptAddress.put("consignee", jsonMap.getStringNoNull("addresse"));
        userReceiptAddress.put("consigneeAddress", jsonMap.getStringNoNull("address"));
        userReceiptAddress.put("phone", jsonMap.getStringNoNull("userPhone"));
        order.put("userReceiptAddress", userReceiptAddress);
        order.put("actualPayPrice", jsonMap.getStringNoNull("finalPrice"));
        order.put("orderId", jsonMap.getStringNoNull("orderId"));
        order.put("externalOrderNumber", jsonMap.getStringNoNull("externalOrderNumber"));
        order.put("shopCartVoList", jsonMap.getList_JsonMap("orderProducts"));
        order.put("payType", jsonMap.getList_JsonMap("payType"));
        Intent intent = new Intent(mContext, PayMethodActivity.class);
        intent.putExtra(ExtraKeys.Key_Msg2, order.toJson());
        jumpTo(intent, false);
    }

    /**
     * 跳转到退货
     */
    private void returns(JsonMap<String, Object> jsonMap) {
        int orderStatusInt = jsonMap.getInt("orderStatus");
        if (orderStatusInt >= 2 && orderStatusInt <= 4) {
            Intent intent = new Intent(mContext, ReturnActivity.class);
            intent.putExtra("addresse", jsonMap.getStringNoNull("addresse"));
            intent.putExtra("userPhone", jsonMap.getStringNoNull("userPhone"));
            intent.putExtra("zipcode", jsonMap.getStringNoNull("zipcode"));
            intent.putExtra("address", jsonMap.getStringNoNull("address"));
            startActivityForResult(intent, 0);
        }
    }



    /**
     * 跳转查看物流
     */
    private void Logisitic(JsonMap<String, Object> jsonMap) {
        Intent intent = new Intent();
        intent.setClass(mContext, LogisticActivity.class);
        intent.putExtra("logisticNumber", jsonMap.getStringNoNull("logisticNumber"));
        intent.putExtra("logistic", jsonMap.getStringNoNull("logistic"));
        startActivityForResult(intent, 0);
    }

    /**
     * 跳转到查询进度
     */
    private void QueryProgress(JsonMap<String, Object> jsonMap) {
        Intent intent = new Intent();
        intent.setClass(mContext, QueryProgressActivity.class);
        intent.putExtra("returnRefundOrderId", jsonMap.getStringNoNull("returnRefundOrderId"));
        startActivityForResult(intent, 0);
    }

    // 跳转到申请售后
    private void returnRefund(String orderId, String orderProductId, String productPrice, int returnRefundStatus,
                              int orderStatus) {
        int orderStatusInt = Integer.valueOf(orderStatus);
        if (orderStatusInt >= 2 && orderStatusInt <= 4) {
            Intent intent = new Intent(mContext, ReturnRefundActivity.class);
            intent.putExtra("orderStatus", orderStatus);
            intent.putExtra("returnRefundStatus", returnRefundStatus);
            intent.putExtra("productPrice", productPrice);
            intent.putExtra("orderId", orderId);
            intent.putExtra("orderProductId", orderProductId);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mContext.setResult(RESULT_OK);
            mContext.finish();
        }
    };

    /**
     * 刷新收货人信息
     */
    private void flushShouHuoRen(String userNickname, String userPhone, String address) {
        ll_shouhuoren.setVisibility(View.VISIBLE);
        addresse.setText(userNickname);
        user_phone.setText(userPhone);
        user_address.setText(address);
    }

    /**
     * 为已选定的商品设定数据源
     */
    private void setAdapter_porList() {
        adapter = new MyorderdDetailAdapter(this, datas);
        adapter.setOnItemClickListener(onItemClickListener);
        lvns_proList.setAdapter(adapter);

    }

    /**
     * 与服务器通信的回调
     */
    private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {

        @Override
        public void onLoaded(GetDataQueue entity) {
            if (entity.isOk()) {
                if (ShowGetDataError.isCodeSuccess(mContext, entity.getInfo())) {
                    if (entity.what == GetDataConfing.What_CancelOrderbyOrderNum) {
                        sendBroadcast(new Intent(Constant.ORDER_STATUS_CHANGE));
                        JsonMap<String, Object> info = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
                                JsonKeys.Key_Info);
                        toast.showToast(R.string.cancel_s);
                        finish();
                    } else if (entity.what == GetDataConfing.What_BuyOrderUpdateOrderStatus) {
                        sendBroadcast(new Intent(Constant.ORDER_STATUS_CHANGE));
                        JsonMap<String, Object> info = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
                                JsonKeys.Key_Info);
                        toast.showToast("确认收货成功");
                        finish();
                    } else if (entity.getWhat() == GetDataConfing.What_GetOrderByOrderStatus) {
                        datas.clear();
                        List<JsonMap<String, Object>> info = JsonParseHelper.getJsonMap_List_JsonMap(entity.getInfo(),
                                JsonKeys.Key_Info);
                        JsonMap<String, Object> jsonMap = info.get(0);
                        datas.add(jsonMap);
                        flushShouHuoRen(jsonMap.getStringNoNull("addresse"), jsonMap.getStringNoNull("userPhone"),
                                jsonMap.getStringNoNull("address"));
                        setAdapter_porList();
                    }
                }
            } else {
                ShowGetDataError.showNetError(mContext);
            }
            loadingToast.dismiss();
        }
    };


}
