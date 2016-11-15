package com.yishanxiu.yishang.adapter;

import android.content.Context;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.model.shopmall.BrandModel;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.view.CustomImageView;

import java.util.List;

/**
 * 热门品牌
 */
public class HotBrandGridAdater extends CommonBaseAdapter<BrandModel> {


	public HotBrandGridAdater(Context context, List<BrandModel> datas) {
		super(context,R.layout.hot_brand_item);
		setData(datas);
	}

	@Override
	public int getCount() {
		int count=mData.size();
		if(count>8){
			return 8;
		} else {
			return count;
		}
	}

//	@Override
//	public View getView(int i, View view, ViewGroup viewGroup) {
//
//		ShoppingMallSelectHolder shoppingMallSelectHolder;
//		if (view == null) {
//			view = layoutInflater.inflate(R.layout.hot_brand_item, viewGroup, false);
//			shoppingMallSelectHolder = new ShoppingMallSelectHolder(view);
//			view.setTag(shoppingMallSelectHolder);
//		} else {
//			shoppingMallSelectHolder = (ShoppingMallSelectHolder) view.getTag();
//		}
//		JsonMap data = (JsonMap) datas.get(i);
//		String logoPath=data.getStringNoNull("logoPath");
//		BitmapHelper.loadImage(context,logoPath,shoppingMallSelectHolder.brandLogoIv, BitmapHelper.LoadImgOption.BrandLogo);
//		return view;
//	}

	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, BrandModel model) {
		CustomImageView brandLogoIv= (CustomImageView) viewHolderHelper.getImageView(R.id.brand_logo_iv);
		String logoPath=model.getLogoPath();
		BitmapHelper.loadImage(mContext,logoPath,brandLogoIv, BitmapHelper.LoadImgOption.BrandLogo);
	}

//	public class ShoppingMallSelectHolder {
//		//@ViewInject(R.id.tv_shopmall_right)
//		//public TextView tv_shopmall_right;
//		public CustomImageView brandLogoIv;
//
//		public ShoppingMallSelectHolder(View view) {
//			brandLogoIv= (CustomImageView) view.findViewById(R.id.brand_logo_iv);
//		}
//	}
}
