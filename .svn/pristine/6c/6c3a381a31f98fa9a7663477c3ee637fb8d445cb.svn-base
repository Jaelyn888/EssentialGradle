package com.yishanxiu.yishang.activity;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.ProductCommentsAdapter;
import com.yishanxiu.yishang.getdata.*;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.shopmall.ProductCommentModel;
import com.yishanxiu.yishang.utils.Constant;

import com.yishanxiu.yishang.utils.ModleUtils;
import net.tsz.afinal.annotation.view.ViewInject;
import com.yishanxiu.yishang.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaelyn on 16/3/3. 商品评论
 */
public class ProductCommentsActivity extends BaseUIActivity {
	// 商品评论
	@ViewInject(id = R.id.ptr_list)
	private PullToRefreshListView ptr_list;

	private ProductCommentsAdapter goodsCommentsAdapter;
	private List<ProductCommentModel> productCommentModelList = new ArrayList<>();
	private String goodsId;// 发现id

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_comments_layout);
		setCenter_title(getResources().getString(R.string.pro_comments));
		goodsCommentsAdapter = new ProductCommentsAdapter(this);
		goodsCommentsAdapter.setDatas(productCommentModelList);
		ptr_list.setAdapter(goodsCommentsAdapter);

		ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
		ptr_list.setOnRefreshListener(loadMoreListener);

		goodsId = getIntent().getStringExtra(Constant.ID);
		loadingToast.show();
		getServerData(0);
	}

	/**
	 * 刷新数据
	 */
	private PullToRefreshBase.OnRefreshListener2 loadMoreListener = new PullToRefreshBase.OnRefreshListener2() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase refreshView) {
			getServerData(1);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase refreshView) {
			getServerData(2);
		}
	};

	/**
	 * 获取服务器端数据 { "categoryId":0,"time": "","requestType":0}
	 *
	 * @param requestType 请求类型
	 */
	public void getServerData(int requestType) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("productId", goodsId);
		if (productCommentModelList.size() == 0) {
			requestType = 0;
		}
		if (requestType == 0) {
			params.put("createTime", "");
		} else if (requestType == 1) {
			params.put("createTime", productCommentModelList.get(0).getCreateTime());
		} else {
			params.put("createTime", productCommentModelList.get(productCommentModelList.size() - 1).getCreateTime());
		}
		params.put("requestType", String.valueOf(requestType));
		GetDataQueue queue = new GetDataQueue();
		queue.setTag(requestType);

		queue.setActionName(GetDataConfing.GetAction_GetProductCommentsList);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_GetProductCommentsList);
		getDataUtil.getData(queue);
	}

	private void validateData() {
		// if (commentsDatas == null || commentsDatas.isEmpty()) {
		// goods_comments_xlv.setVisibility(View.GONE);
		// nodataLayout.setVisibility(View.VISIBLE);
		// } else {
		// goods_comments_xlv.setVisibility(View.VISIBLE);
		// nodataLayout.setVisibility(View.GONE);
		// }
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {

		@Override
		public void onLoaded(GetDataQueue entity) {
			ptr_list.onRefreshComplete();
			loadingToast.dismiss();
			if (entity.getWhat() == GetDataConfing.What_GetProductCommentsList) {
				TypeToken<BaseResponse<List<ProductCommentModel>>> typeToken = new TypeToken<BaseResponse<List<ProductCommentModel>>>() {};
				BaseResponse<List<ProductCommentModel>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					List<ProductCommentModel> temp_data = baseResponse.getInfo();
					setAdapterData(temp_data, (Integer) entity.getTag());
					validateData();
				}
			}
		}
	};

	private void setAdapterData(List<ProductCommentModel> temp_data, int requestType) {
		if (requestType == 0) {
			if (temp_data.size() == 0) {
				ptr_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
			} else {
				ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
			}

			if (!temp_data.isEmpty()) {
				productCommentModelList.addAll(0, temp_data);
				goodsCommentsAdapter.setDatas(productCommentModelList);
			}

		} else if (requestType == 1) {
			if (!temp_data.isEmpty()) {
				productCommentModelList.addAll(0, temp_data);
				goodsCommentsAdapter.setDatas(productCommentModelList);
			}
		} else {
			if (temp_data.size() == 0) {
				ptr_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
			} else {
				ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
			}

			if (!temp_data.isEmpty()) {
				productCommentModelList.addAll(temp_data);
				goodsCommentsAdapter.setDatas(productCommentModelList);
			}
		}
	}

}