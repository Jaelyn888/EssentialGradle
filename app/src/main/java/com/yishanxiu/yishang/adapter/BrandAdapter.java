package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.model.shopmall.BrandModel;
import com.yishanxiu.yishang.model.shopmall.BrandSortModel;
import com.yishanxiu.yishang.utils.BitmapHelper;
import java.util.List;

public class BrandAdapter extends CommonBaseAdapter<BrandSortModel> implements SectionIndexer {



	public BrandAdapter(Context context, int itemLayoutId) {
		super(context, itemLayoutId);
	}

	public BrandAdapter(Context context, List<BrandSortModel> dataList) {
		super(context,R.layout.base_brand_item);
		setData(dataList);
	}

	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, BrandSortModel model) {
		ImageView iv_head = viewHolderHelper.getImageView(R.id.iv_head);
		TextView tv_name = viewHolderHelper.getTextView(R.id.tv_name);
		TextView tv_msg = viewHolderHelper.getTextView(R.id.tv_msg);
		tv_name.setText(model.getBrandName());
		tv_msg.setText(model.getPageDesc());
		String logoPath = model.getLogoPath();
		BitmapHelper.loadImage(mContext, logoPath,iv_head, BitmapHelper.LoadImgOption.BrandLogo, true);
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		BrandSortModel BrandSortModel = mData.get(position);
		String sortStr = BrandSortModel.getSortLetters();
		return sortStr.toUpperCase().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int position) {
		for (int i = 0; i < getCount(); i++) {
			BrandSortModel BrandSortModel = mData.get(i);
			String sortStr = BrandSortModel.getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == position) {
				return i;
			}
		}
		return -1;
	}


	@Override
	public Object[] getSections() {
		return null;
	}

}