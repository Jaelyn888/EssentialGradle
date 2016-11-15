package com.yishanxiu.yishang.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.MyStickyListAdapter;
import com.yishanxiu.yishang.adapter.RelationProductAdapter2;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.shopmall.*;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.ModleUtils;
import com.yishanxiu.yishang.view.NoTouchLinearLayout;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Jaelyn on 16/3/30. 商品大分类,品牌点击详情
 */
public class ProductCategoryRelationActivity extends BaseUIActivity {
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

	/**
	 * 排序
	 */
	@ViewInject(id = R.id.no_touch_layout)
	private NoTouchLinearLayout no_touch_layout;

	/**
	 * 侧滑抽屉
	 */
	@ViewInject(id = R.id.drawer_layout)
	private DrawerLayout drawer_layout;
	@ViewInject(id = R.id.left_drawer)
	private LinearLayout left_drawer;
	//
	@ViewInject(id = R.id.stick_list)
	private StickyListHeadersListView stick_list;
	private MyStickyListAdapter myStickyListAdapter;

	@ViewInject(id = R.id.btn_cancel, click = "btn_cancel_click")
	private Button btn_cancel;

	@ViewInject(id = R.id.btn_confirm, click = "btn_confirm_click")
	private Button btn_confirm;

	private ArrayList<FilterProductSelectionDataModel> leftDataList = new ArrayList<>();
	private String sBrandIds = "";
	private String sClassIds = "";
	/**
	 * 商品
	 */
	private List<ProductInfoModel> dataList = new ArrayList<>();
	private RelationProductAdapter2 relationProductAdapter;

	/**
	 * 排序
	 */
	//private ArrayList<JsonMap<String, Object>> sortList = new ArrayList<JsonMap<String, Object>>();

	/**
	 * 商品大分类的id
	 */
	private String categoryId;
	/**
	 * 排序方式
	 */
	private String sortId;
	//自营商品10 0
	private String isSelfProduct = "0";
	private String isDiscount = "0";
	/**
	 * 界面跳转来源
	 */
	private int flag = 1;
	private int nPage = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productcategory_relationproduct_layout);
		setShowLeftMenu(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (drawer_layout.isDrawerVisible(left_drawer)) {
					drawer_layout.closeDrawer(left_drawer);
				} else {
					drawer_layout.openDrawer(left_drawer);
				}
			}
		});
		ProductCategoryModel productCategoryModel= (ProductCategoryModel) getIntent().getSerializableExtra(ExtraKeys.Key_Msg2);
		categoryId=productCategoryModel.getCategoryId();
		setCenter_title(productCategoryModel.getCategoryName());
		flag = getIntent().getIntExtra(ExtraKeys.Key_Msg1, 1);
		setBtn_menu(R.drawable.search_white, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				jumpToSearchProduct();
				// sortStatus(false);

			}
		}, R.drawable.shopcart_white, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				jumpToShopCart();
				// sortStatus(false);
			}
		});

		self_product_cb.setOnClickListener(onCLickListener);
		discount_cb.setOnClickListener(onCLickListener);

		ptr_gv.setOnRefreshListener(list_loadMoreListener);
		ptr_gv.setOnItemClickListener(onItemClickListener);
		relationProductAdapter = new RelationProductAdapter2(mContext);
		relationProductAdapter.setShowDiscri(true);
		ptr_gv.setAdapter(relationProductAdapter);
		loadingToast.show();
		getClassfyDataById();
		sortId = "3";
		sort_tv.setSelected(true);
		price_tv.setSelected(false);
		pop_iv.setImageResource(R.drawable.pop_gray);
		getServiceData(true);

		initSortView();
	}

	/**
	 * 初始化排序view和数据
	 */
	private void initSortView() {
/*
		ListView listView = new ListView(mContext);
		listView.setDivider(mContext.getResources().getDrawable(R.drawable.hor_line));
		int pad = getResources().getDimensionPixelSize(R.dimen.common_margin);
		listView.setPadding(pad, 0, pad, 0);
		listView.setBackgroundResource(R.drawable.cus_grey_border_frame);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// sortStatus(false);
				sort_tv.setText(sortList.get(position).getStringNoNull("categoryName"));
				String tempSortId = sortList.get(position).getString("sortId");
				if (!tempSortId.endsWith(sortId)) {
					request_type = 1;
					getServiceData(true, request_type, "1");
				}
			}
		});
		MenuAdapter menuAdapter = new MenuAdapter(mContext, sortList);
		listView.setAdapter(menuAdapter);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				MyApplication.getInstance().getScreenWidth() / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
		no_touch_layout.addView(listView, params);*/
		// no_touch_layout.setTouchOutSideCancel(true);

		// sort_tv.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// getServiceData(true,1);
		// }
		// });
	}

	/**
	 * 获取左侧的侧滑数据
	 */
	private void getClassfyDataById() {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("categoryId", categoryId);

		GetDataQueue getDataQueue = new GetDataQueue();
		if (flag == 1) {
			getDataQueue.setActionName(GetDataConfing.GetAction_GetClassfyDataByProductCategoryId);
		} else if (flag == 2) {
			getDataQueue.setActionName(GetDataConfing.GetAction_GetClassfyDataByBrandId);
		}
		getDataQueue.setWhat(GetDataConfing.What_GetClassfyDataByProductCategoryId);
		getDataQueue.setParamsNoJson(hashMap);

		getDataQueue.setCallBack(callBack);
		getDataUtil.getData(getDataQueue);
	}

	/**
	 * 排序点击
	 *
	 * @param view
	 */
	public void sort_tv_click(View view) {
		sortId = "3";
		sort_tv.setSelected(true);
		price_tv.setSelected(false);
		pop_iv.setImageResource(R.drawable.pop_gray);
		getServiceData(true);
	}

	public void price_ll_click(View view) {
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
			loadingToast.show();
			getServiceData(true);
			// sortStatus(false);
		}
	};

	/**
	 * 商品 item的点击
	 */
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
	private PullToRefreshBase.OnRefreshListener2<GridView> list_loadMoreListener = new PullToRefreshBase.OnRefreshListener2<GridView>() {
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
	 * 获取网络数据 private int nPage = 0; /**
	 *
	 * @param chearAllData
	 */
	private void getServiceData(boolean chearAllData) {
		if (chearAllData) {
			nPage = 1;
			ptr_gv.setMode(PullToRefreshBase.Mode.BOTH);
		}
		HashMap<String, Object> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("pageIndex", String.valueOf(nPage));
		params.put("type", sortId);
		params.put("own", isSelfProduct);
		params.put("isDiscount", isDiscount);
		params.put("brandIds", sBrandIds);
		params.put("productTypeIds", sClassIds);
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_SelectCategoryProductList);
		queue.setWhat(GetDataConfing.What_SelectCategoryProductList);
		queue.setCallBack(callBack);

		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 设置适配器数据
	 *
	 * @param data
	 */
	private void setAdapterData(List<ProductInfoModel> data) {
		if (data.size() < 10) {
			ptr_gv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		} else {
			ptr_gv.setMode(PullToRefreshBase.Mode.BOTH);
		}
		if (nPage == 1) {
			this.dataList = data;
			relationProductAdapter.setDatas(data);
			relationProductAdapter.setShowDiscri(true);
			ptr_gv.setAdapter(relationProductAdapter);
		} else {
			this.dataList.addAll(data);
			relationProductAdapter.notifyDataSetChanged();
		}
		nPage++;
	}

	/**
	 * 获取服务器数据的返回信息
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
				if ( entity.getWhat() == GetDataConfing.What_SelectCategoryProductList) {
					TypeToken<BaseResponse<List<ProductInfoModel>>> typeToken=new TypeToken<BaseResponse<List<ProductInfoModel>>>(){};
					BaseResponse<List<ProductInfoModel>> baseResponse=new ModleUtils().mapTo(entity,typeToken);
					if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
						List<ProductInfoModel> data = baseResponse.getInfo();
						setAdapterData(data);
					}
				} else if (entity.getWhat() == GetDataConfing.What_GetClassfyDataByProductCategoryId) {
					TypeToken<BaseResponse<ProductCategoryLeftDataModel>> typeToken=new TypeToken<BaseResponse<ProductCategoryLeftDataModel>>(){};
					BaseResponse<ProductCategoryLeftDataModel> baseResponse=new ModleUtils().mapTo(entity,typeToken);
					if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
						List<BrandModel> brandsList = baseResponse.getInfo().getBrandList();
						List<ProductTypeModel> classfyList = baseResponse.getInfo().getProductTypeList();
						showLeftData(brandsList, classfyList);
					}
				}

			loadingToast.dismiss();
			ptr_gv.onRefreshComplete();
		}
	};

	//侧滑数据解析
	private void showLeftData(List<BrandModel> brandsList, List<ProductTypeModel> classfyList) {

		if (classfyList==null||classfyList.size() == 0) {
		} else {
			for (ProductTypeModel productTypeModel : classfyList) {
				FilterProductSelectionDataModel filterProductSelectionDataModel=new FilterProductSelectionDataModel();
				filterProductSelectionDataModel.setItemType(Constant.ITEM_TYPE_CLASSFY);
				filterProductSelectionDataModel.setMainId(productTypeModel.getProductTypeId());
				filterProductSelectionDataModel.setChecked(false);
				filterProductSelectionDataModel.setName(productTypeModel.getProductypeName());
				leftDataList.add(filterProductSelectionDataModel);
			}
		}

		if (brandsList==null||brandsList.size() == 0) {
		} else {
			for (BrandModel brandModel : brandsList) {
				FilterProductSelectionDataModel filterProductSelectionDataModel=new FilterProductSelectionDataModel();
				filterProductSelectionDataModel.setItemType(Constant.ITEM_TYPE_BRAND);
				filterProductSelectionDataModel.setMainId(brandModel.getBrandId());
				filterProductSelectionDataModel.setChecked(false);
				filterProductSelectionDataModel.setName(brandModel.getBrandName());
				leftDataList.add(filterProductSelectionDataModel);
			}
		}

		myStickyListAdapter = new MyStickyListAdapter(mContext);
		myStickyListAdapter.setData(leftDataList);
		stick_list.setAdapter(myStickyListAdapter);
		myStickyListAdapter.notifyDataSetChanged();
	}

	/**
	 * 确定按钮点击
	 *
	 * @param view
	 */
	public void btn_confirm_click(View view) {
		sBrandIds = "";
		sClassIds = "";
		drawer_layout.closeDrawer(left_drawer);
		if (!leftDataList.isEmpty()) {
			leftDataList = (ArrayList<FilterProductSelectionDataModel>) myStickyListAdapter.getDatas();
			for (FilterProductSelectionDataModel dataModel : leftDataList) {
				int itemType = dataModel.getItemType();
				if (dataModel.isChecked()) {
					if (itemType == Constant.ITEM_TYPE_CLASSFY) {
						String productTypeId = dataModel.getMainId();
						sClassIds += productTypeId + ",";
					} else if (itemType == Constant.ITEM_TYPE_BRAND) {
						String brandId = dataModel.getMainId();
						sBrandIds += brandId + ",";
					}
				}
			}
			if (sBrandIds.length() > 0) {
				sBrandIds = sBrandIds.substring(0, sBrandIds.length() - 1);
			}
			if (sClassIds.length() > 0) {
				sClassIds = sClassIds.substring(0, sClassIds.length() - 1);
			}


		}
		getServiceData(true);
	}

	//取消按钮点击
	public void btn_cancel_click(View view) {
		drawer_layout.closeDrawer(left_drawer);
	}

	@Override
	public void onBackPressed() {
		// 退出activity前关闭菜单
		if (no_touch_layout.getVisibility() == View.VISIBLE) {
			no_touch_layout.setVisibility(View.GONE);
		} else if (drawer_layout.isDrawerOpen(left_drawer)) {
			drawer_layout.closeDrawer(left_drawer);
		} else {
			super.onBackPressed();
		}
	}

}