package com.yishanxiu.yishang.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.QueryProgressAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;
import com.yishanxiu.yishang.R;

/**
 * 查询进度
 * 
 * @author FangDongzhang 2016/7/11
 */
public class QueryProgressActivity extends BaseUIActivity {
	@ViewInject(id=R.id.listView_progress)
	private ListView listView_progress;

	private List<JsonMap<String, Object>> datas;
	private QueryProgressAdapter queryProgressAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_progress);
		setCenter_title(getResources().getString(R.string.query_progress));

		initAdapter();
		Intent intent = getIntent();
		getServerData(intent.getStringExtra("returnRefundOrderId"));

	}

	/**
	 * 适配数据
	 */
	private void initAdapter() {
		queryProgressAdapter = new QueryProgressAdapter(mContext);
		listView_progress.setAdapter(queryProgressAdapter);
	}

	/**
	 * 查询进度
	 */
	private void getServerData(String returnRefundOrderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("returnRefundOrderId", returnRefundOrderId);
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.Query_progress);
		queue.setWhat(GetDataConfing.What_Query_progress);
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
				if (entity.what == GetDataConfing.What_Query_progress) {
					datas = JsonParseHelper.getJsonMap_List_JsonMap(entity.getInfo(), JsonKeys.Key_Info);
					setData(datas);
				}
			} else {
				ShowGetDataError.showNetError(mContext);
			}
		}
	};
	/**
	 * 适配数据
	 */
	private void setData(List<JsonMap<String, Object>> datas){
		queryProgressAdapter.setDatas(datas);
		queryProgressAdapter.notifyDataSetChanged();
	}
	
	
}
