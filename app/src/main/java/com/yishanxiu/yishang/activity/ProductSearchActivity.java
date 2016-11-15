package com.yishanxiu.yishang.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.adapter.RelationProductAdapter2;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;

import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;
import com.yishanxiu.yishang.utils.ModleUtils;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaelyn on 16/4/1. 搜索商品
 */
public class ProductSearchActivity extends BaseActivity {

	/**
	 * 搜索框的隐藏与显示
	 */
	@ViewInject(id = R.id.linearlayout_search)
	private LinearLayout linearlayout_search;
	/**
	 * 商品筛选
	 */
	@ViewInject(id = R.id.select_pro_llt)
	private LinearLayout select_pro_llt;
	/**
	 * 搜索商品
	 */
	@ViewInject(id = R.id.search_ed)
	private EditText search_ed;
	@ViewInject(id = R.id.do_search_iv, click = "do_search_iv_click")
	private ImageView do_search_iv;

	/**
	 * 清楚搜索内容
	 */
	@ViewInject(id = R.id.clear_search_iv, click = "clear_search_iv_click")
	private ImageView clear_search_iv;

	/**
	 * 搜索取消
	 */
	@ViewInject(id = R.id.tv_search_cancel, click = "tv_search_cancel_click")
	private TextView tv_search_cancel;

	@ViewInject(id = R.id.sort_tv)
	private TextView sort_tv;
	@ViewInject(id = R.id.price_tv)
	private TextView price_tv;
	@ViewInject(id = R.id.pop_iv)
	private ImageView pop_iv;
	@ViewInject(id = R.id.sort_tv_layout, click = "sort_tv_click")
	private LinearLayout sort_tv_layout;
	@ViewInject(id = R.id.price_ll, click = "price_ll_click")
	private LinearLayout price_ll;
	// @ViewInject(id = R.id.discount_cb)
	// private CheckBox discount_cb;
//	 @ViewInject(id = R.id.self_product_cb)
//	 private CheckBox self_product_cb;
	@ViewInject(id = R.id.discount_cb)
	private LinearLayout discount_cb;
	@ViewInject(id = R.id.self_product_cb)
	private LinearLayout self_product_cb;

	@ViewInject(id = R.id.ptr_gv)
	private PullToRefreshGridView ptr_gv;

	//自营商品10 0
	private String isSelfProduct = "0";
	private String isDiscount = "0";
	/**
	 * 界面跳转来源
	 */
	private int nPage = 0;

	/**
	 * 搜索关键字
	 */
	private String searchKey;
	private String sortId = "0";
	/**
	 * 商品
	 */
	private List<ProductInfoModel> dataList = new ArrayList<ProductInfoModel>();
	private RelationProductAdapter2 relationProductAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_search_layout);

		select_pro_llt.setVisibility(View.INVISIBLE);
		self_product_cb.setOnClickListener(onCLickListener);
		discount_cb.setOnClickListener(onCLickListener);

		ptr_gv.setOnRefreshListener(list_loadMoreListener);
		ptr_gv.setOnItemClickListener(onItemClickListener);
		relationProductAdapter = new RelationProductAdapter2(mContext);
		relationProductAdapter.setShowDiscri(true);
		ptr_gv.setAdapter(relationProductAdapter);

		linearlayout_search.setVisibility(View.VISIBLE);
		search_ed.setOnEditorActionListener(editorActionListener);
		sortId = "3";
		sort_tv.setSelected(true);
		price_tv.setSelected(false);
		pop_iv.setImageResource(R.drawable.pop_gray);
	}

	/**
	 * 搜索ime的点击
	 */
	private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				searchKey = search_ed.getText().toString();
				loadingToast.show();
				getServiceData(true);
				return true;

			}
			return false;
		}
	};

	public void clear_search_iv_click(View view) {
		search_ed.setText("");
	}

	/**
	 * 搜索
	 *
	 * @param view
	 */
	public void do_search_iv_click(View view) {
		searchKey = search_ed.getText().toString();
		loadingToast.show();
		nPage = 0;
		getServiceData(true);

	}

	/**
	 * 取消搜索
	 */
	public void tv_search_cancel_click(View view) {
		finish();
	}

	AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ProductInfoModel productInfoModel = dataList.get(position);
			seeProductDetail(productInfoModel);
		}
	};

	/**
	 * Gridview
	 */
	PullToRefreshBase.OnRefreshListener2 list_loadMoreListener = new PullToRefreshBase.OnRefreshListener2<GridView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
			getServiceData(true);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
			getServiceData(false);
		}
	};


	/**
	 * @param chearAllData
	 */
	private void getServiceData(boolean chearAllData) {
		if (chearAllData) {
			nPage = 0;
			ptr_gv.setMode(PullToRefreshBase.Mode.BOTH);
		}
		HashMap<String, Object> params = new HashMap<>();
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_SearchProductList);
		queue.setWhat(GetDataConfing.what_SearchProductList);
		params.put("type", sortId);
		params.put("own", isSelfProduct);
		params.put("isDiscount", isDiscount);
		params.put("productName", searchKey);
		params.put("pageIndex", String.valueOf(nPage + 1));
		params.put("brandIds", "");
		params.put("productTypeIds", "");
		queue.setCallBack(callBack);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	private void setAdapterData(List<ProductInfoModel> data) {
		if (nPage == 0) {
			if (data == null || data.size() == 0) {
				toast.showToast(R.string.no_relation_goods_data);
			}
			this.dataList = data;
			relationProductAdapter.setDatas(dataList);
			relationProductAdapter.setShowDiscri(true);
			ptr_gv.setAdapter(relationProductAdapter);
		} else {
			this.dataList.addAll(data);
			relationProductAdapter.setDatas(dataList);
			relationProductAdapter.notifyDataSetChanged();
		}
		nPage++;
		if (data.size() > 0) {
			select_pro_llt.setVisibility(View.VISIBLE);
		}
		if (data.size() < 10) {
			ptr_gv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		} else {
			ptr_gv.setMode(PullToRefreshBase.Mode.BOTH);
		}
	}

	/**
	 * 获取服务器数据的返回信息
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			if (entity.getWhat() == GetDataConfing.what_SearchProductList) {
				TypeToken<BaseResponse<List<ProductInfoModel>>> typeToken = new TypeToken<BaseResponse<List<ProductInfoModel>>>() {
				};
				BaseResponse<List<ProductInfoModel>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					List<ProductInfoModel> data = baseResponse.getInfo();
					setAdapterData(data);
				}
			}
			loadingToast.dismiss();
			ptr_gv.onRefreshComplete();
		}
	};

	/**
	 * 排序点击
	 *
	 * @param view
	 */
	public void sort_tv_click(View view) {
		if (dataList.isEmpty()) {
			return;
		}
		sortId = "3";
		sort_tv.setSelected(true);
		price_tv.setSelected(false);
		pop_iv.setImageResource(R.drawable.pop_gray);
		getServiceData(true);
	}

	public void price_ll_click(View view) {
		// if (no_touch_layout.getVisibility() == View.VISIBLE) {
		if (dataList.isEmpty()) {
			return;
		}
		if (sortId.equalsIgnoreCase("3") || sortId.equalsIgnoreCase("2")) {
			sort_tv.setSelected(false);
			pop_iv.setImageResource(R.drawable.up_balck);
			sortId = "1";
			getServiceData(true);
		} else if (sortId.equalsIgnoreCase("1")) {
			pop_iv.setImageResource(R.drawable.down_black);
			sortId = "2";
			getServiceData(true);
		}
		price_tv.setSelected(true);
	}


	/**
	 * 自营 checkbox 状态改变的监听
	 */
	View.OnClickListener onCLickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {

			boolean bool = view.isSelected();
			view.setSelected(!bool);
			int id = view.getId();

			if (bool) {
				if (id == R.id.discount_cb) {
					isDiscount = "0";
				} else {
					isSelfProduct = "0";
				}

			} else {
				if (id == R.id.discount_cb) {
					isDiscount = "1";
				} else {
					isSelfProduct = "10";
				}
			}
			getServiceData(true);
			// sortStatus(false);
		}
	};

}