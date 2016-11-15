package com.yishanxiu.yishang.adapter;

import android.app.Activity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.ReturnRefundActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传图片
 */
public class GridViewAdapter extends BaseAdapter {
	private int maxImgCount;
	private Context mContext;
	private List<ImageItem> mData;
	private LayoutInflater mInflater;
	private OnRecyclerViewItemClickListener listener;
	private boolean isAdded;   //是否额外添加了最后一个图片

	public interface OnRecyclerViewItemClickListener {
		void onItemClick(View view, int position, int type);
	}

	public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
		this.listener = listener;
	}

	public void setImages(List<ImageItem> data) {
		mData = new ArrayList<>(data);
		if (getCount() < maxImgCount) {
			mData.add(new ImageItem());
			isAdded = true;
		} else {
			isAdded = false;
		}
	}

	public List<ImageItem> getImages() {
		//由于图片未选满时，最后一张显示添加图片，因此这个方法返回真正的已选图片
		if (isAdded) return new ArrayList<>(mData.subList(0, mData.size() - 1));
		else return mData;
	}

	public GridViewAdapter(Context mContext, List<ImageItem> data, int maxImgCount) {
		this.mContext = mContext;
		this.maxImgCount = maxImgCount;
		this.mInflater = LayoutInflater.from(mContext);
		setImages(data);
	}


	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.item_published_grida, parent, false);
		final ViewHolder holder = new ViewHolder();
		holder.iv_img = (ImageView) convertView.findViewById(R.id.item_grida_image);
		holder.delete = (ImageView) convertView.findViewById(R.id.delete_img);
		holder.clickPosition = position;
		convertView.setTag(holder);


		//根据条目位置设置图片
		ImageItem item = mData.get(position);
		if (isAdded && position == getCount() - 1) {
			holder.iv_img.setImageResource(R.drawable.bt_add_pic);
			holder.clickPosition = ReturnRefundActivity.IMAGE_ITEM_ADD;
			holder.delete.setVisibility(View.GONE);
		} else {
			holder.delete.setVisibility(View.VISIBLE);
			ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, holder.iv_img, 0, 0);
		}

		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					int id = v.getId();
					if (id == R.id.delete_img) {
						listener.onItemClick(v, holder.clickPosition, -1);
					} else if (id == R.id.item_grida_image) {
						listener.onItemClick(v, holder.clickPosition, 0);
					} else {
						listener.onItemClick(v, holder.clickPosition, 0);
					}
				}
			}
		};

		holder.delete.setOnClickListener(onClickListener);
		holder.iv_img.setOnClickListener(onClickListener);
		return convertView;
	}

	public class ViewHolder {
		private ImageView iv_img;
		private int clickPosition;
		public ImageView delete;
	}

}




