package com.yishanxiu.yishang.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.fragment.FashionInsiderFragment;

/**
 * 粉丝列表
 * @author FangDongzhang
 *2016/4/19
 */
public class FansListActivity extends BaseUIActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		setCenter_title(R.string.myAttention);
		
		
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction= manager.beginTransaction();
		FashionInsiderFragment fragment = new FashionInsiderFragment();
		transaction.add(R.id.my_llt, fragment);
		transaction.commit();
	}
}
