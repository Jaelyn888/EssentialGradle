package com.yishanxiu.yishang.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import com.yishanxiu.yishang.model.shopmall.BrandSortModel;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.ExtraKeys;

/**
 * Created by Jaelyn on 2015/9/25.
 * 店铺详情介绍
 */
public class ShopIntroductionActivity extends BaseUIActivity {

	/**
	 * to头部图片
	 */
	private ImageView shop_logo_iv;
	/**
	 * 头部信息
	 */
	private TextView shop_rich_discri_tv;
	/**
	 * 网址
	 */
	private TextView internet_tv;

	private BrandSortModel shopDatas; // 店铺信息

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_introduction);
		shopDatas = (BrandSortModel) getIntent().getSerializableExtra(ExtraKeys.Key_Msg1);
		shop_logo_iv = (ImageView) findViewById(R.id.shop_logo_iv);
		shop_rich_discri_tv = (TextView) findViewById(R.id.shop_rich_discri_tv);
		internet_tv = (TextView) findViewById(R.id.internet_tv);
//		internet_tv.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				gotoInternet(shopDatas.getRemark1());
//			}
//		});
		setIntroductData();
	}

	/**
	 * 打开浏览器
	 *
	 * @param internetUrl
	 */
	public void gotoInternet(String internetUrl) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri content_url = Uri.parse(internetUrl);
		intent.setData(content_url);
		startActivity(intent);
	}

	public void setIntroductData() {
		String logoPath = shopDatas.getLogoPath();
		BitmapHelper.loadImage(mContext, logoPath, shop_logo_iv, BitmapHelper.LoadImgOption.BrandLogo);
		shop_rich_discri_tv.setText("\t\t\t\t" +shopDatas.getDetailedDesc());
		internet_tv.setText(shopDatas.getRemark1());
	}
}