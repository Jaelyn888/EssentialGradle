package com.yishanxiu.yishang.adapter;


import android.view.View;
import android.widget.ImageView;
import cn.bingoogolapple.bgabanner.BGABanner;
import com.yishanxiu.yishang.model.shopmall.ProductPicModel;
import com.yishanxiu.yishang.utils.BitmapHelper;


/**
 * Created by Jaelyn on 2015/9/11 0011.
 * 商品图片 轮播图
 */
public class ProductPicAdapter implements BGABanner.Adapter{
	@Override
	public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
		ProductPicModel productPicModel= (ProductPicModel) model;
		BitmapHelper.loadImage(banner.getContext(),productPicModel.getPath(), (ImageView) view, BitmapHelper.LoadImgOption.Rectangle);
	}

}
