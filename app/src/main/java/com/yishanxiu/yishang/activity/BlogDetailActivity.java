package com.yishanxiu.yishang.activity;

import java.util.ArrayList;
import java.util.List;

import com.umeng.socialize.UMShareAPI;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.adapter.BlogAdapter;
import com.yishanxiu.yishang.utils.DisplayUtil;
import com.yishanxiu.yishang.utils.ShareSocialUtils;
import com.yishanxiu.yishang.view.PullScrollView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.json.JsonMap;
import com.yishanxiu.yishang.R;

/**
 * 个人博客详情页 2016/3/25
 * 
 * @author FangDongzhang
 * 
 */
@SuppressLint("InflateParams")
public class BlogDetailActivity extends BaseActivity implements PullScrollView.OnTurnListener {

	private static BlogDetailActivity instance = null;

	public static BlogDetailActivity getInstance() {
		if (instance == null) {
			instance = new BlogDetailActivity();
		}
		return instance;
	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			more.setVisibility(View.VISIBLE);
			ph_progressBar.setVisibility(View.INVISIBLE);
		}
	};
	
	@ViewInject(id = R.id.scrollview_topic_layout)
	RelativeLayout relativeLayout;
	@ViewInject(id = R.id.rllt_search)
	RelativeLayout rllt_search;
	@ViewInject(id = R.id.back, click = "back")
	ImageView back;
	@ViewInject(id = R.id.search, click = "search")
	ImageView search;
	@ViewInject(id = R.id.more, click = "more")
	ImageView more;
	@ViewInject(id = R.id.ph_progressBar, click = "ph_progressBar")
	ProgressBar ph_progressBar;
	@ViewInject(id = R.id.text_center_name, click = "text_center_name_click")
	TextView text_center_name;
	@ViewInject(id = R.id.cancel, click = "cancel")
	TextView cancel;
	@ViewInject(id = R.id.editText_search, click = "editText_search")
	EditText editText_search;
	@ViewInject(id = R.id.search_clear, click = "search_clear")
	private ImageButton search_clear;
	@ViewInject(id = R.id.attention_txt, click = "attention_txt")
	TextView attention_txt;
	@ViewInject(id = R.id.ph_llt)
	private ListView listView;
	private PullScrollView mScrollView;
	private ImageView mHeadImg;
	private Context context;
	public static boolean head_click = false;
	private BlogAdapter blogAdapter;
	
//	private PullToRefreshView mPullToRefreshView;
	@SuppressLint({ "ResourceAsColor", "UseValueOf" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blogdetail);
//		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.ph_pull_refresh_view);
		relativeLayout.getBackground().setAlpha(0);
		context = BlogDetailActivity.this;
		head_click = true;

		blogAdapter = new BlogAdapter(this);
		blogAdapter.setItemCompontClickListener(null);
		listView.setAdapter(blogAdapter);
		// FragmentManager manager = getSupportFragmentManager();
		// FragmentTransaction transaction = manager.beginTransaction();
		// ContentFragment fragment = new ContentFragment();
		// transaction.add(R.id.ph_llt, fragment);
		// transaction.commit();
		initView();
		initTitle();
		
//		mPullToRefreshView.setOnHeaderRefreshListener(this);
//		mPullToRefreshView.setOnFooterRefreshListener(this);

	}

	/**
	 * title
	 */
	private void initTitle() {
		mScrollView.setOnScrollChangedListener(new PullScrollView.OnScrollChangedListener() {

			@SuppressLint("UseValueOf")
			@Override
			public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
				int height = 480;
				if (t < height) {
					text_center_name.setVisibility(View.GONE);
					int alpha = (int) (new Float(t) / new Float(height) * 255);
					relativeLayout.getBackground().setAlpha(alpha);
				} else {
					text_center_name.setVisibility(View.VISIBLE);
					text_center_name.setText("方东");
					relativeLayout.getBackground().setAlpha(255);
				}
			}
		});
	}

	/**
	 * 返回
	 * 
	 * @param v
	 */
	public void back(View v) {
		finish();
	}

	/**
	 * 取消
	 * 
	 * @param v
	 */
	public void cancel(View v) {
		rllt_search.setVisibility(View.GONE);
//		rllt_search.setAlpha(0);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText_search.getWindowToken(), 0);
	}

	/**
	 * 搜索
	 * 
	 * @param v
	 */
	public void search(View v) {
		rllt_search.setVisibility(View.VISIBLE);
//		rllt_search.setAlpha(255);
		// 点击软键盘搜索
		editview_search();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText_search, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 搜索输入框
	 */
	public void editText_search(View v) {
		editText_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					search_clear.setVisibility(View.VISIBLE);
				} else {
					search_clear.setVisibility(View.INVISIBLE);
				}
			}
		});
	}

	/**
	 * 清 除搜索内容
	 */
	public void search_clear(View v) {
		editText_search.getText().clear();
		hideSoftKeyboard();
	}

	/**
	 * 更多
	 * 
	 * @param v
	 */
	public void more(View v) {
		showPopWindowView();
	}

	public PopupWindow popupWindow;
	private TextView collect_tv;
	private TextView share_tv;
	private TextView cancel_tv;

	private void showPopWindowView() {
		if (popupWindow == null) {
			View view = getLayoutInflater().inflate(R.layout.artical_more_menu, null);
			popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
			popupWindow.setContentView(view);
			popupWindow.setAnimationStyle(R.style.popupWindowAnimation);
			collect_tv = (TextView) view.findViewById(R.id.collect_tv);
			collect_tv.setText("分享");
			share_tv = (TextView) view.findViewById(R.id.share_tv);
			share_tv.setText("举报");
			cancel_tv = (TextView) view.findViewById(R.id.cancel_tv);
			// if (isCollection) {
			// collect_tv.setText(R.string.cancel_collection);
			// }
			View select_v = view.findViewById(R.id.select_v);
			collect_tv.setOnClickListener(myclick);
			share_tv.setOnClickListener(myclick);
			select_v.setOnClickListener(myclick);
			cancel_tv.setOnClickListener(myclick);
			popupWindow.showAtLocation(more, Gravity.NO_GRAVITY, 0, 0);

		} else if (!popupWindow.isShowing()) {
			popupWindow.showAtLocation(more, Gravity.NO_GRAVITY, 0, 0);
		}
	}

	/**
	 * “我的”点击事件
	 */
	View.OnClickListener myclick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			popupWindow.dismiss();
			switch (id) {
			case R.id.share_tv:
				break;
			case R.id.collect_tv:
				// share_iv_click();
				break;
			case R.id.select_v:
				break;
			case R.id.cancel_tv:
				popupWindow.dismiss();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 分享
	 */
	private String shareContent;
	private String discoredId;// 发现id
	@SuppressWarnings("rawtypes")
	private JsonMap findDatas;

	public void share_iv_click() {
		@SuppressWarnings("rawtypes")
		JsonMap jsonMap_temp = findDatas;
		String shareLogo = jsonMap_temp.getStringNoNull("sharePic");
		String shareTitle = jsonMap_temp.getStringNoNull("shareTitle");
		shareContent = jsonMap_temp.getStringNoNull("shareSummary");
		if (TextUtils.isEmpty(shareContent)) {
			shareContent = jsonMap_temp.getStringNoNull("shareTitle");
		}
		String shareUrl = "FindBrand/FindBrandDetail?disId=" + discoredId;

		ShareSocialUtils.showShareCompont(this, shareLogo, shareUrl, shareTitle, shareContent);
	}

	/**
	 * 搜索处理
	 * 
	 * @param v
	 */
	public void editview_search() {

		editText_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView arg0, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEND
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					// 调用借口方法
					Toast.makeText(context, "请调用接口", Toast.LENGTH_SHORT).show();
					// return true;
				}

				return false;
			}
		});

	}

	/**
	 * 关注
	 * 
	 * @param v
	 */
	public void attention_txt(View v) {
		// Toast.makeText(context, "关注", Toast.LENGTH_SHORT).show();
		// 新建一个popwindow，并显示里面的内容
		PopupWindow popupWindow = makePopupWindow(context);
		popupWindow.showAsDropDown(attention_txt, 2, 30);
	}

	@SuppressWarnings("deprecation")
	public PopupWindow makePopupWindow(Context cx)

	{
		PopupWindow window;

		window = new PopupWindow(cx);

		ListView listview = new ListView(this);

		listview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		listview.setAdapter(new ArrayAdapter<String>(cx, android.R.layout.simple_expandable_list_item_1, getData()));

		LinearLayout linearLayout = new LinearLayout(this);

		listviewclick(listview);

		linearLayout.addView(listview);

		linearLayout.setOrientation(LinearLayout.VERTICAL);

		window.setContentView(linearLayout); // 选择布局方式

		// 设置popwindow的背景图片

		window.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_shape_white));

		// 设置popwindow的高和宽

		window.setWidth(DisplayUtil.screenWidth / 3);

		window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

		// 设置PopupWindow外部区域是否可触摸

		window.setFocusable(true); // 设置PopupWindow可获得焦点

		window.setTouchable(true); // 设置PopupWindow可触摸

		window.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸

		return window;

	}

	private void listviewclick(ListView listview) {

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				switch (arg2) {
				case 0:
//					Intent intent = new Intent(context, AttentionGroupActivity.class);
//					startActivity(intent);
					break;

				default:
					break;
				}
			}
		});

	}

	// 构造数组的函数

	public List<String> getData() {

		List<String> data = new ArrayList<String>();

		data.add("分组");

		data.add("取消关注");

		return data;

	}

	protected void initView() {
		mScrollView = (PullScrollView) findViewById(R.id.scroll_view);
		mHeadImg = (ImageView) findViewById(R.id.background_img);

		mScrollView.setHeader(mHeadImg);
		mScrollView.setOnTurnListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/**使用SSO授权必须添加如下代码 */
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		head_click = false;
	}

	@Override
	public void onPull(float y,boolean b) {
		if(y>80 && b == false){
			more.setVisibility(View.INVISIBLE);
			ph_progressBar.setVisibility(View.VISIBLE);
			handler.postDelayed(runnable, 3000);
		}
	}

	@Override
	public void onUp(float y) {
		
	}

	@Override
	public void onRefresh() {
	}

//	@Override
//	public void onFooterRefresh(PullToRefreshView view) {
//		mPullToRefreshView.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				mPullToRefreshView.onFooterRefreshComplete();
//			}
//		}, 1000);
//	}
//
//	@Override
//	public void onHeaderRefresh(PullToRefreshView view) {
//		mPullToRefreshView.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				mPullToRefreshView.onHeaderRefreshComplete(""+System.currentTimeMillis());
////				mPullToRefreshView.onHeaderRefreshComplete();
//			}
//		}, 1000);
//	}
}
