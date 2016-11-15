package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.model.shopmall.FilterProductSelectionDataModel;
import com.yishanxiu.yishang.utils.Constant;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Jaelyn on 2016/8/23.
 * 分类相关商品侧滑 adapter
 */
public class MyStickyListAdapter extends CommonBaseAdapter<FilterProductSelectionDataModel> implements StickyListHeadersAdapter {


	public MyStickyListAdapter(Context context) {
		super(context,R.layout.checkbox_item);
	}


	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, final int position, FilterProductSelectionDataModel model) {

		TextView item_cb=viewHolderHelper.getTextView(R.id.item_cb);
		item_cb.setText(model.getName());
		item_cb.setSelected(model.isChecked());
		item_cb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean bool = v.isSelected();
				v.setSelected(!bool);
				((FilterProductSelectionDataModel) mData.get(position)).setChecked(!bool);
			}
		});
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;
		FilterProductSelectionDataModel data= (FilterProductSelectionDataModel) mData.get(position);
		if (convertView == null) {
			holder = new HeaderViewHolder();
			LayoutInflater layoutInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.sticky_header, parent, false);
			//holder.text = (TextView) convertView.findViewById(R.id.text);
			holder.text= (TextView) convertView;
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		//set header text as first char in name
		int headerText = R.string.brand;
		int itemType=data.getItemType();
		if(itemType==Constant.ITEM_TYPE_BRAND){
		} else if (itemType==Constant.ITEM_TYPE_CLASSFY){
			headerText=R.string.category;
		}
		holder.text.setText(headerText);
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		FilterProductSelectionDataModel data= (FilterProductSelectionDataModel) mData.get(position);
		int itemType=data.getItemType();
		return itemType;
	}

	class HeaderViewHolder {
		TextView text;
	}

}
