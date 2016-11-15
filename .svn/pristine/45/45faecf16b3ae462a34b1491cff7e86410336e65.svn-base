package com.yishanxiu.yishang.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.adapter.BlogAdapter;
import com.yishanxiu.yishang.adapter.MenuAdapter;
import com.yishanxiu.yishang.adapter.RelationPersonAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.DisplayUtil;
import com.yishanxiu.yishang.utils.Utils;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;
import com.yishanxiu.yishang.R;


/**
 * 时尚达人
 * 
 * @author FangDongzhang 2016/4/11
 */
public class FashionInsiderFragment extends LazyFragment {
	@ViewInject(R.id.ptr_list)
	private PullToRefreshListView ptr_list;

	/**
	 * 信息数据
	 */
	private ArrayList<JsonMap<String, Object>> datas = new ArrayList<JsonMap<String, Object>>();
	private RelationPersonAdapter blogAdapter;

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.content_fragment);
		ViewUtils.inject(this, getContentView());
	}

	@Override
	protected void onFragmentStartLazy() {
		// TODO Auto-generated method stub
		super.onFragmentStartLazy();
		blogAdapter = new RelationPersonAdapter(activity);
		// blogAdapter.setItemCompontClickListener(blogItemCompontClickListener);
		ptr_list.setAdapter(blogAdapter);
		ptr_list.setOnItemClickListener(itemClickListener);
		// loadingToast.show();
		// getServerData(0);
		ptr_list.setOnRefreshListener(loadMoreListener);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		}
	};

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
	 * 获取服务器端数据
	 *
	 * @param requestType
	 *            请求类型
	 */
	public void getServerData(int requestType) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		// params.put(GetDataQueue.Params_key, GetDataConfing.Key_Str);
		params.put("UserInfoId", Utils.getUserId(activity));

		params.put("currentPage", 1);
		params.put("pageSize", 10);
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_SelectVendorPostList);
		queue.setWhat(GetDataConfing.What_SelectVendorPostList);
		queue.setTag(requestType);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		getDataUtil.getData(queue);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {

		@Override
		public void onLoaded(GetDataQueue entity) {
			// xListView.stopLoadMore();
			// xListView.stopRefresh();
			if (entity.isOk()) {
				if (ShowGetDataError.isCodeSuccess(activity, entity.getInfo())) {
					if (entity.what == GetDataConfing.What_SelectVendorPostList) {
						ArrayList<JsonMap<String, Object>> temp_data = (ArrayList<JsonMap<String, Object>>) JsonParseHelper
								.getJsonMap_List_JsonMap(entity.getInfo(), JsonKeys.Key_Info);
						setAdapterData(temp_data, (Integer) entity.getTag());
						validateData();
					}
				}
			} else {
				ShowGetDataError.showNetError(activity);
			}
			loadingToast.dismiss();
		}

	};

	/**
	 * 校验数据 显示无数据等 是否请求正常 网络错误且数据为空的话显示网络异常界面 提供刷新的接口
	 *
	 * @param
	 */
	private void validateData() {
//		if (datas == null || datas.size() == 0) {
//			xListView.setVisibility(View.GONE);
//			nodataLayout.setVisibility(View.VISIBLE);
//		} else {
//			xListView.setVisibility(View.VISIBLE);
//			nodataLayout.setVisibility(View.GONE);
//		}
	}

	private void setAdapterData(ArrayList<JsonMap<String, Object>> temp_data, int requestType) {
		if (temp_data.size() == 0) {
			// xListView.setPullLoadEnable(false);
		} else {
			// xListView.setPullLoadEnable(true);
		}
		if (requestType == 0 || requestType == 1) {
			datas.addAll(0, temp_data);
		} else {
			datas.addAll(temp_data);
		}
		blogAdapter.setDatas(datas);
		blogAdapter.notifyDataSetChanged();
	}

	private BlogAdapter.ItemCompontClickListener blogItemCompontClickListener = new BlogAdapter.ItemCompontClickListener() {
		@Override
		public void onItemCompontClickListener(int position, int itemType, int index) {
			JsonMap jsonMap = new JsonMap<String, Object>();// .get(position);
			switch (itemType) {
			case Constant.BlogItemCompontType.ATTENTION:
				break;
			case Constant.BlogItemCompontType.RECOMMAND_OPTION:
				showPopWindowView(0, jsonMap.getStringNoNull("isCollection"), "");
				break;
			case Constant.BlogItemCompontType.ATTENTIONED_OPTION:
				showPopWindowView(1, jsonMap.getStringNoNull("isCollection"), jsonMap.getStringNoNull("isFocus"));
				break;
			case Constant.BlogItemCompontType.COMMENT:
				break;
			case Constant.BlogItemCompontType.SCAN_BIG_IMAGE:
				activity.goodsImage_click(null, index);
				break;
			case Constant.BlogItemCompontType.SCAN_DETAIL:
				break;
			case Constant.BlogItemCompontType.TRANSMIT:
				break;
			case Constant.BlogItemCompontType.USER_PHOTO:
				activity.scanUserBlog(jsonMap.getStringNoNull("userId"));
				break;
			default:
			}

		}
	};

	/**
	 * 操作的选项展示
	 */
	public PopupWindow popupWindow;
	private LinearLayout attention_layout;
	private TextView collect_tv;
	private TextView adjust_group_tv;
	private TextView focus_tv;
	private TextView report_tv;
	private TextView cancel_tv;

	/**
	 * 点击箭头 展示操作选项
	 * 
	 * @param i
	 *            item类型 推荐的还是关注的 0:推荐的
	 * @param isCollection
	 *            是否已收藏
	 * @param isFocus
	 *            已关注的或者取消的
	 */
	private void showPopWindowView(int i, String isCollection, String isFocus) {
		if (popupWindow == null || popupWindow != null && !popupWindow.isShowing()) {
			View view = activity.getLayoutInflater().inflate(R.layout.community_menu, null);
			popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			popupWindow.setContentView(view);
			popupWindow.setAnimationStyle(R.style.popupWindowAnimation);
			collect_tv = (TextView) view.findViewById(R.id.collect_tv);
			attention_layout = (LinearLayout) view.findViewById(R.id.attention_layout);
			adjust_group_tv = (TextView) view.findViewById(R.id.adjust_group_tv);
			focus_tv = (TextView) view.findViewById(R.id.focus_tv);
			report_tv = (TextView) view.findViewById(R.id.report_tv);
			cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
			if (isCollection.equals("1")) {
				collect_tv.setText(R.string.cancel_collection);
			}
			if (i == 0) {
				attention_layout.setVisibility(View.GONE);
			} else {
				attention_layout.setVisibility(View.VISIBLE);
				if (isFocus.equals("1")) {
					focus_tv.setText(R.string.cancel_focus);
				}
			}
			View select_v = view.findViewById(R.id.select_v);
			collect_tv.setOnClickListener(myclick);
			adjust_group_tv.setOnClickListener(myclick);
			focus_tv.setOnClickListener(myclick);
			report_tv.setOnClickListener(myclick);
			select_v.setOnClickListener(myclick);
			cancel_tv.setOnClickListener(myclick);
			// popupWindow.showAtLocation(theme1_layout, Gravity.NO_GRAVITY, 0,
			// 0);
		} else if (!popupWindow.isShowing()) {
			// popupWindow.showAtLocation(theme1_layout, Gravity.NO_GRAVITY, 0,
			// 0);
		}
	}

	/**
	 * “我的”点击事件
	 */
	View.OnClickListener myclick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			popupWindow.dismiss();
			switch (id) {
			case R.id.collect_tv:
				collect_tv_click();
				break;
			case R.id.adjust_group_tv:
				adjust_group_tv_click();
				break;
			case R.id.focus_tv:
				focus_tv_click();
				break;
			case R.id.report_tv:
				report_tv_click();
				break;

			case R.id.select_v:
				break;
			case R.id.cancel_tv:
				// 退出登录
				break;

			default:
				break;
			}
		}
	};

	private void report_tv_click() {

	}

	private void focus_tv_click() {

	}

	private void adjust_group_tv_click() {

	}

	private void collect_tv_click() {

	}

	private PopupWindow mSourceListPop;
	private boolean isOutTouch = false;

	/**
	 * 来源
	 *
	 * @param view
	 */
	@OnClick(R.id.item_left_tv)
	public void theme1_tv_click(View view) {
		if (mListPop != null && mListPop.isShowing()) {
			mListPop.dismiss();
		}
		isSortOutTouch = false;
		if (mSourceListPop != null && mSourceListPop.isShowing()) {
			mSourceListPop.dismiss();
			return;
		}
		if (isOutTouch) {
			isOutTouch = false;
			return;
		}
		ListView listView = new ListView(activity);
		listView.setDivider(activity.getResources().getDrawable(R.drawable.hor_line));
		int pad = getResources().getDimensionPixelSize(R.dimen.common_margin);
		listView.setPadding(pad, 0, pad, 0);
		MenuAdapter menuAdapter = new MenuAdapter(activity, sortData);
		listView.setAdapter(menuAdapter);
		// window.setFocusable(true);//如果不设置setFocusable为true，popupwindow里面是获取不到焦点的，那么如果popupwindow里面有输入框等的话就无法输入。
		mSourceListPop = new PopupWindow(listView, DisplayUtil.screenWidth / 2,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mSourceListPop.setBackgroundDrawable(getResources().getDrawable(R.drawable.cus_grey_border_frame));
		mSourceListPop.setOutsideTouchable(true);
		mSourceListPop.setTouchInterceptor(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					mSourceListPop.dismiss();
					isOutTouch = true;
				}
				return false;
			}
		});

		// mSourceListPop.showAsDropDown(theme1_layout);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// theme2_tv.setText(sortData.get(position).getStringNoNull("title"));
				mSourceListPop.dismiss();
			}
		});
	}

	private PopupWindow mListPop;
	private ArrayList<JsonMap<String, Object>> sortData;
	private boolean isSortOutTouch = false;

	/**
	 * 排序
	 *
	 * @param view
	 */
	@OnClick(R.id.item_right_tv)
	public void theme2_tv_click(View view) {
		if (mSourceListPop != null && mSourceListPop.isShowing()) {
			mSourceListPop.dismiss();
		}
		isOutTouch = false;
		if (mListPop != null && mListPop.isShowing()) {
			mListPop.dismiss();
			return;
		}

		if (isSortOutTouch) {
			isSortOutTouch = false;
			return;
		}
		ListView listView = new ListView(activity);
		listView.setDivider(activity.getResources().getDrawable(R.drawable.hor_line));
		int pad = getResources().getDimensionPixelSize(R.dimen.common_margin);
		listView.setPadding(pad, 0, pad, 0);
		MenuAdapter menuAdapter = new MenuAdapter(activity, sortData);
		listView.setAdapter(menuAdapter);
		mListPop = new PopupWindow(listView, DisplayUtil.screenWidth / 2,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mListPop.setBackgroundDrawable(getResources().getDrawable(R.drawable.cus_grey_border_frame));
		mListPop.setOutsideTouchable(true);
		mListPop.setSplitTouchEnabled(false);
		mListPop.setTouchInterceptor(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					mListPop.dismiss();
					isSortOutTouch = true;
				}
				return false;
			}
		});
		// mListPop.showAsDropDown(theme2_layout);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// theme2_tv.setText(sortData.get(position).getStringNoNull("title"));
				mListPop.dismiss();
			}
		});
	}

	/**
	 * 搜索图标
	 *
	 * @param view
	 */
	@OnClick(R.id.iv_search)
	public void iv_search_click(View view) {
		// Intent findSearchIntent=new
		// Intent(activity,FindSearchActivity.class);
		// activity.jumpTo(findSearchIntent,false);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (mListPop != null && mListPop.isShowing()) {
			mListPop.dismiss();
		}

		if (mSourceListPop != null && mSourceListPop.isShowing()) {
			mSourceListPop.dismiss();

		}

		isOutTouch = false;
		isSortOutTouch = false;
	}
}