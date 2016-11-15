package com.yishanxiu.yishang.activity;

import java.util.HashMap;
import java.util.Map;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.QueryOrderCommentsAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.view.ListViewNoScroll;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;

/**
 * 查询订单评论
 * 
 * @author FangDongzhang  2016/8/22
 *
 */
public class QueryOrderCommentActivity extends BaseUIActivity {
	
	@ViewInject(R.id.brand_logo)
	ImageView brand_logo;
	
	@ViewInject(R.id.brandName_tv)
	TextView brandName_tv;
	
	@ViewInject(R.id.lv_noScrool)
	ListViewNoScroll lv_noScrool;
	
	private QueryOrderCommentsAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_order_comment);
		ViewUtils.inject(mContext);
		setCenter_title(R.string.query_comments);
		Intent intent = getIntent();
		adapter = new QueryOrderCommentsAdapter(mContext);
		lv_noScrool.setAdapter(adapter);
		
		refundOrderUpdate(intent.getStringExtra("orderId"));
	}
	
	private void init(JsonMap<String, Object> jsonMap){
		BitmapHelper.loadImage(mContext, jsonMap.getStringNoNull("logoPath"),
				brand_logo, BitmapHelper.LoadImgOption.BrandLogo, true);
		brandName_tv.setText(jsonMap.getStringNoNull("shopName"));
		
		adapter.setDatas(jsonMap.getList_JsonMap("orderCommentVos"));
		adapter.notifyDataSetChanged();
	}
	/**
	 * 获取评论
	 */
	private void refundOrderUpdate(String orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.QueryOrder_QueryOrderComment);
		queue.setWhat(GetDataConfing.What_QueryOrder_QueryOrderComment);
		queue.setParamsNoJson(params);
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
				if (entity.what == GetDataConfing.What_QueryOrder_QueryOrderComment) {
					JsonMap<String, Object> jsonMap = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
							JsonKeys.Key_Info);
					init(jsonMap);
				}
			} else {
				ShowGetDataError.showNetError(mContext);
			}
		}
	};
}
