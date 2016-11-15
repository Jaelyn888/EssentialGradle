package com.yishanxiu.yishang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.yishanxiu.yishang.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.adapter.ArticalSearchAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.getdata.GetDataQueue.MediaType;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.article.ArticleModel;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.yishanxiu.yishang.utils.ModleUtils;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by Jaelyn on 16/2/23. 发现搜索页面
 * <p/>
 * Update @author FangDongzhang 2016/7/23
 */
public class ArticalSearchActivity extends BaseActivity {
	/**
	 * 搜索框的隐藏与显示
	 */
	@ViewInject(id = R.id.linearlayout_search)
	private LinearLayout linearlayout_search;
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

	@ViewInject(id = R.id.ptr_list)
	private PullToRefreshListView ptr_list;
	private ArticalSearchAdapter findSearchAdapter;

	/**
	 * 无相关数据
	 */
	// @ViewInject(id = R.id.nodata_layout, click = "nodata_layout_click")
	// private LinearLayout nodata_layout;

	private List<ArticleModel> dataList = new ArrayList<ArticleModel>();
	private String articleContent = ""; // 搜索框中的字

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artical_search_layout);
		articleContent = getIntent().getStringExtra(ExtraKeys.Key_Msg1);
		findSearchAdapter = new ArticalSearchAdapter(mContext);
		ptr_list.setAdapter(findSearchAdapter);

		ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
		ptr_list.setOnRefreshListener(loadMoreListener);
		ptr_list.setOnItemClickListener(onItemClickListener);
		linearlayout_search.setVisibility(View.VISIBLE);
		search_ed.setOnEditorActionListener(editorActionListener);
	}

	private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				articleContent = search_ed.getText().toString();
				if (TextUtils.isEmpty(articleContent)) {
					toast.showToast(R.string.search_key_null);
					return true;
				} else {
					loadingToast.show();
					getServerData(0);
					return true;
				}
			}
			return false;
		}
	};

	/**
	 * item 点击监听
	 */
	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {
			if (itemPosition > 0) {
				ArticleModel articleModel = dataList.get(itemPosition - 1);

				Intent intent = new Intent(mContext, ArticalDetailActivity.class);
				intent.putExtra(ExtraKeys.Key_Msg1, articleModel);
				jumpTo(intent, false);
			}
		}
	};

	private PullToRefreshBase.OnRefreshListener2<ListView> loadMoreListener = new PullToRefreshBase.OnRefreshListener2<ListView>() {
		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			getServerData(1);
		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			getServerData(2);
		}
	};

	/**
	 * 开始搜索 { "articleContent":"测试", "createTime": "1467252002724", "requestType": "2" }
	 */
	private void getServerData(int requestType) {
		HashMap<String, Object> params = new HashMap<>();

		if (dataList.size() == 0) {
			requestType = 0;
		}
		if (requestType == 0) {
			//params.put("createTime", "");
		} else if (requestType == 1) {
			params.put("createTime", dataList.get(0).getCreateTime());
		} else {
			params.put("createTime", dataList.get(dataList.size() - 1).getCreateTime());
		}

		params.put("requestType", String.valueOf(requestType));
		params.put("articleContent", articleContent);
		GetDataQueue queue = new GetDataQueue();
		queue.setTag(requestType);
		queue.setActionName(GetDataConfing.Action_GetSearchArtical);
		queue.setMediaType(MediaType.JSON);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_GetSearchArtical);
		getDataUtil.getData(queue);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			ptr_list.onRefreshComplete();
			if (entity.getWhat() == GetDataConfing.What_GetSearchArtical) {
				TypeToken<BaseResponse<List<ArticleModel>>> typeToken = new TypeToken<BaseResponse<List<ArticleModel>>>() {};
				BaseResponse<List<ArticleModel>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					if (entity.getWhat() == GetDataConfing.What_GetSearchArtical) {
						List<ArticleModel> temp_data = baseResponse.getInfo();
						setAdapterData(temp_data, (Integer) entity.getTag());
						validateData();
					}
				}

			}

		}
	};

	/**
	 * 校验数据 显示无数据等 是否请求正常 网络错误且数据为空的话显示网络异常界面 提供刷新的接口
	 *
	 * @param
	 */
	private void validateData() {
		// if (dataList == null || dataList.size() == 0) {
		// ptr_list.setVisibility(View.GONE);
		// nodata_layout.setVisibility(View.VISIBLE);
		// } else {
		// ptr_list.setVisibility(View.VISIBLE);
		// nodata_layout.setVisibility(View.GONE);
		// }
	}

	private void setAdapterData(List<ArticleModel> temp_data, int requestType) {
		findSearchAdapter.setSpecialStr(articleContent);
		if (requestType == 0) {
			if (temp_data.size() == 0) {
				toast.showToast(R.string.nodata);
				ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
			} else {
				ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
				dataList = temp_data;
				findSearchAdapter.setDatas(dataList);
				findSearchAdapter.notifyDataSetChanged();
			}

		} else if (requestType == 1) {
			if (!temp_data.isEmpty()) {
				if (temp_data.size() >= 10) {
					dataList.clear();
				}
				dataList.addAll(0, temp_data);
				findSearchAdapter.setDatas(dataList);
				findSearchAdapter.notifyDataSetChanged();
			}
		} else {
			if (temp_data.size() == 0) {
				ptr_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
			} else {
				ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
				dataList.addAll(temp_data);
				findSearchAdapter.setDatas(dataList);
				findSearchAdapter.notifyDataSetChanged();
			}
		}
	}

	public void clear_search_iv_click(View view) {
		search_ed.setText("");
	}

	/**
	 * 搜索
	 *
	 * @param view
	 */
	public void do_search_iv_click(View view) {
		articleContent = search_ed.getText().toString();
		if (TextUtils.isEmpty(articleContent)) {
			toast.showToast(R.string.search_key_null);
		} else {
			loadingToast.show();
			getServerData(0);
		}
	}

	/**
	 * 取消搜索
	 */
	public void tv_search_cancel_click(View view) {
		finish();
	}
}