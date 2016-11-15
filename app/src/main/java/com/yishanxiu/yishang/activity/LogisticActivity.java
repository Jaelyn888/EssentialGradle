package com.yishanxiu.yishang.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.LogisticAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;

/**
 * 查询物流
 * 
 * @author FangDongzhang 2016/7/11
 */
public class LogisticActivity extends BaseUIActivity {
	@ViewInject(R.id.logistics_companies)
	private TextView logistics_companies;

	@ViewInject(R.id.logistics_nu)
	private TextView logistics_nu;

	@ViewInject(R.id.listView_logistic)
	private ListView listView_logistic;

	private List<JsonMap<String, Object>> datas;
	private LogisticAdapter queryProgressAdapter;

	@ViewInject(R.id.scrollView)
	private PullToRefreshScrollView pullToRefreshScrollView;

	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logisitic);
		ViewUtils.inject(this);
		setCenter_title(getResources().getString(R.string.query_logistic));
		setRefreshLayoutListener();
		initAdapter();
		intent = getIntent();
		getServerData(intent.getStringExtra("logisticNumber"), intent.getStringExtra("logistic"));

		initData(intent.getStringExtra("logisticNumber"), intent.getStringExtra("logistic"));

		pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		pullToRefreshScrollView.setOnRefreshListener(loadMoreListener);
		pullToRefreshScrollView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_bg));
	}

	private PullToRefreshBase.OnRefreshListener2<ScrollView> loadMoreListener = new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
			getServerData(intent.getStringExtra("logisticNumber"), intent.getStringExtra("logistic"));
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		}
	};

	/**
	 * 刷新
	 */
	private void setRefreshLayoutListener() {
		/*
		 * swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
		 * 
		 * @Override public void onRefresh() {
		 * swipeRefreshLayout.setRefreshing(true); new Handler().postDelayed(new
		 * Runnable() {
		 * 
		 * @Override public void run() { // 刷新具体方法
		 * swipeRefreshLayout.setRefreshing(false); } }, 1500);
		 * 
		 * } });
		 * 
		 * // 加载监听器 swipeRefreshLayout.setOnLoadListener(new OnLoadListener() {
		 * 
		 * @Override public void onLoad() {
		 * 
		 * swipeRefreshLayout.postDelayed(new Runnable() {
		 * 
		 * @Override public void run() { // getServerData(false, orderStatus);
		 * swipeRefreshLayout.setLoading(false); } }, 1500);
		 * 
		 * } });
		 */}

	/**
	 * 适配数据
	 */
	private void initAdapter() {
		queryProgressAdapter = new LogisticAdapter(mContext);
		listView_logistic.setAdapter(queryProgressAdapter);
	}

	/**
	 * 查询进度
	 */
	private void getServerData(String nu, String com) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nu", nu);
		params.put("com", com);
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.Express_query);
		queue.setWhat(GetDataConfing.What_Express_query);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 获取服务器数据的返回信息
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			pullToRefreshScrollView.onRefreshComplete();
			loadingToast.dismiss();
			if (entity.isOk()) {
				if (entity.what == GetDataConfing.What_Express_query) {
					JsonMap<String, Object> jsonMap = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
							JsonKeys.Key_Info);
					datas = jsonMap.getList_JsonMap("data");
					setData(datas);

				}
			} else {
				ShowGetDataError.showNetError(mContext);
			}
		}
	};

	/**
	 * 适配数据
	 */
	private void setData(List<JsonMap<String, Object>> datas) {
		queryProgressAdapter.setDatas(datas);
		queryProgressAdapter.notifyDataSetChanged();
	}

	/**
	 * 初始化数据
	 */
	private void initData(String nu, String content) {
		if (content.equals("shunfeng")) {
			logistics_companies.setText("顺丰快递");
		} else if (content.equals("yuantong")) {
			logistics_companies.setText("圆通快递");
		} else if (content.equals("shentong")) {
			logistics_companies.setText("申通快递");
		} else if (content.equals("yunda")) {
			logistics_companies.setText("韵达快递");
		} else if (content.equals("zhongtong")) {
			logistics_companies.setText("中通快递");
		}
		logistics_nu.setText(nu);
	}
}
