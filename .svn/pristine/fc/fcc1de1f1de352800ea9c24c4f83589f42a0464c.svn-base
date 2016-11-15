package com.yishanxiu.yishang.adapter;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yishanxiu.yishang.model.article.ArticleModel;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.BitmapHelper.LoadImgOption;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;

/**
 * Created by Jaelyn on 2015/9/6 0006.
 */
public class ArticleAdapter extends CommonBaseAdapter<ArticleModel> {

	/**
	 * 按钮和推荐的列表的点击事件
	 */

	public ArticleAdapter(Context context) {
		super(context, R.layout.article_item);
	}


	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, ArticleModel model) {
		ImageView big_iv = viewHolderHelper.getImageView(R.id.big_iv);
		TextView find_item_big_title_tv = viewHolderHelper.getTextView(R.id.find_item_big_title_tv);
		TextView find_item_classfy_tv = viewHolderHelper.getTextView(R.id.find_item_classfy_tv);
		ImageView iv_icon = viewHolderHelper.getImageView(R.id.iv_icon);


		find_item_big_title_tv.setText(model.getArticleName());
		find_item_classfy_tv.setText(model.getGroupName());
//		viewHolder.find_item_create_time_tv
//				.setText(StringUtils.getDatayyyyMMddBySeconds(jsonMap.getStringNoNull("createTime")));
//		viewHolder.find_item_scan_num_tv.setText(jsonMap.getStringNoNull("readcount"));
		String picPath = model.getMainPath();
		//picPath="http://139.196.33.67:8005//discover/20151126/06583ebb-f599-428a-8f72-6dd0b18a2b93.png";
		BitmapHelper.loadImage(mContext, picPath, big_iv, LoadImgOption.Rectangle);
		String iconStr = model.getAuthorIcon();
		BitmapHelper.loadImage(mContext, iconStr, iv_icon, LoadImgOption.Photo);
	}

//	public class FindProductAdapterViewHolder {
//		@ViewInject(R.id.big_iv)
//		public ImageView big_iv;
//
//		@ViewInject(R.id.find_item_big_title_tv)
//		public TextView find_item_big_title_tv;
//
//		@ViewInject(R.id.find_item_classfy_tv)
//		public TextView find_item_classfy_tv;
//
///*		@ViewInject(R.id.create_time_tv)
//		public TextView find_item_create_time_tv;
//
//		@ViewInject(R.id.find_item_scan_num_tv)
//		public TextView find_item_scan_num_tv;*/
//
//		@ViewInject(R.id.iv_icon)
//		public ImageView iv_icon;
//
//	}

}
