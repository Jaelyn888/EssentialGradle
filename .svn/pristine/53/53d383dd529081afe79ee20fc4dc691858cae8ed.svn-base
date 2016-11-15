package com.yishanxiu.yishang.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.fragment.WebViewFragment;
import com.yishanxiu.yishang.utils.Constant;

/**
 * 网页加载
 */
public class UrlWebviewActivity extends BaseUIActivity implements View.OnClickListener {
	private WebViewFragment webViewFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_agreement);
		String url = getIntent().getStringExtra(Constant.INTENT_URL);
		int titleResId = getIntent().getIntExtra(Constant.Center_Title, R.string.app_name);
		webViewFragment = new WebViewFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constant.INTENT_URL, url);
		setCenter_title(titleResId);
		webViewFragment.setArguments(bundle);

		FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.webViewFragmentContainer, webViewFragment).commit();
		setBtn_backListener(this);
	}


	@Override
	public void onClick(View v) {
		if (webViewFragment.backKeyClick()) {
			return;
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onBackPressed() {
		if (webViewFragment.backKeyClick()) {
			return;
		} else {
			super.onBackPressed();
		}
	}

}