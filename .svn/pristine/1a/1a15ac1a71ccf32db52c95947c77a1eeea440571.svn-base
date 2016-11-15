/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */
package com.yishanxiu.yishang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.MyOrderCommentsAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.getdata.GetDataQueue.MediaType;
import com.yishanxiu.yishang.utils.BitmapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.Utils;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;
import com.yishanxiu.yishang.R;

/**
 * 订单评论
 * 
 * @author FangDongzhang
 * @updata 2016/7/14
 */
public class MyOrderComments extends BaseUIActivity {
	private List<JsonMap<String, Object>> selectProduct = new ArrayList<JsonMap<String, Object>>();
	private MyOrderCommentsAdapter myOrderCommentsAdapter;
	
	@ViewInject(id=R.id.listView_pro)
	private ListView listView_pro;
	
	@ViewInject(id=R.id.brand_logo)
	private ImageView brand_logo;
	
	@ViewInject(id=R.id.brandName_tv)
	private TextView brandName_tv;
	
	@ViewInject(id=R.id.submit_comments,click = "submit_comments")
	private TextView submit_comments;
	
	private String shopId;
	private List<JsonMap<String, Object>> jsonMaps;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order_comments);
		setCenter_title(getResources().getString(R.string.order_comment));
		Intent intent = getIntent();
		String s = intent.getStringExtra("selectProduct");
		JsonMap<String, Object> jsonMap = JsonParseHelper.getJsonMap(s);
		selectProduct.add(jsonMap);
		
		initAdapter();
		jsonMaps = selectProduct.get(0).getList_JsonMap("orderProducts");
		shopId = selectProduct.get(0).getStringNoNull("shopId");
		setData(jsonMaps);
		
		initData(selectProduct.get(0).getStringNoNull("logoPath"), selectProduct.get(0).getStringNoNull("name"));

	}

	/**
	 * 适配数据
	 */
	private void initAdapter() {
		myOrderCommentsAdapter = new MyOrderCommentsAdapter(mContext);
		listView_pro.setAdapter(myOrderCommentsAdapter);
	}

	public void submit_comments(View v){
		getServerData();
	}
	/**
	 * 评论
	 */
	private void getServerData() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (JsonMap<String, Object> jsonMap : jsonMaps) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", jsonMap.getStringNoNull("orderId"));
			params.put("shopId", shopId);
			params.put("orderProductId", jsonMap.getStringNoNull("orderProductId"));
			if (jsonMap.containsKey("list_item_inputvalue")) {
				params.put("commentContent", jsonMap.getStringNoNull("list_item_inputvalue"));
			} else {
				params.put("commentContent", getString(R.string.product_default_commtent));
			}
			params.put("productId", jsonMap.getStringNoNull("productId"));
			params.put("productSpecification", jsonMap.getStringNoNull("productSpecification"));
			params.put("createUser", Utils.getUserId(mContext));
			list.add(params);
		}
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.ProductComment_SaveProductComment);
		queue.setWhat(GetDataConfing.What_ProductComment_SaveProductComment);
		queue.setParamsNoJson(list);
		queue.setMediaType(MediaType.JSON);
		getDataUtil.getData(queue);
	}

	/**
	 * 获取服务器数据的返回信息
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.isOk()) {
				if (ShowGetDataError.isCodeSuccess(mContext, entity.getInfo())) {
					if (entity.what == GetDataConfing.What_ProductComment_SaveProductComment) {
						toast.showToast("评价成功");
						sendBroadcast(new Intent(Constant.ORDER_STATUS_CHANGE));
						finish();
					}
				}
			} else {
				ShowGetDataError.showNetError(mContext);
			}
		}
	};

	/**
	 * 适配数据
	 */
	private void setData(List<JsonMap<String, Object>> datas) {
		myOrderCommentsAdapter.setDatas(datas);
		myOrderCommentsAdapter.notifyDataSetChanged();
	}

	/**
	 * 初始化数据
	 */
	private void initData(String pUrl, String brandName) {
		BitmapHelper.loadImage(this, pUrl, brand_logo);
		brandName_tv.setText(brandName);
	}

}
