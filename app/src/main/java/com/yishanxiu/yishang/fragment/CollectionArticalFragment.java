package com.yishanxiu.yishang.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.ArticalDetailActivity;
import com.yishanxiu.yishang.adapter.ArticleAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.article.ArticleModel;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.ModleUtils;
import com.yishanxiu.yishang.utils.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;

/**
 * Created by Jaelyn on 2015/9/6 0006. 页面需要获取屏幕的宽高
 * <p>
 * Update @author FangDongzhang 2016/7/22
 */
public class CollectionArticalFragment extends LazyFragment {

	private PullToRefreshListView ptr_list;
	/**
	 * 信息数据
	 */
	private List<ArticleModel> dataList = new ArrayList<>();
	private ArticleAdapter articleAdapter;

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			getServerData(0);
		}
	};

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.artical_layout);
		ptr_list = (PullToRefreshListView) findViewById(R.id.ptr_list);
		IntentFilter intentFilter = new IntentFilter(Constant.ACT_ARTICLE_COLLECTION_CHANGE);
		activity.registerReceiver(broadcastReceiver, intentFilter);
		articleAdapter = new ArticleAdapter(activity);
		ptr_list.setAdapter(articleAdapter);
		ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
		ptr_list.setOnRefreshListener(loadMoreListener);
		ptr_list.setOnItemClickListener(onItemClickListener);
		loadingToast.show();
		getServerData(0);
	}


	/**
	 * item 点击监听
	 */
	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {
			ArticleModel data = null;
			if (itemPosition > 0) {
				data = dataList.get(itemPosition - 1);
			}
			if (data != null) {
				Intent intent = new Intent(activity, ArticalDetailActivity.class);
				intent.putExtra(ExtraKeys.Key_Msg1,data);
				activity.jumpTo(intent, false);
			}
		}
	};

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

	/**
	 * 获取服务器端数据 { "categoryId":0,"time": "","requestType":0}
	 *
	 * @param requestType 请求类型
	 */
	public void getServerData(int requestType) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		if (dataList.size() == 0) {
			requestType = 0;
		}
		// 我的收藏 userId=1&collectionType=5&createTime=1465954497253&requestType=0
		if (requestType == 0) {
			params.put("createTime", "");
		} else if (requestType == 1) {
			params.put("createTime", dataList.get(0).getCreateTime());
		} else {
			params.put("createTime", dataList.get(dataList.size() - 1).getCreateTime());
		}
		params.put("userId", Utils.getUserId(activity));
		params.put("collectionType", "0");

		params.put("requestType", String.valueOf(requestType));
		GetDataQueue queue = new GetDataQueue();
		queue.setTag(requestType);
		queue.setActionName(GetDataConfing.GetAction_queryCollectionByType);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_GetArticals);
		getDataUtil.getData(queue);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			ptr_list.onRefreshComplete();
			if (entity.getWhat() == GetDataConfing.What_GetArticals) {
				TypeToken<BaseResponse<List<ArticleModel>>> typeToken = new TypeToken<BaseResponse<List<ArticleModel>>>() {};
				BaseResponse<List<ArticleModel>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					List<ArticleModel> temp_data = baseResponse.getInfo();
					setAdapterData(temp_data, (Integer) entity.getTag());
					validateData();
				}
			}
			activity.loadingToast.dismiss();
		}

	};

	/**
	 * 校验数据 显示无数据等 是否请求正常 网络错误且数据为空的话显示网络异常界面 提供刷新的接口
	 *
	 * @param
	 */
	private void validateData() {
		// if (dataList == null || dataList.size() == 0) {
		// ptr_list.setVisibility(View.GONE);
		// nodataLayout.setVisibility(View.VISIBLE);
		// } else {
		// ptr_list.setVisibility(View.VISIBLE);
		// nodataLayout.setVisibility(View.GONE);
		// }
	}

	private void setAdapterData(List<ArticleModel> temp_data, int requestType) {
		if (requestType == 0) {
			ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
			dataList = temp_data;
			articleAdapter.setDatas(dataList);
			articleAdapter.notifyDataSetChanged();
		} else if (requestType == 1) {
			if (!temp_data.isEmpty()) {
				dataList.addAll(0, temp_data);
				articleAdapter.setDatas(dataList);
				articleAdapter.notifyDataSetChanged();
			}
		} else {
			if (temp_data.size() == 0) {
				ptr_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
			} else {
				ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
				dataList.addAll(temp_data);
				articleAdapter.setDatas(dataList);
				articleAdapter.notifyDataSetChanged();
			}
		}

		if (dataList.isEmpty()) {
			toast.showToast(R.string.no_collection_atticle_msg);
		}
	}
}