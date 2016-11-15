package com.yishanxiu.yishang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.hyphenate.util.EasyUtils;
import com.umeng.socialize.UMShareAPI;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.LinkProductAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.article.ArticleModel;
import com.yishanxiu.yishang.model.user.CollectionResponse;
import com.yishanxiu.yishang.model.shopmall.LinkProductModel;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;
import com.yishanxiu.yishang.model.user.UserBaseInfoModel;
import com.yishanxiu.yishang.utils.*;
import com.yishanxiu.yishang.view.GridViewNoScroll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by Jaelyn on 2015/9/8 0008. 发现详情
 */
public class ArticalDetailActivity extends BaseUIActivity {

	/**
	 * 加载内容的控件
	 */
	@ViewInject(id = R.id.content_layout)
	private LinearLayout content_layout;

	/**
	 * 品牌logo
	 */
	@ViewInject(id = R.id.find_contents_wb)
	private WebView find_contents_wb;
	@ViewInject(id = R.id.myProgressBar)
	private ProgressBar myProgressBar;

	/**
	 * 相关用户的title
	 */
	@ViewInject(id = R.id.relation_person_layout)
	private LinearLayout relation_person_layout;
	@ViewInject(id = R.id.relation_person_info)
	private LinearLayout relation_person_info;

	/**
	 * 相关商品的title
	 */
	@ViewInject(id = R.id.relation_goods)
	private LinearLayout relation_goods_title_layout;

	// 相关商品
	@ViewInject(id = R.id.noscroll_gv)
	private GridViewNoScroll relation_goods_xgv;
	private LinkProductAdapter relationProductAdapter;

	private ArticleModel articleDatas;// 文章详情
	private int nPage = 0;
	/**
	 * 商品的信息数据
	 */
	private List<LinkProductModel> productModelList;
   private boolean isCollected=false;
	private List<UserBaseInfoModel> relationPersons = new ArrayList<>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artical_detail_layout);
		setBtn_backListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (EasyUtils.isSingleActivity(mContext)) {
					Intent intent = new Intent(mContext, SplashActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
					jumpTo(intent);
				}
				finish();
			}
		});
		setBtn_menu(R.drawable.msg_white, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				msg_iv_click(v);
			}
		}, R.drawable.more_white, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				more_iv_click();
			}
		});
		articleDatas= (ArticleModel) getIntent().getSerializableExtra(ExtraKeys.Key_Msg1);
		relationProductAdapter = new LinkProductAdapter(mContext);
		relation_goods_xgv.setOnItemClickListener(onItemClickListener);
		String formartStr = GetDataConfing.Url_Base + "pages/mobile_page/articles.html?articleId=" + articleDatas.getArticleId();
		setCenter_title(articleDatas.getGroupName());
		// String url = String.format(formartStr, Utils.getUserId(mContext),
		// discoredId);
		setWebViewFeature();
		find_contents_wb.loadUrl(formartStr);
		getArticalDetail();
		// getRelationGoods();
	}

	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			LinkProductModel data = productModelList.get(position);
			int isLinkProduct = data.getIsLinkProduct();
			if (isLinkProduct>0) {
				Intent intent = new Intent(mContext, UrlWebviewActivity.class);
				intent.putExtra(Constant.INTENT_URL, data.getLinkUrl());
				jumpTo(intent);
			} else {
				ProductInfoModel productInfoModel=new ProductInfoModel();
				productInfoModel.setProductId(data.getProductId());
				productInfoModel.setBrandName(data.getBrandName());
				productInfoModel.setPath(data.getPath());
				productInfoModel.setIsDiscount(data.getIsDiscount());
				productInfoModel.setProductName(data.getProductName());
				productInfoModel.setCostPrice(data.getCostPrice());
				productInfoModel.setDiscountPrice(data.getDiscountPrice());

				seeProductDetail(productInfoModel);
			}
		}
	};

	/**
	 * 获取相关商品 {DiscoverId:3, currentPage:1,pageSize:10}
	 */
	private void getRelationGoods() {
		Map<String, Object> params = new HashMap<>();
		params.put("articleId", articleDatas.getArticleId());
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_GetArticalRelationGoods);
		queue.setWhat(GetDataConfing.What_GetArticalRelationGoods);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		getDataUtil.getData(queue);
	}

	/**
	 * {DiscoverId:3} ProudctWebService.asmx/GetDiscoverDetails {userID: 2,
	 * articleID: 18} 获取商品详情
	 */
	private void getArticalDetail() {
		loadingToast.show();
		Map<String, Object> params = new HashMap<>();

		if (Utils.isHasLogin(this)) {
			params.put("userId", Utils.getUserId(mContext));
		}
		params.put("articleId", articleDatas.getArticleId());
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_GetArticalDetail);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_GetArticalDetail);
		getDataUtil.getData(queue);
	}

	/**
	 * 校验数据 显示无数据等
	 * <p/>
	 * 是否请求正常 网络错误且数据为空的话显示网络异常界面 提供刷新的接口
	 */
	private void validateData() {
		relation_goods_xgv.requestLayout();
		find_contents_wb.requestLayout();
		// if (data_server.size() == 0 || data_server.size() % pageSize != 0) {
		// relation_goods_xgv.setPullLoadEnable(false);
		// } else {
		// relation_goods_xgv.setPullLoadEnable(true);
		// }
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {

		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.what == GetDataConfing.What_GetArticalDetail) {
				TypeToken<BaseResponse<ArticleModel>> typeToken=new TypeToken<BaseResponse<ArticleModel>>(){};
				BaseResponse<ArticleModel> baseResponse=new ModleUtils().mapTo(entity,typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					String articleId=articleDatas.getArticleId();
					articleDatas = baseResponse.getInfo();
					articleDatas.setArticleId(articleId);
					refreshDetail();
				}else {
					articleDatas=null;
				}
			} else if (entity.what == GetDataConfing.What_GetArticalRelationGoods) {
				TypeToken<BaseResponse<List<LinkProductModel>>> typeToken=new TypeToken<BaseResponse<List<LinkProductModel>>>(){};
				BaseResponse<List<LinkProductModel>> baseResponse=new ModleUtils().mapTo(entity,typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					List<LinkProductModel> data_server = baseResponse.getInfo();
					setData(data_server);
					// validateData(data_server);
				}
			} else if (entity.what == GetDataConfing.WHAT_GET_FOCUS_ON) { // 关注平拍
				TypeToken<BaseResponse<CollectionResponse>> typeToken=new TypeToken<BaseResponse<CollectionResponse>>(){};
				BaseResponse<CollectionResponse> baseResponse=new ModleUtils().mapTo(entity,typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					isCollected = baseResponse.getInfo().isCollected()>0?true:false;
					toast.showToast(baseResponse.getMsg());
					sendBroadcast(new Intent(Constant.ACT_ARTICLE_COLLECTION_CHANGE));
				}
			}

		}

	};

	private void setData(List<LinkProductModel> temp_data) {
		if (nPage == 0) {
			productModelList = temp_data;
			relationProductAdapter.setDatas(productModelList);
			relation_goods_xgv.setAdapter(relationProductAdapter);
			relationProductAdapter.notifyDataSetChanged();
		} else {
			productModelList.addAll(temp_data);
			relationProductAdapter.notifyDataSetChanged();
		}
		nPage++;
	}

	private void refreshDetail() {
		setCenter_title(articleDatas.getGroupName());
		isCollected = articleDatas.getIsCollected()>0?true:false;
		// boolean isHasMoreRelationPerson =
		// articleDatas.getBoolean("hasMoreRelatePerson", false);
		// setCommentsNum(commentsCounts);
		// relationPersons = (ArrayList<JsonMap<String, Object>>)
		// articleDatas.getList_JsonMap("relationPersons");
		// for (int i=0;i<4;i++){
		// JsonMap<String,Object> jsonMap=new JsonMap<String, Object>();
		// jsonMap.put("userName","userName"+i);
		// relationPersons.add(jsonMap);
		// }
		// isHasMoreRelationPerson=true;

		// if (relationPersons.size() != 0) {
		// relation_person_layout.setVisibility(View.VISIBLE);
		// for (int i = 0; i < relationPersons.size(); i++) {
		// View relationView =
		// getLayoutInflater().inflate(R.layout.relation_person_item,
		// content_layout, false);
		// relationView.setLayoutParams(new LinearLayout.LayoutParams(
		// MyApplication.getInstance().getScreenWidth() /
		// relationPersons.size(),
		// LinearLayout.LayoutParams.MATCH_PARENT));
		// relationView.setTag(i);
		// relationView.setOnClickListener(relationPersionClick);
		// JsonMap<String, Object> jsonMap = relationPersons.get(i);
		// TextView textView = (TextView)
		// relationView.findViewById(R.id.userName_tv);
		// CircleImageView user_photo_mask_iv = (CircleImageView) relationView
		// .findViewById(R.id.user_photo_mask_iv);
		// CircleImageView user_photo_iv = (CircleImageView)
		// relationView.findViewById(R.id.user_photo_iv);
		// textView.setText(jsonMap.getStringNoNull("userName"));
		// BitmapHelper.setUserIcon(mContext, jsonMap, user_photo_iv);
		// if (i == 4 && isHasMoreRelationPerson) {
		// user_photo_mask_iv.setVisibility(View.VISIBLE);
		// textView.setText(R.string.more);
		// }
		// relation_person_info.addView(relationView);
		// }
		// } else {
		// relation_person_layout.setVisibility(View.GONE);
		// }

		int relationGoodsNum = articleDatas.getRelationProductCount();
		if (relationGoodsNum > 0) {
			relation_goods_xgv.setVisibility(View.VISIBLE);
			relation_goods_title_layout.setVisibility(View.VISIBLE);
			relation_goods_xgv.setAdapter(relationProductAdapter);
			getRelationGoods();
		} else {
			relation_goods_xgv.setVisibility(View.GONE);
			relation_goods_title_layout.setVisibility(View.GONE);
		}
	}

	/**
	 * 相关人员点击
	 */
	// View.OnClickListener relationPersionClick = new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// int id = (Integer) v.getTag();
	// JsonMap<String, Object> jsonMap = relationPersons.get(id);
	// String userId = jsonMap.getStringNoNull("userId");
	//
	// switch (id) {
	// case 0:
	// case 1:
	// case 2:
	// scanUserBlog(userId);
	// break;
	// case 3:
	// boolean isHasMoreRelationPerson =
	// articleDatas.getBoolean("hasMoreRelatePerson", false);
	//
	// if (isHasMoreRelationPerson) {
	// Intent intent = new Intent(mContext, MoreRelationPersonActivity.class);
	// intent.putExtra(ExtraKeys.Key_Msg1, 1);
	// jumpTo(intent);
	// } else {
	// scanUserBlog(userId);
	// }
	// break;
	// default:
	// }
	//
	// }
	// };
	private void setWebViewFeature() {
		WebSettings webSettings = find_contents_wb.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setAllowContentAccess(true);
		webSettings.setAppCacheEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setEnableSmoothTransition(true);
		// webSettings.setBuiltInZoomControls(true);
		webSettings.setLightTouchEnabled(true);
		// webSettings.setSupportZoom(true);
		find_contents_wb.setHapticFeedbackEnabled(true);
		// register_agreement_wv.setWebViewClient(new MyWebUriViewClient());
		find_contents_wb.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					myProgressBar.setVisibility(View.GONE);
					if (relationProductAdapter != null)
						relationProductAdapter.notifyDataSetChanged();
				} else {
					if (newProgress == 50) {
						if (relationProductAdapter != null)
							relationProductAdapter.notifyDataSetChanged();
					}
					if (View.VISIBLE != myProgressBar.getVisibility()) {
						myProgressBar.setVisibility(View.VISIBLE);
					}
					myProgressBar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

		});
		webSettings.setUseWideViewPort(false);
		webSettings.setLoadWithOverviewMode(true);
	}

	/**
	 * 分享
	 */
	public void share_iv_click() {
		if(articleDatas==null){
			return;
		}
		String shareLogo = articleDatas.getThumbnailPath();
		String shareTitle = articleDatas.getArticleName();
		String shareContent = articleDatas.getArticleAbstract();

		String shareUrl = "pages/mobile_page/commodity_article.html?articleId=" + articleDatas.getArticleId();
		ShareSocialUtils.showShareCompont(mContext, shareLogo, shareUrl, shareTitle, shareContent);
	}

	/**
	 * 收藏文章
	 */
	private void collect_tv_click() {
		if(articleDatas==null){
			return;
		}
		getDate_GetFocusOn(callBack, articleDatas.getArticleId(), Constant.CollectionType.Article, mContext);
	}

	/**
	 * 所有评论
	 *
	 * @param view
	 */
	public void msg_iv_click(View view) {
		// if (TextUtils.isEmpty(comments) || comments.equalsIgnoreCase("0")) {
		// return;
		// }
		if(articleDatas==null){
			return;
		}
		Intent intent = new Intent(this, ArticalCommentsActivity.class);
		intent.putExtra(ExtraKeys.Key_Msg1, articleDatas.getArticleId());
		intent.putExtra(ExtraKeys.Key_Msg2, articleDatas.getArticleName());
		jumpTo(intent, false);
	}

	/**
	 * 所有评论
	 */
	public void more_iv_click() {
		if (popupWindows != null && popupWindows.isShowing()) {
			popupWindows.dismiss();
		} else {
			if (popupWindows == null) {
				popupWindows = new MyPopWindowView(mContext);
			}
			if (isCollected) {
				collect_tv.setText(R.string.cancel_collection);
			} else {
				collect_tv.setText(R.string.collection);
			}
			popupWindows.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			popupWindows.update();
		}

	}

	private MyPopWindowView popupWindows;
	private TextView collect_tv;
	private TextView share_tv;
	private TextView cancel_tv;

	public class MyPopWindowView extends PopupWindow {

		public MyPopWindowView(Context mContext) {
			View view = View.inflate(mContext, R.layout.artical_more_menu, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.select_photo);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			setOutsideTouchable(true);
			setContentView(view);

			collect_tv = (TextView) view.findViewById(R.id.collect_tv);
			share_tv = (TextView) view.findViewById(R.id.share_tv);
			cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
			// share_tv.setOnClickListener(myclick);
			// cancel_tv.setOnClickListener(myclick);
			// collect_tv.setOnClickListener(myclick);
			collect_tv.setOnClickListener(myclick);
			share_tv.setOnClickListener(myclick);
			cancel_tv.setOnClickListener(myclick);
			if (isCollected) {
				collect_tv.setText(R.string.cancel_collection);
			}

			RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rlt_pop);
			layout.setOnClickListener(myclick);
		}
	}

	/**
	 * “我的”点击事件
	 */
	private View.OnClickListener myclick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			popupWindows.dismiss();
			switch (v.getId()) {
				case R.id.share_tv:
					share_iv_click();
					break;
				case R.id.collect_tv:
					collect_tv_click();
					break;
				case R.id.cancel_tv:
					break;

				default:
					break;
			}

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 点击banck见
	 *
	 * @return
	 */
	@Override
	public void onBackPressed() {
		if (popupWindows != null && popupWindows.isShowing()) {
			popupWindows.dismiss();
		} else {
			popupWindows=null;
			if (EasyUtils.isSingleActivity(mContext)) {
				Intent intent = new Intent(mContext, SplashActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
				jumpTo(intent,false);
			}
			super.onBackPressed();
		}
	}

}