package com.yishanxiu.yishang.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.activity.ArticalSearchActivity;
import com.yishanxiu.yishang.adapter.MenuAdapter;
import com.yishanxiu.yishang.app.MyApplication;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.Constant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yishanxiu.yishang.utils.DisplayUtil;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;
import com.yishanxiu.yishang.R;

/**
 * Created by Jaelyn on 2015/8/20 0020. 社区
 */
@SuppressLint("ClickableViewAccessibility")
public class CommunityFragment extends LazyFragment {
	@ViewInject(R.id.ptr_list)
	private PullToRefreshListView ptr_list;

	@ViewInject(R.id.main_top_layout) // title
	private LinearLayout main_top_layout;
	@ViewInject(R.id.title_menu_iv)
	private ImageView title_menu_iv;
	@ViewInject(R.id.item_left_layout) // title
	private LinearLayout item_left_layout;
	@ViewInject(R.id.item_right_layout) // title
	private RelativeLayout item_right_layout;
	@ViewInject(R.id.item_left_tv)
	private TextView item_left_tv;
	@ViewInject(R.id.item_right_tv)
	private TextView item_right_tv;
	@ViewInject(R.id.iv_search)
	private ImageView iv_search;
	/**
	 * 保存点击过得fragment
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<Integer, BaseFragment> fragments = new HashMap();

	private String leftId = "0";
	private String rightId = "0";

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.community_layout);
		ViewUtils.inject(this, getView());

	}

	@Override
	protected void onFragmentStartLazy() {
		super.onFragmentStartLazy();
		item_left_tv.setText(R.string.group);
		item_right_tv.setText(R.string.hot);
		title_menu_iv.setVisibility(View.GONE);
		loadingToast.show();
		getServerData(0);
		showLeftFragment();
	}

	/**
	 * 获取服务器端数据
	 *
	 * @param requestType
	 *            请求类型
	 */
	public void getServerData(int requestType) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_GetFindTopCategoryData);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_GetFindTopCategoryData);
		getDataUtil.getData(queue);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void onLoaded(GetDataQueue entity) {
			if (entity.isOk()) {
				if (ShowGetDataError.isCodeSuccess(activity, entity.getInfo())) {
					JsonMap data = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(), JsonKeys.Key_Info);
					leftData = (ArrayList<JsonMap<String, Object>>) data.getList_JsonMap("articalCategories");
					rightData = (ArrayList<JsonMap<String, Object>>) data.getList_JsonMap("specialSubjectCategories");
				}
			} else {
				ShowGetDataError.showNetError(activity);
			}
			loadingToast.dismiss();
		}

	};

	private PopupWindow mLeftPop;
	private ArrayList<JsonMap<String, Object>> leftData;
	private boolean isLeftOutTouch = false;

	/**
	 * 来源
	 *
	 * @param view
	 */
	@SuppressLint("ClickableViewAccessibility")
	@OnClick(R.id.item_left_layout)
	public void item_left_layout_click(View view) {
		if (currentFragment != fragments.get(0)) {
			if (isRightPutSide) { // 切换点击
				isRightPutSide = false;
				return;
			}
			showLeftFragment();
		} else {
			if (isLeftOutTouch) { // 当前点击
				isLeftOutTouch = false;
				return;
			}
			setSelectLeftRightStatus(2, 0);
			ListView listView = new ListView(activity);
			listView.setDivider(activity.getResources().getDrawable(R.drawable.hor_line));
			int pad = getResources().getDimensionPixelSize(R.dimen.common_margin);
			listView.setPadding(pad, 0, pad, 0);
			MenuAdapter menuAdapter = new MenuAdapter(activity, leftData);
			listView.setAdapter(menuAdapter);
			mLeftPop = new PopupWindow(listView, DisplayUtil.screenWidth / 2,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			mLeftPop.setOutsideTouchable(true);
			mLeftPop.setBackgroundDrawable(getResources().getDrawable(R.drawable.cus_grey_border_frame));
			// mLeftPop.setOutsideTouchable(true);
			mLeftPop.setTouchInterceptor(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						setSelectLeftRightStatus(1, 0);
						mLeftPop.dismiss();
						isLeftOutTouch = true;
						return true;
					}
					// mLeftPop.dismiss();
					return false;
				}
			});

			mLeftPop.showAsDropDown(item_left_layout, 0, 0);
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					mLeftPop.dismiss();
					item_left_tv.setText(leftData.get(position).getStringNoNull("categoryName"));
					leftId = leftData.get(position).getString("categoryId");
					fragments.remove(0);
					showLeftFragment();
				}
			});
		}
	}

	private void showLeftFragment() {
		title_menu_iv.setVisibility(View.GONE);
		setSelectLeftRightStatus(1, 0);
		if (fragments.get(0) != null) {
			replaceView(fragments.get(0));
		} else {
			ContentFragment contentFragment = new ContentFragment();
			bundle.putString(Constant.ID, leftId);
			contentFragment.setArguments(bundle);
			replaceView(contentFragment);
			fragments.put(0, contentFragment);
		}
	}

	/**
	 * 设置左侧按钮状态 0:不选中,1:选中,2:下拉弹框,
	 *
	 * @param i
	 */
	private void setSelectLeftRightStatus(int i, int j) {
		Drawable popStateDrawable1 = getResources().getDrawable(R.drawable.pop_none);
		Drawable popStateDrawable2 = getResources().getDrawable(R.drawable.pop_down_grey);
		Drawable popStateDrawable3 = getResources().getDrawable(R.drawable.pop_down_white);

		Drawable bottomStateDrawable1 = getResources().getDrawable(R.drawable.hor_line_trans_2);
		Drawable bottomStateDrawable2 = getResources().getDrawable(R.drawable.hor_line_white_2);

		// 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
		popStateDrawable2.setBounds(0, 0, popStateDrawable2.getMinimumWidth(), popStateDrawable2.getMinimumHeight());
		popStateDrawable3.setBounds(0, 0, popStateDrawable3.getMinimumWidth(), popStateDrawable3.getMinimumHeight());

		bottomStateDrawable1.setBounds(0, 0, bottomStateDrawable1.getMinimumWidth(),
				bottomStateDrawable1.getMinimumHeight());
		bottomStateDrawable2.setBounds(0, 0, bottomStateDrawable2.getMinimumWidth(),
				bottomStateDrawable2.getMinimumHeight());

		switch (i) {
		case 0:
			item_left_tv.setCompoundDrawables(null, null, popStateDrawable1, null);
			item_left_layout.setBackgroundResource(R.drawable.nav_bar);
			break;
		case 1:
			item_left_layout.setBackgroundResource(R.drawable.nav_bar);
			item_left_tv.setCompoundDrawables(null, null, popStateDrawable2, bottomStateDrawable2);
			break;
		case 2:
			item_left_layout.setBackgroundResource(R.drawable.nav_bar_selected);
			item_left_tv.setCompoundDrawables(null, null, popStateDrawable3, bottomStateDrawable1);
			break;
		default:
		}
		switch (j) {
		case 0:
			item_right_layout.setBackgroundResource(R.drawable.nav_bar);
			item_right_tv.setCompoundDrawables(null, null, popStateDrawable1, null);
			break;
		case 1:
			item_right_layout.setBackgroundResource(R.drawable.nav_bar);
			item_right_tv.setCompoundDrawables(null, null, popStateDrawable2, bottomStateDrawable2);
			break;
		case 2:
			item_right_layout.setBackgroundResource(R.drawable.nav_bar_selected);
			item_right_tv.setCompoundDrawables(null, null, popStateDrawable3, bottomStateDrawable1);
			break;
		default:
		}

		if (i == 2 || j == 2) {
			item_left_layout.setClickable(false);
			item_right_layout.setClickable(false);
		} else {
			item_left_layout.setClickable(true);
			item_right_layout.setClickable(true);
		}
	}

	private PopupWindow mRightPop;
	private ArrayList<JsonMap<String, Object>> rightData;
	private boolean isRightPutSide = false;

	/**
	 * 排序
	 *
	 * @param view
	 */
	@OnClick(R.id.item_right_layout)
	public void item_right_layout_click(View view) {
		if ((currentFragment != fragments.get(1))) {
			if (isLeftOutTouch) {
				isLeftOutTouch = false;
				return;
			}
			if (fragments.get(1) == null) {
				rightId = rightData.get(0).getStringNoNull("categoryId");
			}
			showRightFragment();
		} else {
			if (isRightPutSide) {
				isRightPutSide = false;
				return;
			}
			setSelectLeftRightStatus(0, 2);
			ListView listView = new ListView(activity);
			listView.setDivider(activity.getResources().getDrawable(R.drawable.hor_line));
			int pad = getResources().getDimensionPixelSize(R.dimen.common_margin);
			listView.setPadding(pad, 0, pad, 0);
			MenuAdapter menuAdapter = new MenuAdapter(activity, rightData);
			listView.setAdapter(menuAdapter);
			mRightPop = new PopupWindow(listView, DisplayUtil.screenWidth / 2,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			mRightPop.setBackgroundDrawable(getResources().getDrawable(R.drawable.cus_grey_border_frame));
			mRightPop.setOutsideTouchable(true);
			mRightPop.setTouchInterceptor(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						setSelectLeftRightStatus(0, 1);
						mRightPop.dismiss();
						isRightPutSide = true;
						return true;
					}
					return false;
				}
			});
			mRightPop.showAsDropDown(item_right_layout);

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					item_right_tv.setText(rightData.get(position).getStringNoNull("categoryName"));
					mRightPop.dismiss();
					selectReplaceFragment(position);

				}
			});
		}
	}

	/**
	 * 根据popwindow中listview的item切换fragment
	 * 
	 * @param position
	 */
	private void selectReplaceFragment(int position) {
		switch (position) {
		case 0:
			rightId = rightData.get(position).getStringNoNull("categoryId");
			fragments.remove(1);
			showRightFragment();
			break;
		case 1:
			rightId = rightData.get(position).getStringNoNull("categoryId");
			fragments.remove(1);
			showRightFashionInsiderFragment();// Fragment();
			break;
		default:
			break;
		}
	}

	/**
	 * fragments 传值
	 */
	private Bundle bundle = new Bundle();

	private void showRightFragment() {
		title_menu_iv.setVisibility(View.GONE);

		setSelectLeftRightStatus(0, 1);
		if (fragments.get(1) != null) {
			replaceView(fragments.get(1));
		} else {
			ContentFragment articalFragment = new ContentFragment();
			bundle.putString(Constant.ID, rightId);
			articalFragment.setArguments(bundle);
			replaceView(articalFragment);
			fragments.put(1, articalFragment);
		}
	}

	/**
	 * fragments 传值
	 */
	private Bundle bundle2 = new Bundle();

	private void showRightFashionInsiderFragment() {
		title_menu_iv.setVisibility(View.GONE);

		setSelectLeftRightStatus(0, 1);
		if (fragments.get(1) != null) {
			replaceView(fragments.get(1));
		} else {
			FashionInsiderFragment articalFragment = new FashionInsiderFragment();
			bundle2.putString(Constant.ID, rightId);
			articalFragment.setArguments(bundle2);
			replaceView(articalFragment);
			fragments.put(1, articalFragment);
		}
	}

	private Fragment currentFragment;

	private void replaceView(BaseFragment fragment) {

		FragmentTransaction transaction = this.getFragmentManager().beginTransaction();
		// FragmentTransaction transaction =
		// mFragmentMan.beginTransaction().setCustomAnimations(
		// android.R.anim.fade_in, R.anim.slide_out);
		if (currentFragment != null) {
			if (!fragment.isAdded()) { // 先判断是否被add过
				transaction.hide(currentFragment).add(R.id.community_content_layout, fragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(currentFragment).show(fragment).commit(); // 隐藏当前的fragment，显示下一个
			}
		} else {
			transaction.add(R.id.community_content_layout, fragment).commit(); // add下一个到Activity中
		}
		currentFragment = fragment;
	}

	/**
	 * 搜索图标
	 *
	 * @param view
	 */
	@OnClick(R.id.iv_search)
	public void iv_search_click(View view) {
		Intent findSearchIntent = new Intent(activity, ArticalSearchActivity.class);
		activity.jumpTo(findSearchIntent, false);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		backKeyClick();
	}

	/**
	 * back 键处理
	 *
	 * @return true：消费此次返回 false 不处理
	 */
	public boolean backKeyClick() {
		isLeftOutTouch = false;
		isRightPutSide = false;
		if (mRightPop != null && mRightPop.isShowing()) {
			mRightPop.dismiss();
			setSelectLeftRightStatus(0, 1);
			return true;
		}

		if (mLeftPop != null && mLeftPop.isShowing()) {
			mLeftPop.dismiss();
			setSelectLeftRightStatus(1, 0);
			return true;
		}
		return false;
	}
}