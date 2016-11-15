package com.yishanxiu.yishang.activity;

import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.fragment.ShopCartFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Jaelyn on 2015/9/19 0019. 购物车
 * 
 * UPdate 2016/6/23 布局功能
 * 
 * @author FangDongzhang
 */
public class ShoppingCartActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// atention_status = true;
		setContentView(R.layout.activity_my);

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		ShopCartFragment shopCartFragment = new ShopCartFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean("back_status", true);
		shopCartFragment.setArguments(bundle);
		transaction.add(R.id.my_llt, shopCartFragment);
		transaction.commit();
//		fragment.setBack();
	}

}