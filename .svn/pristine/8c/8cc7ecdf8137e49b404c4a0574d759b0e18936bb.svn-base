package com.yishanxiu.yishang.fragment;

import android.webkit.WebChromeClient;
import android.widget.ProgressBar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 用户订单
 *
 * @author FangDongzhang 2016/7/6
 */
public class WebViewFragment extends LazyFragment {

	private ProgressBar myProgressBar;
	private WebView wvWebView;

	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.web_view_activity);
		wvWebView = (WebView) getContentView().findViewById(R.id.webView);
		myProgressBar = (ProgressBar) getContentView().findViewById(R.id.myProgressBar);
		setWebViewFeature();
		Bundle bundle = getArguments();
		initData(bundle);
	}


	public void initData(Bundle bundle) {
		WebSettings webSettings = wvWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		String url = bundle.getString(Constant.INTENT_URL);
		if (TextUtils.isEmpty(url)) {
			Log.e(TAG, "initData  StringUtil.isNotEmpty(url, true) == false >> finish(); return;");
			return;
		}

		wvWebView.requestFocus();
		wvWebView.loadUrl(url);
	}

	private void setWebViewFeature() {
		WebSettings webSettings = wvWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setAllowContentAccess(true);
		webSettings.setAppCacheEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setGeolocationEnabled(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setEnableSmoothTransition(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setLightTouchEnabled(true);
		webSettings.setSupportZoom(true);
		wvWebView.setHapticFeedbackEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		wvWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// if (url.contains("/ShopMall/Index?vid=")) {
				// int startIndex = url.indexOf("=");
				// String id = url.substring(startIndex + 1);
				// seeShopThemeDetail(id, "");
				// } else if (url.contains("/Orders/OrderDetail?orderNum=")) {
				// int startIndex = url.indexOf("=");
				// String id = url.substring(startIndex + 1);
				// seeOrderDetail("", id, "");
				// } else {
				// view.loadUrl(url);
				// }
				view.loadUrl(url);
				return false;
			}

		});
		wvWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					myProgressBar.setVisibility(View.GONE);
				} else {
					if (View.INVISIBLE == myProgressBar.getVisibility()) {
						myProgressBar.setVisibility(View.VISIBLE);
					}
					myProgressBar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

		});
	}

	@Override
	public boolean backKeyClick() {
		if (wvWebView.canGoBack()) {
			wvWebView.goBack();
			return true;
		} else {
			return false;
		}
	}
}