package com.yishanxiu.yishang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.hyphenate.util.EasyUtils;
import com.umeng.socialize.UMShareAPI;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.RelationProductAdapter2;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.recommend.RecommendDetailResponse;
import com.yishanxiu.yishang.model.recommend.RecommendModel;
import com.yishanxiu.yishang.model.recommend.RecommendProModel;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;
import com.yishanxiu.yishang.model.user.CollectionResponse;
import com.yishanxiu.yishang.utils.*;
import com.yishanxiu.yishang.view.GridViewNoScroll;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FangDongzhang on 2016/9/9 0016. 推荐详情
 */
public class RecommendDerailActivity extends BaseUIActivity {
	/**
	 * 刷新的
	 */
	@ViewInject(id = R.id.ptr_scrollview)
	private PullToRefreshScrollView ptr_scrollview;
	@ViewInject(id = R.id.xgv)
	private GridViewNoScroll xgv;

	@ViewInject(id = R.id.scroll_view_head)
	private LinearLayout scroll_view_head;
	//head view
	@ViewInject(id = R.id.shop_bg_iv)
	private ImageView shop_bg_iv;// 商品图片
	@ViewInject(id = R.id.shop_detail_discri_tv)
	private TextView shop_detail_discri_tv;

	private List<ProductInfoModel> dataList = new ArrayList<>();
	private String recommendId = ""; // 推荐的id

	private int nPage = 0;
	/**
	 * 下拉刷新Gridview适配器
	 */
	private RelationProductAdapter2 relationProductAdapter;
	private RecommendModel recommendModel; // 店铺信息

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend_detial);
		setBtn_backListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (EasyUtils.isSingleActivity(mContext)) {
					Intent intent = new Intent(mContext, SplashActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					jumpTo(intent);
				}
				finish();
			}
		});

		setBtn_menu(/*R.drawable.search_white, new View.OnClickListener() {
	        @Override
			public void onClick(View view) {
				jumpToSearchProduct();
			}
		},*/ R.drawable.share_white, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				share();
			}
		});
		Intent intent = getIntent();
		if (intent != null) {
			recommendModel = (RecommendModel) intent.getSerializableExtra(ExtraKeys.Key_Msg1);
			recommendId = recommendModel.getRecommendId();
			if (TextUtils.isEmpty(recommendId)) {
				toast.showToast(R.string.params_error);
				if (EasyUtils.isSingleActivity(mContext)) {
					Intent newIntent = new Intent(mContext, SplashActivity.class);
					newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					jumpTo(newIntent);
				}
				finish();
			}
		} else {
			finish();
		}

		initHead();
		addHeadView();
		getServerData();
	}

	private void initHead() {
		setCenter_title(recommendModel.getRecommendTitle());
		String backImge = recommendModel.getMainPicPath(); // 背景大图
		BitmapHelper.loadImage(mContext, backImge, shop_bg_iv, BitmapHelper.LoadImgOption.Rectangle);
		shop_detail_discri_tv.setText(recommendModel.getRecommendDescription());
	}

	/**
	 * 拉取店铺信息
	 */
	private void getServerData() {
		loadingToast.show();
		Map<String, Object> params = new HashMap<>();
		if (Utils.isHasLogin(this)) {
			params.put("userId", Utils.getUserId(this));
		}
		params.put("recommendId", recommendId);
		params.put("pageIndex", String.valueOf(nPage + 1));
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Recommend_QueryProductDetails);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_Recommend_QueryProductDetails);
		getDataUtil.getData(queue);
	}

	/**
	 * 关注按钮点击
	 */
	public void attention_tag_iv_click(View view) {
		getDate_GetFocusOn(callBack, recommendId, Constant.CollectionType.Brand, this);
	}

	/**
	 * 添加头部
	 */
	private void addHeadView() {
		relationProductAdapter = new RelationProductAdapter2(this);
		relationProductAdapter.setDatas(dataList);
		xgv.setAdapter(relationProductAdapter);
		xgv.setOnItemClickListener(onItemClickListener);
		//ptr_scrollview.setHeader(shop_bg_iv);
		ptr_scrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		//ptr_scrollview.setOnTurnListener(this);
		ptr_scrollview.setOnRefreshListener(loadMoreListener);
	}

	private PullToRefreshScrollView.OnRefreshListener2<ScrollView> loadMoreListener = new PullToRefreshScrollView.OnRefreshListener2<ScrollView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			ptr_scrollview.onRefreshComplete();
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//            getRelationGoods();
			getServerData();
		}

	};

	/**
	 * item的点击
	 */
	AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ProductInfoModel productInfoModel = dataList.get(position);
			seeProductDetail(productInfoModel);
		}
	};

	/**
	 * 获取商品 {shopId:165,typeId:2,nPage:1}
	 */
	private void getRelationGoods() {
		// xgv.setBoolInterceptTouchEvent(true);
		Map<String, Object> params = new HashMap<>();
		params.put("brandId", recommendId);
		params.put("pageIndex", String.valueOf(nPage + 1));
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_SelectVendorProductList);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_SelectVendorProductList);
		getDataUtil.getData(queue);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.what == GetDataConfing.What_Recommend_QueryProductDetails) {
				ptr_scrollview.onRefreshComplete();
				TypeToken<BaseResponse<RecommendDetailResponse>> typeToken = new TypeToken<BaseResponse<RecommendDetailResponse>>() {};
				BaseResponse<RecommendDetailResponse> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					recommendModel = baseResponse.getInfo().getRecommendDetail();
					List<RecommendProModel> productInfoModelList = baseResponse.getInfo().getRelatedProduct();
					initHead();
					setData(productInfoModelList);
					validateData(productInfoModelList);
				}

			} else if (entity.what == GetDataConfing.WHAT_GET_FOCUS_ON) { // 关注平拍
				TypeToken<BaseResponse<CollectionResponse>> typeToken = new TypeToken<BaseResponse<CollectionResponse>>() {};
				BaseResponse<CollectionResponse> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					//isCollected = baseResponse.getInfo().isCollected()>0?true:false;
					//toast.showToast(baseResponse.getMsg());
					//sendBroadcast(new Intent(Constant.ACT_ARTICLE_COLLECTION_CHANGE));
				}
			}
		}

	};

	/**
	 * 设置商品信息 并请求相关商品的信息
	 *
	 * @param productList
	 */
	private void refreshShopDetail(List<RecommendProModel> productList) {
//        initHead();
		if (productList.size() > 0) {
			setData(productList);
		}
		validateData(productList);
	}

	/**
	 * 更改关注状态
	 *
	 * @param isFocus
	 */
	private void changeAttendStatue(boolean isFocus) {/*
        attention_tag_iv.setSelected(isFocus);
        if (isFocus) {
            attention_tag_iv.setText(R.string.attentioned);
        } else {
            attention_tag_iv.setText(R.string.noattention);
        }
    */
	}

	private void setData(List<RecommendProModel> data_server) {
		if (data_server.size() != 0) {
			dataList.addAll(data_server);

			relationProductAdapter.setDatas(dataList);
			//xgv.setAdapter(relationProductAdapter);
			//ptr_scrollview.notifyAll();
			nPage++;
		} else {
			ptr_scrollview.setMode(PullToRefreshBase.Mode.DISABLED);
		}
	}


	/**
	 * 校验数据 显示无数据等
	 *
	 * @param data_server
	 */
	private void validateData(List<RecommendProModel> data_server) {
		if (data_server.size() < 10) {
			ptr_scrollview.setMode(PullToRefreshBase.Mode.DISABLED);
		} else {
			ptr_scrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		}
		//setListViewHeightBasedOnChildren(xgv);
	}

	private void share() {
		String shareLogo = recommendModel.getCoverPic();
		String shareTitle = recommendModel.getRecommendTitle();
		String shareContent = recommendModel.getRecommendIntroduction();
		String shareUrl = "pages/mobile_page/theme_details.html?recommendId=" + recommendId;
		ShareSocialUtils.showShareCompont(mContext, shareLogo, shareUrl, shareTitle, shareContent);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		if (EasyUtils.isSingleActivity(mContext)) {
			Intent intent = new Intent(mContext, SplashActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			jumpTo(intent, false);
		}
		super.onBackPressed();
	}
}