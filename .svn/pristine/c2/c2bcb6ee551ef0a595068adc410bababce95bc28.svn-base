package com.yishanxiu.yishang.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yishanxiu.yishang.adapter.RelationProductAdapter2;
import com.yishanxiu.yishang.getdata.*;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ModleUtils;
import com.yishanxiu.yishang.utils.Utils;
import com.yishanxiu.yishang.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaelyn on 16/4/19. 收藏商品
 */
public class CollectionProductsFragment extends LazyFragment {
	private PullToRefreshGridView ptr_gv;

	/**
	 * 商品
	 */
	private List<ProductInfoModel> dataList = new ArrayList<>();
	private RelationProductAdapter2 relationProductAdapter;

	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			getServiceData(0);
		}
	};

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.collection_product_layout);
	}

	@Override
	protected void onFragmentStartLazy() {
		super.onFragmentStartLazy();
		ptr_gv = (PullToRefreshGridView) findViewById(R.id.ptr_gv);
		IntentFilter intentFilter = new IntentFilter(Constant.ACT_PRODUCT_COLLECTION_CHANGE);
		activity.registerReceiver(broadcastReceiver, intentFilter);
		ptr_gv.setOnRefreshListener(list_loadMoreListener);
		ptr_gv.setOnItemClickListener(onItemClickListener);
		relationProductAdapter = new RelationProductAdapter2(activity);
		relationProductAdapter.setShowDiscri(true);
		ptr_gv.setAdapter(relationProductAdapter);
		ptr_gv.setMode(PullToRefreshBase.Mode.BOTH);
		getServiceData(0);
	}

	/**
	 * 商品 item的点击
	 */
	AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ProductInfoModel productInfoModel = dataList.get(position);
			activity.seeProductDetail(productInfoModel);
		}
	};

	/**
	 * Gridview
	 */
	PullToRefreshBase.OnRefreshListener2 list_loadMoreListener = new PullToRefreshBase.OnRefreshListener2<GridView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
			getServiceData(1);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
			getServiceData(2);
		}
	};

	/**
	 * 获取网络数据 private int nPage = 0; /**
	 *
	 * @param requestType
	 */
	private void getServiceData(int requestType) {
		HashMap<String, Object> params = new HashMap<>();

		if (dataList.size() == 0) {
			requestType = 0;
		}
		if (requestType == 0) {
			params.put("createTime", "");
		} else if (requestType == 1) {
			params.put("createTime", dataList.get(0).getCreateTime());
		} else {
			params.put("createTime", dataList.get(dataList.size() - 1).getCreateTime());
		}
		params.put("userId", Utils.getUserId(activity));
		params.put("collectionType", Constant.CollectionType.Product.ordinal() + "");
		params.put("requestType", String.valueOf(requestType));
		GetDataQueue queue = new GetDataQueue();
		queue.setTag(requestType);
		queue.setActionName(GetDataConfing.GetAction_queryCollectionByType);

		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_queryCollectionByType);
		getDataUtil.getData(queue);
	}

	/**
	 * 设置适配器数据
	 */
	private void setAdapterData(List<ProductInfoModel> temp_data, int requestType) {
		if (requestType == 0) {
			dataList = temp_data;
			relationProductAdapter.setDatas(dataList);

		} else if (requestType == 1) {
			if (!temp_data.isEmpty()) {
				dataList.addAll(0, temp_data);
				relationProductAdapter.setDatas(dataList);
			}
		} else {
			if (temp_data.size() < 10) {
				ptr_gv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
			} else {
				ptr_gv.setMode(PullToRefreshBase.Mode.BOTH);
			}
			dataList.addAll(temp_data);
			relationProductAdapter.setDatas(dataList);
		}

		if (dataList.isEmpty()) {
			toast.showToast(R.string.no_collection_product_msg);
		}
	}

	/**
	 * 获取服务器数据的返回信息
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			if (entity.what == GetDataConfing.What_queryCollectionByType) {
				TypeToken<BaseResponse<List<ProductInfoModel>>> typeToken=new TypeToken<BaseResponse<List<ProductInfoModel>>>(){};
				BaseResponse<List<ProductInfoModel>> baseResponse=new ModleUtils().mapTo(entity,typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
						List<ProductInfoModel> data = baseResponse.getInfo();
						setAdapterData(data, (Integer) entity.getTag());
				}
			}
			ptr_gv.onRefreshComplete();
		}
	};

}