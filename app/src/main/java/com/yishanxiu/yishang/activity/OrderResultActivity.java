package com.yishanxiu.yishang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.RelationProductAdapter2;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.ModleUtils;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.view.GridViewNoScroll;

import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderResultActivity extends BaseUIActivity {

	private LinearLayout recepientLayout;
	/**
	 * 收货人
	 */
	private TextView tv_shouhuren;
	/**
	 * 收货人电话
	 */
	private TextView tv_shouhuren_dianhua;
	/**
	 * 收货人地址
	 */
	private TextView address_lable_tv;
	private TextView tv_shouhuren_dizhi;
	//总金额
	private TextView totle_money_tv;
	//订单详情
	private TextView order_detail_tv;
	//继续购买
	private TextView continue_buy_tv;

	private GridViewNoScroll relationProductGv;
	private PullToRefreshScrollView ptr_scrollview;

	/**
	 * 商品
	 */
	private List<ProductInfoModel> dataList = new ArrayList<>();
	private RelationProductAdapter2 relationProductAdapter;
	private int nPage = 0;

	private JsonMap<String, Object> data;
	private String productTypeId;
	private String orderId, orderNum, productName;
	private double totalPrice;
	private int type; //0:单订单 ，1 多订单

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_pay_result);
		String result = getIntent().getStringExtra(ExtraKeys.Key_Msg1);
		//result="{\"actualPayPrice\":0.02,\"payId\":1,\"shopCartVoList\":[{\"isAllSelected\":\"null\",\"shopFreight\":0.01,\"shopProductCount\":1,\"shopId\":1,\"name\":\"奕赏\",\"shopSelectedTotalActivityPrice\":0.01,\"skuShopCartVo\":[{\"specificationValue\":\"通用码:180/92A  颜色:黑色  均码:均码\",\"createTime\":\"2016-08-25 18:48:40.264\",\"saleStatus\":1,\"isDeleted\":0,\"productId\":17162,\"createUser\":14040,\"activityPrices\":\"null\",\"productCount\":1,\"productSkuId\":23545,\"articleNumber\":\"33\",\"isSelected\":1,\"shopId\":1,\"modifyUser\":18245,\"userId\":14040,\"path\":\"http://essential-test.oss-cn-shanghai.aliyuncs.com/20160823/product/1471917968946bc337c9e5.jpg\",\"shopcartId\":482,\"prices\":0.01,\"productName\":\"测试8/23\",\"modifyTime\":\"2016-08-25 18:48:40.264\"}],\"shopSelectedProductPrice\":0.01,\"shoptypeName\":\"null\",\"logoPath\":\"http://essential-test.oss-cn-shanghai.aliyuncs.com/20160812/shop/1470990727720866d34c78.jpg\"}],\"flag\":1,\"orderId\":195,\"userReceiptAddress\":{\"provinceId\":116,\"detail\":\"Baoshan\",\"phone\":\"13365169690\",\"consigneeAddress\":\"北京市 北京市 东城区Baoshan\",\"districtId\":15571,\"isdefault\":1,\"cityId\":1148,\"consignee\":\"Wei\",\"userId\":14040,\"provincesRegions\":\"北京市 北京市 东城区\",\"addressId\":9507},\"externalOrderNumber\":\"ESAE2610163714040\"}";
		data = JsonParseHelper.getJsonMap(result);
		initCompont();
		type = data.getInt("type");
		orderId = data.getStringNoNull("orderId");
		orderNum = data.getStringNoNull("externalOrderNumber");
		totalPrice = data.getDouble("actualPayPrice", 0d);
		flushShouHuoRen(data.getJsonMap("userReceiptAddress"));
		totle_money_tv.setText(initMoney(StringUtils.getFormatMoneyWithSign(totalPrice)));

		ArrayList<JsonMap<String, Object>> productList = (ArrayList<JsonMap<String, Object>>) data.getList_JsonMap("shopCartVoList");
		JsonMap<String, Object> productMap = productList.get(0);
		productTypeId = productMap.getStringNoNull("productId");
		ptr_scrollview.setOnRefreshListener(list_loadMoreListener);
		relationProductGv.setOnItemClickListener(onItemClickListener);
		relationProductAdapter = new RelationProductAdapter2(mContext);
		relationProductAdapter.setShowDiscri(true);
		relationProductGv.setAdapter(relationProductAdapter);
		getServiceData(true);
	}

	private SpannableStringBuilder initMoney(String value) {
		SpannableStringBuilder style = new SpannableStringBuilder(value);
		style.setSpan(new TextAppearanceSpan(mContext, R.style.style1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return style;
	}

	private void initCompont() {
		ptr_scrollview = (PullToRefreshScrollView) findViewById(R.id.ptr_scrollview);
		relationProductGv = (GridViewNoScroll) findViewById(R.id.relation_product_gv);
		recepientLayout = (LinearLayout) findViewById(R.id.recepientLayout);
		tv_shouhuren = (TextView) findViewById(R.id.s_a_o_tv_shouhuoren);
		//收货人电话
		tv_shouhuren_dianhua = (TextView) findViewById(R.id.s_a_o_tv_phone);
		//收货人地址
		address_lable_tv= (TextView) findViewById(R.id.address_lable_tv);
		tv_shouhuren_dizhi = (TextView) findViewById(R.id.s_a_o_tv_address);
		address_lable_tv.setTextColor(getResources().getColor(R.color.black));
		tv_shouhuren_dizhi.setTextColor(getResources().getColor(R.color.black));
		//总金额
		totle_money_tv = (TextView) findViewById(R.id.totle_money_tv);
		//订单详情
		order_detail_tv = (TextView) findViewById(R.id.order_detail_tv);
		//继续购买
		continue_buy_tv = (TextView) findViewById(R.id.continue_buy_tv);
		order_detail_tv.setOnClickListener(clickListener);
		continue_buy_tv.setOnClickListener(clickListener);
	}

	private void flushShouHuoRen(JsonMap<String, Object> userReceiptAddress) {
		recepientLayout.setVisibility(View.VISIBLE);
		String shr = userReceiptAddress.getStringNoNull("consignee");
		String dh = userReceiptAddress.getStringNoNull("phone");
		String dz = userReceiptAddress.getStringNoNull("consigneeAddress");

		tv_shouhuren.setText(shr);
		tv_shouhuren_dianhua.setText(dh);
		tv_shouhuren_dizhi.setText(dz);
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int id = view.getId();
			if (id == R.id.order_detail_tv) {
				if (type == 1) {
					jumpTo(MyOrderActivity2.class, true);
				} else {
					Intent intentSeeLogistics = new Intent(mContext, MyOrderDetailActivity.class);
					intentSeeLogistics.putExtra(Constant.ORDER_ID, orderId);
					jumpTo(intentSeeLogistics, true);
				}
			} else if (id == R.id.continue_buy_tv) {
				Intent intent=new Intent(mContext,MainActivity2.class);
				intent.putExtra(ExtraKeys.Key_Msg1,1);
				intent.putExtra(ExtraKeys.Key_Msg2,1);
				jumpTo(intent);
			}
		}
	};

	/**
	 * 商品 item的点击
	 */
	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ProductInfoModel productInfoModel= dataList.get(position);
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
	 * 获取网络数据 private int nPage = 0; /**
	 *
	 * @param chearAllData
	 */
	private void getServiceData(boolean chearAllData) {
		if (chearAllData) {
			nPage = 0;
		}
		Map<String, Object> params = new HashMap<>();

		params.put("pageIndex", nPage + 1 + "");
		params.put("productId", productTypeId);
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_GetProudctCategroyList);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_GetProudctCategroyList);
		getDataUtil.getData(queue);
	}

	/**
	 * 设置适配器数据
	 *
	 * @param data
	 */
	private void setAdapterData(List<ProductInfoModel> data) {
		if (data.size() <10) {
			ptr_scrollview.setMode(PullToRefreshBase.Mode.DISABLED);
		} else {
			ptr_scrollview.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		}
		if (nPage == 0) {
			this.dataList = data;
		} else {
			this.dataList.addAll(data);
		}
		relationProductAdapter.setDatas(dataList);
		relationProductAdapter.notifyDataSetChanged();
		nPage++;
	}

	/**
	 * 获取服务器数据的返回信息
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			if (entity.what == GetDataConfing.What_GetProudctCategroyList) {
				TypeToken<BaseResponse<List<ProductInfoModel>>> typeToken=new TypeToken<BaseResponse<List<ProductInfoModel>>>(){};
				BaseResponse<List<ProductInfoModel>> baseResponse=new ModleUtils().mapTo(entity,typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					List<ProductInfoModel> data = baseResponse.getInfo();
					setAdapterData(data);
				}
			}
			loadingToast.dismiss();
			ptr_scrollview.onRefreshComplete();
		}
	};

}
