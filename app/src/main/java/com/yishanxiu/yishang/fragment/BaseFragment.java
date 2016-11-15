package com.yishanxiu.yishang.fragment;

import java.lang.reflect.Field;

import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.getdata.GetServicesDataUtil;
import com.yishanxiu.yishang.utils.LogUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.tsz.afinal.loading.MyLoadingDataDialogManager;
import net.tsz.afinal.toast.ToastUtil;

/**
 * Created by Jaelyn on 2015/8/20 0020.
 * 
 * {@link UpdateAppearance} @author FangDongzhang 2016/8/15
 */
public class BaseFragment extends Fragment {
	protected String TAG = BaseFragment.class.getSimpleName();
	/**
	 * 提示toast的展示对象 可直接调用期show方法展示文字
	 */
	protected ToastUtil toast;
	/**
	 * 模拟toast展示加载数据的对话框，使界面更加友好
	 */
	protected MyLoadingDataDialogManager loadingToast;
	protected BaseActivity activity;
	/**
	 * 调用服务器接口获取服务器数据的工具
	 */

	protected LayoutInflater inflater;
	private View contentView;
	private Context context;
	private ViewGroup container;

	protected GetServicesDataUtil getDataUtil;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getDataUtil = GetServicesDataUtil.init();
		activity= (BaseActivity) getActivity();
		context = activity.getApplicationContext();
		toast=activity.initToast();
		loadingToast=activity.initLoadingToast();
	}


	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.container = container;
		onCreateView(savedInstanceState);
		if (contentView == null)
			return super.onCreateView(inflater, container, savedInstanceState);
		return contentView;
	}

	protected void onCreateView(Bundle savedInstanceState) {

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		contentView = null;
		container = null;
		inflater = null;
	}

	public Context getApplicationContext() {
		return context;
	}

	public void setContentView(int layoutResID) {
		setContentView(inflater.inflate(layoutResID, container, false));
	}

	public void setContentView(View view) {
		contentView = view;
	}

	public View getContentView() {
		return contentView;
	}

	public View findViewById(int id) {
		if (contentView != null)
			return contentView.findViewById(id);
		return null;
	}

	// http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		LogUtil.d(TAG, "onViewCreated");
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		LogUtil.d(TAG, "onHiddenChanged");

		super.onHiddenChanged(hidden);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		LogUtil.d(TAG, "onActivityCreated");

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		LogUtil.d(TAG, "onDestroy");
		super.onDestroy();
	}

	/**
	 * back 键处理
	 * 
	 * @return true：消费此次返回 false 不处理
	 */
	public boolean backKeyClick() {
		return false;
	}
}