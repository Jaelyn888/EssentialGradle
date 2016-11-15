package com.yishanxiu.yishang.fragment;

import java.text.ParseException;
import java.util.*;

import cn.bingoogolapple.bgabanner.BGABanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.LoginActivity;
import com.yishanxiu.yishang.activity.ProductCommentsActivity;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.ProductPicAdapter;
import com.yishanxiu.yishang.adapter.RelationProductAdapter2;
import com.yishanxiu.yishang.app.MyApplication;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.shopmall.*;
import com.yishanxiu.yishang.model.user.CollectionResponse;
import com.yishanxiu.yishang.utils.*;
import com.yishanxiu.yishang.view.CircleImageView;
import com.yishanxiu.yishang.view.GridViewNoScroll;


import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.iwgang.countdownview.CountdownView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * 商品详情
 *
 * @author FangDongzhang 2016/8/3
 */
public class ProductDetailFragment extends LazyFragment {

	@ViewInject(R.id.ptr_scrollview)
	private PullToRefreshScrollView ptr_scrollview;

	@ViewInject(R.id.llt_pro_datail)
	private LinearLayout llt_pro_datail;

	@ViewInject(R.id.pro_detail)
	private RelativeLayout pro_detail;

	/**
	 * 店铺layout
	 */
	// @ViewInject(R.id.base_brand_layout)
	// private LinearLayout base_brand_layout;
	// @ViewInject(R.id.iv_head)
	// private ImageView brandLogo_iv;
	// @ViewInject(R.id.tv_name)
	// private TextView brand_name_tv;
	// @ViewInject(R.id.tv_msg)
	// private TextView brand_discri_tv;

	/**
	 * 动画的imageview
	 */
	// @ViewInject(R.id.iv_anim_collection)
	// private ImageView iv_anim_collection;

	@ViewInject(R.id.banner)
	private BGABanner banner; // 商品的图片

	/**
	 * 剩余商品数量
	 */
	@ViewInject(R.id.goods_retain_tv)
	private TextView goods_retain_tv;
	/**
	 * 刷新图下第一行
	 */
	@ViewInject(R.id.product_name_tv)
	private TextView product_name_tv;

	/**
	 * 现价
	 */
	@ViewInject(R.id.product_price_tv)
	private TextView product_price_tv;
	/**
	 * 查看详情
	 */
	@ViewInject(R.id.promotion_tv)
	private TextView promotion_tv;

	/**
	 * 原价
	 */
	@ViewInject(R.id.product_price_discount_tv)
	private TextView product_price_discount_tv;

	/**
	 * 收藏人数
	 */
	@ViewInject(R.id.collect_tv)
	private TextView collect_tv;
	/**
	 * 联系客服
	 */
	@ViewInject(R.id.custom_service_tv)
	private TextView custom_service_tv;

	/**
	 * 加入购物车
	 */
	@ViewInject(R.id.add_cart_tv)
	private TextView add_cart_tv;

	/**
	 * 选择颜色 尺码
	 */
	@ViewInject(R.id.product_specifiction_tv)
	private LinearLayout product_specifiction_tv;

	/**
	 * 店铺
	 */
	@ViewInject(R.id.shop_tv)
	private TextView shop_tv;

	@ViewInject(R.id.relation_product_gv)
	private GridViewNoScroll relation_product_gv;

	/**
	 * 下拉刷新Gridview适配器
	 */
	private RelationProductAdapter2 relationProductAdapter;

	/**
	 * 商品图片展示图
	 */
	private ProductPicAdapter productPicAdapter;


	/**
	 * 商品评论大布局
	 */
	@ViewInject(R.id.goods_comments_layout)
	private LinearLayout goods_comments_layout;
	/**
	 * 查看所有评论
	 */
	@ViewInject(R.id.scan_all_comments_tv)
	private TextView scan_all_comments_tv;

	/**
	 * 评论提示
	 */
	@ViewInject(R.id.goods_comments_tv)
	private TextView goods_comments_tv;
	/**
	 * 用户头像
	 */
	@ViewInject(R.id.user_photo_iv)
	private CircleImageView user_photo_iv;
	/**
	 * 用户名
	 */
	@ViewInject(R.id.userName_tv)
	private TextView userName_tv;

	/**
	 * 评论内容
	 */
	@ViewInject(R.id.userComments_tv)
	private TextView userComments_tv;

	@ViewInject(R.id.create_time_tv)
	private TextView create_time_tv;

	//倒计时
	@ViewInject(R.id.countdownLayout)
	private LinearLayout countdownLayout;
	@ViewInject(R.id.countdownView)
	private CountdownView countdownView;

	/**
	 * 评论中 商品尺码 颜色
	 */
	@ViewInject(R.id.goods_color_size_tv)
	private TextView goods_color_size_tv;

	private ProductDetailModel productDetailModel;
	/**
	 * 相关商品数据
	 */
	private List<ProductInfoModel> productInfoModelList = new ArrayList<>();
	private int nPage = 1;
	public PopupWindow popupWindow;
	private String skuId;
	// sku库存
	private int count;
	/**
	 * 加入购物车
	 *
	 * @button
	 */
	private TextView addShopCar;

	private ImageView iv_pronum_jian, iv_pronum_jia, pro_pic;
	private int pronum = 1;
	private final int MAX_NUM = 99;
	private TextView et_product_pronum;
	private TextView select_goods_params_tv;
	private TextView tv_stocks;// 库存显示
	public TextView product_price_tv_pop; // 原价
	public TextView product_price_discount_tv_pop; // 折扣价

	/**
	 * 当前展示的商品的可选参数的列表信息
	 */
	private List<TagFlowLayout> pips;
	public List<ProductSpecificationModel> specifictionList;

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_pro_detail);
		ViewUtils.inject(this, getContentView());

		Bundle bundle = getArguments();
		ProductInfoModel productInfoModel = (ProductInfoModel) bundle.getSerializable(ExtraKeys.Key_Msg1);
		productDetailModel = new ProductDetailModel();
		productDetailModel = DTOUtils.map(productInfoModel, ProductDetailModel.class);

		ViewGroup.LayoutParams linearParams = banner.getLayoutParams();
		linearParams.height = DisplayUtil.screenWidth;
		banner.setLayoutParams(linearParams);

		if (TextUtils.isEmpty(productDetailModel.getProductId())) {
			activity.finish();
		} else {
			productPicAdapter = new ProductPicAdapter();
			banner.setAdapter(productPicAdapter);
			initPreView();
			relation_product_gv.setOnItemClickListener(onItemClickListener);
			ptr_scrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
			ptr_scrollview.setOnRefreshListener(loadMoreListener);


			relationProductAdapter = new RelationProductAdapter2(activity);
			relationProductAdapter.setShowDiscri(true);
			relation_product_gv.setAdapter(relationProductAdapter);
			getGoodsDetial();
			getProudctSpec();
			getRelationGoods();
		}
	}

	private void initPreView() {
		handleProductPic();
		handleProductPrePrice();
		product_name_tv.setText(productDetailModel.getSalesName());
	}

	/**
	 * 处理商品价格
	 */
	private void handleProductPrePrice() {
		double marketPrice = productDetailModel.getCostPrice(); // 原价
		int isPromotion = productDetailModel.getIsPromotion();
		if (isPromotion > 0) {
			double discountPrice = productDetailModel.getDiscountPrice(); // 折扣价
			product_price_discount_tv.setText(initMoney(StringUtils.getFormatMoneyWithSign(discountPrice)));

			product_price_tv.setText(StringUtils.getFormatMoneyWithSign(marketPrice));
			Utils.addDeleteLine(product_price_tv);
			product_price_tv.setSelected(true);
			product_price_tv.setVisibility(View.VISIBLE);
		} else {
			product_price_tv.setVisibility(View.GONE);
			promotion_tv.setVisibility(View.GONE);
			product_price_discount_tv.setText(initMoney(StringUtils.getFormatMoneyWithSign(marketPrice)));
		}
	}

	/**
	 * 显示商品轮播图
	 */
	private void handleProductPic() {
		List<ProductPicModel> productPics = productDetailModel.getPicList();
		if (productPics != null && productPics.size() != 0) {
			banner.setData(productPics, null);
		} else {
			ArrayList<ProductPicModel> productPicModelList = new ArrayList<>();
			ProductPicModel productPicModel = new ProductPicModel();
			productPicModel.setPath(productDetailModel.getPath());
			productPicModelList.add(productPicModel);
			productDetailModel.setPicList(productPicModelList);
			banner.setData(productPicModelList, null);
		}

		banner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
			@Override
			public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
				activity.goodsImage_click(productDetailModel.getPicList(), position);
			}
		});
	}


	/**
	 * 商品点击
	 */
	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ProductInfoModel data = productInfoModelList.get(position);
			activity.seeProductDetail(data);
		}
	};

	// 购物车接口
	// private void getShopCartNum() {
	// Map<String, Object> params = new HashMap<>();
	//
	// params.put("UserAccount", Utils.getUserAccount(this));
	// GetDataQueue queue = new GetDataQueue();
	// queue.setCallBack(callBack);
	// queue.setActionName(GetDataConfing.GetAction_GetShoppingCartCount);
	// queue.setWhat(GetDataConfing.What_GetShoppingCartCount);
	// queue.setParamsNoJson(params);
	// getDataUtil.getData(queue);
	// }

	/**
	 * 刷新监听
	 */
	private OnRefreshListener2<ScrollView> loadMoreListener = new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			getRelationGoods();
		}
	};

	/**
	 * 获取商品详情 * {Id:15973}
	 */
	private void getGoodsDetial() {
		loadingToast.show();
		Map<String, Object> params = new HashMap<>();
		if (Utils.isHasLogin(activity)) {
			params.put("userId", Utils.getUserId(activity));
		}
		params.put("productId", productDetailModel.getProductId());
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_GetProductDetails);
		queue.setWhat(GetDataConfing.What_GetProductDetails);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);

		getDataUtil.getData(queue);
	}

	/**
	 * 获取相关商品 {VendorId:165,CategoryId:1535,currentPage:1,pageSize:10} 相关商品
	 * pageIndex 页数 categoryId 大分类Id productId 商品Id
	 */
	private void getRelationGoods() {
		Map<String, Object> params = new HashMap<>();
		params.put("pageIndex", String.valueOf(nPage));
		params.put("productId", productDetailModel.getProductId());
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_GetProudctCategroyList);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_GetProudctCategroyList);
		getDataUtil.getData(queue);

	}

	// @OnClick(R.id.base_brand_layout)
	// public void base_brand_layout_click(View view) {
	// if (goodsDatas == null) {
	// return;
	// }
	// JsonMap<String, Object> hashMap = new JsonMap<>();
	// hashMap.put("brandId", brandId);
	// hashMap.put("brandName", goodsDatas.getStringNoNull("brandName"));
	// hashMap.put("pageDesc", goodsDatas.getStringNoNull("pageDesc"));
	// hashMap.put("logoPath", goodsDatas.getStringNoNull("logoPath"));
	// activity.seeShopDetail(hashMap);
	// }

	/**
	 * 查看所有评论
	 *
	 * @param view
	 */
	@OnClick(R.id.scan_all_comments_tv)
	public void scan_all_comments_tv_click(View view) {
		if (productDetailModel == null) {
			return;
		}
		Intent intent = new Intent(activity, ProductCommentsActivity.class);
		intent.putExtra(Constant.ID, productDetailModel.getProductId());
		activity.jumpTo(intent, false);
	}

	/**
	 * 分享的点击事件
	 *
	 * @param view
	 */
	public void share_iv_click(View view) {
		if (productDetailModel == null) {
			return;
		}
		String shareLogo = productDetailModel.getPath();
		String shareContent = productDetailModel.getWords();
		String shareTitle = productDetailModel.getSalesName();
		String shareUrl = "pages/busi_platform/business/sharepages/gods_detail.html?productId=" + productDetailModel.getProductId();
		ShareSocialUtils.showShareCompont(activity, shareLogo, shareUrl, shareTitle, shareContent);
	}

	/**
	 * 进入店铺
	 *
	 * @param view
	 */
	@OnClick(R.id.shop_tv)
	public void shop_tv_click(View view) {
		if (productDetailModel == null) {
			return;
		}
		BrandModel brandInfo = new BrandModel();
		brandInfo.setBrandId(productDetailModel.getBrandId());
		brandInfo.setPageDesc(productDetailModel.getPageDesc());
		brandInfo.setBrandName(productDetailModel.getBrandName());
		brandInfo.setLogoPath(productDetailModel.getLogoPath());

		activity.seeShopDetail(brandInfo);
	}

	/**
	 * 联系客服
	 *
	 * @param view
	 */
	@OnClick(R.id.custom_service_tv)
	public void custom_service_tv_click(View view) {
		if (productDetailModel == null) {
			return;
		}
		String shareUrl = GetDataConfing.Product_Share_Url + productDetailModel.getProductId();
		CustomServiceProductInfo customServiceProductInfo = DTOUtils.map(productDetailModel, CustomServiceProductInfo.class);
		customServiceProductInfo.setProductUrl(shareUrl);
		activity.contactCustomServiceAndSendInfo(Constant.IM, Constant.CHATTYPE_SINGLE, 1, customServiceProductInfo);
	}

	/**
	 * 收藏的点击事件
	 *
	 * @param view
	 */
	@OnClick(R.id.collect_tv)
	public void collect_tv_click(View view) {
		if (productDetailModel == null) {
			return;
		}
		activity.getDate_GetFocusOn(callBack, productDetailModel.getProductId(), Constant.CollectionType.Product, activity);
	}

	/**
	 * 加入购物车的点击事件
	 *
	 * @param view
	 */
	@OnClick(R.id.add_cart_tv)
	public void add_cart_tv_click(View view) {
		if (productDetailModel == null) {
			return;
		}
		if (Utils.isFastClick() == false) {
			if (Utils.isHasLogin(activity)) {
				SpecificationPopwindow();
			} else {
				activity.jumpTo(LoginActivity.class);
			}
		}
	}

	/**
	 * 选择规格
	 *
	 * @param view
	 */
	@OnClick(R.id.product_specifiction_tv)
	public void product_specifiction_tv_click(View view) {
		add_cart_tv_click(view);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.what == GetDataConfing.What_GetProudctCategroyList) {
				ptr_scrollview.onRefreshComplete();
				TypeToken<BaseResponse<List<ProductInfoModel>>> typeToken = new TypeToken<BaseResponse<List<ProductInfoModel>>>() {
				};
				BaseResponse<List<ProductInfoModel>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					List<ProductInfoModel> data = baseResponse.getInfo();
					setAdapterData(data);
					validateData(data);
				}
			} else if (entity.what == GetDataConfing.What_GetShoppingCartCount) { // 获取购物车数据
				int num = 0;
				MyApplication.getInstance().setShopcart_num(num);
				// refreshShoppingCartNum();
			} else if (entity.what == GetDataConfing.What_AddShoppingCart) {// 添加购物车
				TypeToken<BaseResponse> typeToken = new TypeToken<BaseResponse>() {
				};
				BaseResponse baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					toast.showToast(baseResponse.getMsg());
					if (popupWindow != null && popupWindow.isShowing()) {
						popupWindow.dismiss();
					}
				}

				// MyApplication.getInstance().addShopCartNum();
				// refreshShoppingCartNum();
			} else if (entity.what == GetDataConfing.What_GetProductDetails) {
				TypeToken<BaseResponse<ProductDetailModel>> typeToken = new TypeToken<BaseResponse<ProductDetailModel>>() {
				};
				BaseResponse<ProductDetailModel> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					String productId = productDetailModel.getProductId();
					productDetailModel = baseResponse.getInfo();
					productDetailModel.setProductId(productId);
					refreshGoodsDetail();
				} else {
					productDetailModel = null;
				}

			} else if (entity.what == GetDataConfing.What_GetProductSpec) {// 规格列表
				TypeToken<BaseResponse<List<ProductSpecificationModel>>> typeToken = new TypeToken<BaseResponse<List<ProductSpecificationModel>>>() {
				};
				BaseResponse<List<ProductSpecificationModel>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					specifictionList = baseResponse.getInfo();
				}

			} else if (entity.what == GetDataConfing.What_GetProductByproidandSpec) {// 规格列表返回
				TypeToken<BaseResponse<ProductSKUInfoModel>> typeToken = new TypeToken<BaseResponse<ProductSKUInfoModel>>() {
				};
				BaseResponse<ProductSKUInfoModel> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					flushMsg(baseResponse.getInfo());
				}

			} else if (entity.what == GetDataConfing.WHAT_GET_FOCUS_ON) { // 收藏商品
				TypeToken<BaseResponse<CollectionResponse>> typeToken = new TypeToken<BaseResponse<CollectionResponse>>() {
				};
				BaseResponse<CollectionResponse> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					int isCollected = baseResponse.getInfo().isCollected();
					boolean isCollectedBoolean = isCollected > 0 ? true : false;
					toast.showToast(baseResponse.getMsg());
					collect_tv.setSelected(isCollectedBoolean);
					collect_tv.setText(isCollectedBoolean ? R.string.collectioned : R.string.collection);
					if (isCollectedBoolean) {
						startAnimation();
					}
					activity.sendBroadcast(new Intent(Constant.ACT_PRODUCT_COLLECTION_CHANGE));
				}
			}
		}
	};

	/**
	 * 刷新提示信息
	 *
	 * @param productSKUInfoModel
	 */
	private void flushMsg(ProductSKUInfoModel productSKUInfoModel) {
		skuId = productSKUInfoModel.getSkuId();
		count = productSKUInfoModel.getCount();

		select_goods_params_tv.setText(getProductParamsSelectTitle());
		tv_stocks.setText(getResources().getString(R.string.pro_count, count));
		double marketPrice = productSKUInfoModel.getCostPrice(); // 原价
		int isPromotion = productSKUInfoModel.getIsDiscount();
		if (isPromotion > 0) {
			double discountPrice = productSKUInfoModel.getDiscountPrice(); // 折扣价
			product_price_discount_tv_pop.setText(initMoney(StringUtils.getFormatMoneyWithSign(discountPrice)));
			product_price_tv_pop.setText(StringUtils.getFormatMoneyWithSign(marketPrice));
			Utils.addDeleteLine(product_price_tv_pop);
			product_price_tv_pop.setVisibility(View.VISIBLE);
		} else {
			product_price_discount_tv_pop.setText(initMoney(StringUtils.getFormatMoneyWithSign(marketPrice)));
		}
		if (count >= pronum) {
		} else {
			pronum = 1;
			show();
		}

	}

	private void startAnimation() {
		Animation animout = AnimationUtils.loadAnimation(activity, R.anim.zoom_exit);
		// iv_anim_collection.setVisibility(View.VISIBLE);
		// iv_anim_collection.startAnimation(animout);
		animout.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// iv_anim_collection.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}

	/**
	 * 设置商品信息 并请求相关商品的信息
	 */
	private void refreshGoodsDetail() {
		// 商品轮播图
		handleProductPic();
		//处理价格区间及倒计时
		handleProductPriceDistance();

		product_name_tv.setText(productDetailModel.getSalesName());
		((BaseUIActivity)getActivity()).setCenter_title(productDetailModel.getBrandName());
		int isCollected = productDetailModel.getIsCollection();
		//收藏状态
		handleProductCollectStatus(isCollected);
		// 商品状态和上下架
		handleProductSalesStatus();
		// 商品评论
		handleProductComment();
	}

	//收藏状态
	private void handleProductCollectStatus(int isCollected) {
		boolean isCollectedBoolean = isCollected > 0 ? true : false;
		collect_tv.setSelected(isCollectedBoolean);
		collect_tv.setText(isCollectedBoolean ? R.string.collectioned : R.string.collection);
	}

	// 商品评论
	private void handleProductComment() {
		int productCommentsCount = productDetailModel.getCommentCount();
		if (productCommentsCount > 0) {
			goods_comments_layout.setVisibility(View.VISIBLE);
			ProductCommentModel commentJson = productDetailModel.getRecommendComment();
			userName_tv.setText(commentJson.getUserNickname());
			String stringFormat = getString(R.string.goods_comments);
			String msg = String.format(stringFormat, productCommentsCount);
			goods_comments_tv.setText(msg);
			BitmapHelper.setUserIcon(activity, commentJson, user_photo_iv);
			userComments_tv.setText(commentJson.getCommentContent());
			goods_color_size_tv.setText(commentJson.getProductSpecification());
		} else {
			goods_comments_layout.setVisibility(View.GONE);
		}
		create_time_tv.setVisibility(View.GONE);
	}

	// 商品状态和上下架
	private void handleProductSalesStatus() {
		int isEnable = productDetailModel.getSaleStatus();
		if (isEnable > 0) {
			int stock = productDetailModel.getSpuStock();
			if (stock < 4) {
				// goods_retain_tv.setVisibility(View.VISIBLE);
				if (stock < 1) {
					goods_retain_tv.setText(R.string.salve_over);
					ViewAnimationUtils.startAnimation(activity, goods_retain_tv);
				} else {
					String strFormat = this.getString(R.string.product_retain, stock);
					goods_retain_tv.setText(strFormat);
					ViewAnimationUtils.startAnimation(activity, goods_retain_tv);
				}
			} else {
				goods_retain_tv.setVisibility(View.INVISIBLE);
			}

		} else {
			// goods_retain_tv.setVisibility(View.VISIBLE);
			goods_retain_tv.setText(R.string.sales_off);
			ViewAnimationUtils.startAnimation(activity, goods_retain_tv);
		}
	}

	//处理价格区间
	private void handleProductPriceDistance() {
		double minPriceOrigin = productDetailModel.getMinPriceOrigin();
		double maxPriceOrigin = productDetailModel.getMaxPriceOrigin();
		double discountMinPrice = productDetailModel.getDiscountMinPrice();
		double discountMaxPrice = productDetailModel.getDiscountMaxPrice();
		int isPromotion = productDetailModel.getIsPromotion();

		if (isPromotion > 0) {
			if (Math.abs(maxPriceOrigin - minPriceOrigin) >= 0.01) {//价格区间
				product_price_discount_tv.setText(initMoney(StringUtils.getFormatMoneyWithSign(discountMinPrice) + "-" + StringUtils.getFormatMoney(discountMaxPrice)));
				product_price_tv.setText(StringUtils.getFormatMoneyWithSign(minPriceOrigin) + "-" + StringUtils.getFormatMoney(maxPriceOrigin));
			} else {
				product_price_discount_tv.setText(initMoney(StringUtils.getFormatMoneyWithSign(discountMinPrice)));
				product_price_tv.setText(StringUtils.getFormatMoneyWithSign(minPriceOrigin));
			}
			Utils.addDeleteLine(product_price_tv);
			product_price_tv.setSelected(true);
			product_price_tv.setVisibility(View.VISIBLE);
			handleCountDown();
		} else {
			if (Math.abs(maxPriceOrigin - minPriceOrigin) >= 0.01) {//价格区间
				product_price_discount_tv.setText(initMoney(StringUtils.getFormatMoneyWithSign(minPriceOrigin) + "-" + StringUtils.getFormatMoney(maxPriceOrigin)));
			} else {
				product_price_discount_tv.setText(initMoney(StringUtils.getFormatMoneyWithSign(minPriceOrigin)));
			}
			product_price_tv.setVisibility(View.GONE);
		}
	}

	//处理倒计时显示
	private void handleCountDown() {
		String promotinEndTime = productDetailModel.getPromotinEndTime();
		if (!TextUtils.isEmpty(promotinEndTime)) {
			long promotinEndTimeLong = 0;
			try {
				promotinEndTimeLong = StringUtils.stringToLong(promotinEndTime);
				long currentTimeMillis = System.currentTimeMillis();

				long time4 = promotinEndTimeLong - currentTimeMillis;
				countdownView.start(time4);
				countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
					@Override
					public void onEnd(CountdownView cv) {

					}
				});
				countdownLayout.setVisibility(View.VISIBLE);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			promotion_tv.setText(productDetailModel.getPromotionName());
			promotion_tv.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * ￥缩小显示
	 *
	 * @param value
	 * @return
	 */
	private SpannableStringBuilder initMoney(String value) {
		SpannableStringBuilder style = new SpannableStringBuilder(value);
		style.setSpan(new TextAppearanceSpan(activity, R.style.style1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return style;
	}

	private void setAdapterData(List<ProductInfoModel> data_server) {
		if (nPage == 1) {
			productInfoModelList = data_server;
			relationProductAdapter.setDatas(data_server);
			relation_product_gv.setAdapter(relationProductAdapter);
		} else {
			productInfoModelList.addAll(data_server);
			relationProductAdapter.setDatas(productInfoModelList);
		}
		nPage++;
	}

	/**
	 * 校验数据 显示无数据等
	 * <p>
	 * 是否请求正常 网络错误且数据为空的话显示网络异常界面 提供刷新的接口
	 *
	 * @param data_server
	 */
	private void validateData(List<ProductInfoModel> data_server) {
		if (data_server == null || data_server.size() == 0) {
			ptr_scrollview.setMode(PullToRefreshBase.Mode.DISABLED);
		} else {
			ptr_scrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		}
	}

	/**
	 * 规格
	 *
	 * @author FangDongzhang
	 */
	public LinearLayout goods_params;

	public void SpecificationPopwindow() {
		if (specifictionList == null) {
			toast.showToast(R.string.product_info_error);
			return;
		} else if (specifictionList.size() == 0) {
			add2Cart();
			return;
		}
		skuId = "";
		count = 0;
		View view = activity.getLayoutInflater().inflate(R.layout.select_goods_params_layout, null);
		popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_ins));
		LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.relativeLayout01);
		ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));
		popupWindow.setOutsideTouchable(true);
		popupWindow.setContentView(view);
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		popupWindow.update();

		popInitView(view);
		String path = productDetailModel.getPath();
		BitmapHelper.loadImage(activity, StringUtils.get_MImage(path), pro_pic, BitmapHelper.LoadImgOption.BrandLogo);
		setParamsView();
		// et_product_pronum.addTextChangedListener(textWatcher);
		addShopCar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (Utils.isFastClick()) {
					return;
				}
				if (TextUtils.isEmpty(skuId)) {
					toast.showToast(R.string.select_product_specification);
					return;
				}
				if (count < 1) {
					toast.showToast(R.string.stock_short);
					return;
				}
				add2Cart();
			}
		});
		iv_pronum_jia.setOnClickListener(onClickListener);
		iv_pronum_jian.setOnClickListener(jianOnClick);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rlt_pop);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		// } else if (!popupWindow.isShowing()) {
		// popupWindow.showAtLocation(add_cart_tv, Gravity.BOTTOM, 0, 0);
		// setParamsView();
		// }
	}

	/**
	 * @popupwindow 初始化组件
	 */
	private void popInitView(View view) {
		ScrollView ll_popup = (ScrollView) view.findViewById(R.id.scrollView);
		ll_popup.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_bottom_in_2));
		popupWindow.showAtLocation(pro_detail, Gravity.BOTTOM, 0, 0);

		popupWindow.setOutsideTouchable(true);
		popupWindow.update();

		iv_pronum_jian = (ImageView) view.findViewById(R.id.i_s_iv_pronum_jian);
		iv_pronum_jia = (ImageView) view.findViewById(R.id.i_s_iv_pronum_jia);
		pro_pic = (ImageView) view.findViewById(R.id.pro_pic);
		et_product_pronum = (TextView) view.findViewById(R.id.i_s_et_product_pronum);

		select_goods_params_tv = (TextView) view.findViewById(R.id.select_goods_params_tv);
		tv_stocks = (TextView) view.findViewById(R.id.tv_stocks);

		product_price_tv_pop = (TextView) view.findViewById(R.id.product_price_tv);
		product_price_discount_tv_pop = (TextView) view.findViewById(R.id.product_price_discount_tv);

		double minPriceOrigin = productDetailModel.getMinPriceOrigin();
		double maxPriceOrigin = productDetailModel.getMaxPriceOrigin();
		double discountMinPrice = productDetailModel.getDiscountMinPrice();
		double discountMaxPrice = productDetailModel.getDiscountMaxPrice();
		int isPromotion = productDetailModel.getIsPromotion();

		if (isPromotion > 0) {
			if (Math.abs(maxPriceOrigin - minPriceOrigin) >= 0.01) {//价格区间
				product_price_discount_tv_pop.setText(initMoney(StringUtils.getFormatMoneyWithSign(discountMinPrice) + "-" + StringUtils.getFormatMoney(discountMaxPrice)));
				product_price_tv_pop.setText(StringUtils.getFormatMoneyWithSign(minPriceOrigin) + "-" + StringUtils.getFormatMoney(maxPriceOrigin));
			} else {
				product_price_discount_tv_pop.setText(initMoney(StringUtils.getFormatMoneyWithSign(discountMinPrice)));
				product_price_tv_pop.setText(StringUtils.getFormatMoneyWithSign(minPriceOrigin));
			}
			Utils.addDeleteLine(product_price_tv_pop);
			product_price_tv_pop.setSelected(true);
			product_price_tv_pop.setVisibility(View.VISIBLE);

		} else {
			if (Math.abs(maxPriceOrigin - minPriceOrigin) >= 0.01) {//价格区间
				product_price_discount_tv_pop.setText(initMoney(StringUtils.getFormatMoneyWithSign(minPriceOrigin) + "-" + StringUtils.getFormatMoney(maxPriceOrigin)));
			} else {
				product_price_discount_tv_pop.setText(initMoney(StringUtils.getFormatMoneyWithSign(minPriceOrigin)));
			}
			product_price_tv_pop.setVisibility(View.GONE);
		}

		addShopCar = (TextView) view.findViewById(R.id.p_add_cart_tv);
		goods_params = (LinearLayout) view.findViewById(R.id.goods_params);
	}

/**
 * 数量文本框
 */
// public TextWatcher textWatcher = new TextWatcher() {
//
// @Override
// public void onTextChanged(CharSequence s, int start, int before, int
// count) {
//
// }
//
// @Override
// public void beforeTextChanged(CharSequence s, int start, int count, int
// after) {
//
// }
//
// @Override
// public void afterTextChanged(Editable s) {
// int num = Integer.parseInt(s.toString().trim());
// if(num<=count&&num<MAX_NUM) {
// pronum=num;
// show();
// }
// }
// };
	/**
	 * 数量加
	 */
	public OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (pronum < count && pronum < MAX_NUM) {
				pronum += 1;
				show();
			}
		}
	};
	/**
	 * 数量减
	 */
	public OnClickListener jianOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (pronum > 1) {
				pronum = pronum - 1;
				show();
			}
		}
	};

	/**
	 * 数量显示
	 */
	public void show() {
		et_product_pronum.setText(pronum + "");
	}

	/**
	 * 获取商品规格的数据
	 */
	private void getProudctSpec() {
		Map<String, Object> params = new HashMap<>();
		params.put("productId", productDetailModel.getProductId());
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_GetProducSpec);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_GetProductSpec);
		getDataUtil.getData(queue);
	}

	/**
	 * 设置显示选项
	 */
	private void setParamsView() {
		pips = new ArrayList<>();
		goods_params.removeAllViews();
		for (ProductSpecificationModel productSpecificationModel : specifictionList) {
			View view = activity.getLayoutInflater().inflate(R.layout.layout_product_info_params, null);
			final TextView tv = (TextView) view.findViewById(R.id.l_p_i_tv_pro_param);
			tv.setText(productSpecificationModel.getSpecificationName());
			List<SpecificationValueModel> data = productSpecificationModel.getSpecificationValueVos();
			final TagFlowLayout mFlowLayout = (TagFlowLayout) view.findViewById(R.id.tagview);

			mFlowLayout.setAdapter(new TagAdapter<SpecificationValueModel>(data) {
				@Override
				public View getView(FlowLayout parent, int position, SpecificationValueModel specificationValueModel) {
					TextView tv = (TextView) activity.getLayoutInflater().inflate(R.layout.product_specification_item,
							mFlowLayout, false);
					tv.setText(specificationValueModel.getSpecificationValue());
					tv.setTag(specificationValueModel);
					return tv;
				}
			});

			mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
				@Override
				public void onSelected(Set<Integer> selectPosSet) {
					getProudctInfoByByproidandSpec(getProductParamsSelect());
				}
			});
			goods_params.addView(view);
			pips.add(mFlowLayout);
		}

	}

	/**
	 * 获取商品当前选中的 商品信息
	 */
	private String getProductParamsSelect() {
		String p = "";
		for (int i=0;i< pips.size();i++) {
			TagFlowLayout pa =pips.get(i);
			Set<Integer> integerSet=pa.getSelectedList();
			int selectIndex=-1;
			  for (Integer id:integerSet){
				  selectIndex=id;
				  break;
			  }

			if (selectIndex != -1) {
				SpecificationValueModel specificationValueModel=specifictionList.get(i).getSpecificationValueVos().get(selectIndex);
				p += specificationValueModel.getSpecificationValueId()+ ",";
			}
		}

		if (p.length() > 0) {
			p = p.substring(0, p.length() - 1);
		}

		return p;
	}

	/**
	 * 获取商品当前选中的 商品信息
	 */
	private String getProductParamsSelectTitle() {
		String p = "";
		for (int i=0;i< pips.size();i++) {
			TagFlowLayout pa =pips.get(i);
			Set<Integer> integerSet=pa.getSelectedList();
			int selectIndex=-1;
			for (Integer id:integerSet){
				selectIndex=id;
				break;
			}

			if (selectIndex != -1) {
				SpecificationValueModel specificationValueModel=specifictionList.get(i).getSpecificationValueVos().get(selectIndex);
				p += specificationValueModel.getSpecificationValue()+ ",";
			}
		}

		if (p.length() > 0) {
			p = p.substring(0, p.length() - 1);
		}
		return "已选:" + p;
	}

	/**
	 * 获取商品规格的数据
	 */
	private void getProudctInfoByByproidandSpec(String spec) {
		int specifictionSize = specifictionList.size();
		int num = spec.split(",").length;
		if (num == specifictionSize) {
			Map<String, Object> params = new HashMap<>();
			params.put("productId", productDetailModel.getProductId());
			params.put("valueIds", spec);
			GetDataQueue queue = new GetDataQueue();
			queue.setActionName(GetDataConfing.Action_GetProductByproidandSpec);
			queue.setParamsNoJson(params);
			queue.setCallBack(callBack);
			queue.setWhat(GetDataConfing.What_GetProductByproidandSpec);
			getDataUtil.getData(queue);
		} else {
			skuId="";
		}
	}

	/**
	 * 加入购物车
	 *
	 * @param
	 */
	public void add2Cart() {
		loadingToast.show(R.string.add2cart_ing);
		Map<String, Object> params = new HashMap<>();
		params.put("userId", Utils.getUserId(activity));
		params.put("productId", productDetailModel.getProductId());
		if (specifictionList != null) {
			params.put("productSkuId", skuId);
		}
		params.put("productCount", String.valueOf(pronum));
		params.put("shopId", productDetailModel.getShopId());
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_AddShoppingCart);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_AddShoppingCart);
		getDataUtil.getData(queue);
	}

	/**
	 * 点击banck见
	 *
	 * @return
	 */
	@Override
	public boolean backKeyClick() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			return false;
		} else {
			return true;
		}
	}

}