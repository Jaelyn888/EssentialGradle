package com.yishanxiu.yishang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.shopmall.ProductCategoryModel;
import com.yishanxiu.yishang.utils.ModleUtils;
import net.tsz.afinal.json.JsonMap;
import com.yishanxiu.yishang.activity.ProductCategoryRelationActivity;
import com.yishanxiu.yishang.adapter.ProductCategoryAdapter;
import com.yishanxiu.yishang.getdata.*;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;

import net.tsz.afinal.json.JsonParseHelper;
import com.yishanxiu.yishang.R;

import java.util.*;

/**
 * Created by Jaelyn on 2015/9/7. 商品大分类
 */
public class ProductCategoryFragment extends LazyFragment {

	private PullToRefreshListView ptr_list;

	private ProductCategoryAdapter goodsCategoryAdapter;

	/**
	 * 当前展示的数据
	 */
	private List<ProductCategoryModel> dataList;

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.product_category_fragment);
		ptr_list = (PullToRefreshListView) getContentView().findViewById(R.id.ptr_list);
		ptr_list.setOnRefreshListener(xListLoadMore);
		ptr_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		ptr_list.setOnItemClickListener(onItemClickListener);
		loadingToast.show();
		getDataService(true);
	}

	/**
	 * item 点击
	 */
	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (position > 0) {
				ProductCategoryModel productCategoryModel = dataList.get(position - 1);
				Intent intent = new Intent(activity, ProductCategoryRelationActivity.class);
				intent.putExtra(ExtraKeys.Key_Msg1, 1);
				intent.putExtra(ExtraKeys.Key_Msg2, productCategoryModel);

				activity.jumpTo(intent);
			}
		}
	};

	private void getDataService(boolean chearAllData) {
		Map<String, Object> params = new HashMap<>();
		params.put("isDeleted", "0");
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_SelectGoodsCategoryList);
		queue.setCallBack(callBack);
		queue.setParamsNoJson(params);
		queue.setWhat(GetDataConfing.What_SelectGoodsCategoryList);
		getDataUtil.getData(queue);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			ptr_list.onRefreshComplete();
			if (entity.getWhat() == GetDataConfing.What_SelectGoodsCategoryList) {
				TypeToken<BaseResponse<List<ProductCategoryModel>>> typeToken=new TypeToken<BaseResponse<List<ProductCategoryModel>>>(){};
				BaseResponse<List<ProductCategoryModel>> baseResponse=new ModleUtils().mapTo(entity,typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					List<ProductCategoryModel> data = baseResponse.getInfo();
					setAdatperlist(data);
				}
			}

			validateData();
		}
	};

	private void validateData() {
		if (dataList == null || dataList.isEmpty()) {
			ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
		} else {
			ptr_list.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
		}
	}

	private void setAdatperlist(List<ProductCategoryModel> data) {
		this.dataList = data;
		goodsCategoryAdapter = new ProductCategoryAdapter(activity);
		 goodsCategoryAdapter.setData(dataList);
		ptr_list.setAdapter(goodsCategoryAdapter);
		goodsCategoryAdapter.notifyDataSetChanged();
	}

	private PullToRefreshBase.OnRefreshListener2<ListView> xListLoadMore = new PullToRefreshBase.OnRefreshListener2<ListView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			getDataService(true);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			getDataService(false);
		}
	};

}
