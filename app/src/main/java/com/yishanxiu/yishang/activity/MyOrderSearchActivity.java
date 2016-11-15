package com.yishanxiu.yishang.activity;



import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.fragment.MyOrderFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 订单 搜索
 * 
 * @author FangDongzhang 2016/8/2
 */
public class MyOrderSearchActivity extends BaseActivity {
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

	private String key = ""; // 搜索框中的字
	private MyOrderFragment fragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_search_layout);
		linearlayout_search.setVisibility(View.VISIBLE);
		search_ed.setOnEditorActionListener(editorActionListener);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		fragment = new MyOrderFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean("isSearch",true);
		fragment.setArguments(bundle);
		transaction.add(R.id.order_search_container, fragment);
		transaction.commit();
	}

	private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				key = search_ed.getText().toString();
				if (TextUtils.isEmpty(key)) {
					toast.showToast(R.string.search_key_null);
					return true;
				} else {
					loadingToast.show();
					doSearch();
					return true;
				}
			}
			return false;
		}
	};

	private void doSearch() {
		fragment.startSearch(key,true);
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
		key = search_ed.getText().toString();
		if (TextUtils.isEmpty(key)) {
			toast.showToast(R.string.search_key_null);
		} else {
			loadingToast.show();
			doSearch();
		}
	}

	/**
	 * 取消搜索
	 */
	public void tv_search_cancel_click(View view) {
		finish();
	}
}