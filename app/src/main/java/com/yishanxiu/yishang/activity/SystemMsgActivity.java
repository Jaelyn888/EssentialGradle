package com.yishanxiu.yishang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.SystemAdapter;
import com.yishanxiu.yishang.adapter.SystemAdapter.OnItemClickListener;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.Utils;
import com.yishanxiu.yishang.utils.Constant.ShopCartItemCompontType;

import android.content.Intent;
import android.os.Bundle;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;

/**
 * 系统消息
 *
 * @author FangDongzhang
 *         <p>
 *         2016/8/5
 */
public class SystemMsgActivity extends BaseUIActivity {

	@ViewInject(id = R.id.ptr_list)
	private PullToRefreshListView ptr_list;
	private SystemAdapter systemAdapter;
	private List<JsonMap<String, Object>> dataList=new ArrayList<JsonMap<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_msg);
		setCenter_title(R.string.system_msg);
		systemAdapter = new SystemAdapter(mContext);
		ptr_list.setAdapter(systemAdapter);
		ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
		ptr_list.setOnRefreshListener(loadMoreListener);
		loadingToast.show();
		ptr_list.setOnItemClickListener(onItemClickListener);
		systemAdapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(int position, ShopCartItemCompontType shopCartitemType, int index) {
				 itemClick(position);
			}
		});
		getServerData(0);
	}

	private PullToRefreshBase.OnRefreshListener2<ListView> loadMoreListener = new PullToRefreshBase.OnRefreshListener2<ListView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			getServerData(1);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			getServerData(2);
		}
	};


	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
			itemClick(position);

		}
	};

	private void itemClick(int position) {
		JsonMap<String, Object> jsonMapTmp = dataList.get(position);
		JsonMap<String, Object> jsonMap = jsonMapTmp.getList_JsonMap("data").get(0);
		int pushType = jsonMapTmp.getInt("pushTypeId");
		Intent intent = new Intent();
		if (pushType == 8) {
			String expressName = jsonMapTmp.getStringNoNull("attr1");
			String expressNum = jsonMapTmp.getStringNoNull("threeId");
			intent.putExtra("logisticNumber", expressNum);
			intent.putExtra("logistic", expressName);
			intent.setClass(mContext, LogisticActivity.class);
		} else if (pushType == 9) {
			String mainId = jsonMapTmp.getStringNoNull("mainId");
			intent.putExtra("returnRefundOrderId", mainId);
			intent.setClass(mContext, QueryProgressActivity.class);
		} else {
			String orderId = jsonMap.getStringNoNull("orderId");
			intent.setClass(mContext, MyOrderDetailActivity.class);
			intent.putExtra(Constant.ORDER_ID, orderId);
		}
		jumpTo(intent);
	}

	/**
	 * 获取系统消息
	 */
	private void getServerData(int requestType) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		if (dataList.size() == 0) {
			requestType = 0;
		}
		if (requestType == 0) {
			params.put("createTime", "");
		} else if (requestType == 1) {
			params.put("createTime", dataList.get(0).getStringNoNull("createTime"));
		} else {
			params.put("createTime", dataList.get(dataList.size() - 1).getStringNoNull("createTime"));
		}
		params.put("userId", Utils.getUserId(this));
		params.put("requestType", String.valueOf(requestType));
		GetDataQueue queue = new GetDataQueue();
		queue.setTag(requestType);
		queue.setActionName(GetDataConfing.GetAction_PushMsg_QueryUserPushMsg);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_PushMsg_QueryUserPushMsg);
		getDataUtil.getData(queue);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			ptr_list.onRefreshComplete();
			if (entity.isOk()) {
				if (ShowGetDataError.isCodeSuccess(mContext, entity.getInfo())) {
					if (entity.getWhat() == GetDataConfing.What_PushMsg_QueryUserPushMsg) {
						ArrayList<JsonMap<String, Object>> temp_data = (ArrayList<JsonMap<String, Object>>) JsonParseHelper
								.getJsonMap_List_JsonMap(entity.getInfo(), JsonKeys.Key_Info);
						setAdapterData(temp_data, (Integer) entity.getTag());
						validateData();
					}
				}
			} else {
				ShowGetDataError.showNetError(mContext);
			}
			loadingToast.dismiss();
		}
	};

	private void validateData() {
		// if (dataList == null || dataList.size() == 0) {
		// ptr_list.setVisibility(View.GONE);
		// nodataLayout.setVisibility(View.VISIBLE);
		// } else {
		// ptr_list.setVisibility(View.VISIBLE);
		// nodataLayout.setVisibility(View.GONE);
		// }
	}

	private void setAdapterData(ArrayList<JsonMap<String, Object>> temp_data, int requestType) {
		if (requestType == 0) {
			if (temp_data.size() == 0) {
				ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
			} else {
				ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
				dataList.addAll(0, temp_data);
				systemAdapter.setDatas(dataList);
				systemAdapter.notifyDataSetChanged();
			}

		} else if (requestType == 1) {
			if (!temp_data.isEmpty()) {
				if (temp_data.size() >= 10) {
					dataList.clear();
				}
				dataList.addAll(0, temp_data);
				systemAdapter.setDatas(dataList);
				systemAdapter.notifyDataSetChanged();
			}
		} else {
			if (temp_data.size() == 0) {
				ptr_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
			} else {
				ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
				dataList.addAll(temp_data);
				systemAdapter.setDatas(dataList);
				systemAdapter.notifyDataSetChanged();
			}
		}
	}

}
