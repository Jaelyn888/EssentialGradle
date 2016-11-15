package com.yishanxiu.yishang.adapter;

import android.widget.ImageView;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.model.shopmall.ProductCategoryModel;
import com.yishanxiu.yishang.utils.BitmapHelper;

import android.content.Context;
import com.yishanxiu.yishang.R;

/**
 * Created by Jaelyn on 2015/9/7.
 */
public class ProductCategoryAdapter extends CommonBaseAdapter<ProductCategoryModel> {

	//private ItemAttentionChange attentionChange;

	public ProductCategoryAdapter(Context context) {
		super(context, R.layout.product_category_item);
		//this.attentionChange = callback2;
	}

	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, ProductCategoryModel model) {
		ImageView classfy_logo_iv = viewHolderHelper.getImageView(R.id.classfy_logo_iv);
		BitmapHelper.loadImage(mContext, model.getPic(), classfy_logo_iv, BitmapHelper.LoadImgOption.Rectangle, true);
	}

//	private  class GoodsCategoryAdapterViewHolder {
//		@ViewInject(R.id.classfy_logo_iv)
//		private CustomImageView classfy_logo_iv;
//		@ViewInject(R.id.classfy_name_tv)
//		private TextView classfy_name_tv;
//
//		public GoodsCategoryAdapterViewHolder(View view) {
//			ViewUtils.inject(this, view);
//		}
//	}

}
