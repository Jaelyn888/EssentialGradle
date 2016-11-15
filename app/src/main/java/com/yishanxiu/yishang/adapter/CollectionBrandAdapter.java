package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.model.shopmall.BrandCollectionModel;
import com.yishanxiu.yishang.utils.BitmapHelper;

/**
 * Created by Jaelyn on 2016/8/3.
 * 收藏品牌
 */
public class CollectionBrandAdapter extends CommonBaseAdapter<BrandCollectionModel> {

	public CollectionBrandAdapter(Context context) {
		super(context,R.layout.base_brand_item);
	}

//	public View getView(final int position, View view, ViewGroup viewGroup) {
//		CollectionBrandViewHolder viewHolder;
//		JsonMap<String, Object> jsonMap = (JsonMap<String, Object>) datas.get(position);
//		if (view == null) {
//			view = LayoutInflater.from(context).inflate(R.layout.base_brand_item, viewGroup, false);
//			viewHolder = new CollectionBrandViewHolder(view);
//			view.setTag(viewHolder);
//		} else {
//			viewHolder = (CollectionBrandViewHolder) view.getTag();
//		}
//
//		viewHolder.tv_name.setText(jsonMap.getStringNoNull("brandName"));
//		viewHolder.tv_msg.setText(jsonMap.getStringNoNull("pageDesc"));
//
//		boolean isSelected = jsonMap.getBoolean("isCollected");
//		viewHolder.tv_if_attention.setVisibility(View.VISIBLE);
//		viewHolder.tv_if_attention.setSelected(isSelected);
//		if (isSelected) {
//			viewHolder.tv_if_attention.setText(R.string.attentioned);
//		} else {
//			viewHolder.tv_if_attention.setText(R.string.add_attention);
//		}
//		String logoPath = jsonMap.getStringNoNull("logoPath");
//		BitmapHelper.loadImage(context, logoPath, viewHolder.iv_head, BitmapHelper.LoadImgOption.BrandLogo, true);
//
//		viewHolder.tv_if_attention.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (attentionChange != null) {
//					attentionChange.onStatusChange(position, v);
//				}
//			}
//		});
//		return view;
//
//	}

	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, BrandCollectionModel model) {
		ImageView iv_head;
		TextView tv_name;
		TextView tv_msg;
		TextView tv_if_attention;
		iv_head = viewHolderHelper.getImageView(R.id.iv_head);
		tv_name = viewHolderHelper.getTextView(R.id.tv_name);
		tv_msg = viewHolderHelper.getTextView(R.id.tv_msg);
		tv_if_attention = viewHolderHelper.getTextView(R.id.tv_if_attention);

		tv_name.setText(model.getBrandName());
		tv_msg.setText(model.getPageDesc());
		int isSelected = model.getIsCollected();
		tv_if_attention.setVisibility(View.VISIBLE);

		if (isSelected>0) {
			tv_if_attention.setSelected(true);
			tv_if_attention.setText(R.string.attentioned);
		} else {
			tv_if_attention.setText(R.string.add_attention);
			tv_if_attention.setSelected(false);
		}
		String logoPath = model.getLogoPath();
		BitmapHelper.loadImage(mContext, logoPath,iv_head, BitmapHelper.LoadImgOption.BrandLogo, true);
	}

	@Override
	protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
		super.setItemChildListener(viewHolderHelper);
		viewHolderHelper.setItemChildClickListener(R.id.tv_if_attention);
	}

//	public class CollectionBrandViewHolder {
//		private ImageView iv_head;
//		private TextView tv_name;
//		private TextView tv_msg;
//		public TextView tv_if_attention;
//
//		public CollectionBrandViewHolder(View view) {
//			iv_head = (ImageView) view.findViewById(R.id.iv_head);
//			tv_name = (TextView) view.findViewById(R.id.tv_name);
//			tv_msg = (TextView) view.findViewById(R.id.tv_msg);
//			tv_if_attention = (TextView) view.findViewById(R.id.tv_if_attention);
//		}
//	}



}