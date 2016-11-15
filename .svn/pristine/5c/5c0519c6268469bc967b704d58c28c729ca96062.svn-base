package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.model.article.ArticleModel;
import com.cnfol.emoj.bean.LiveColorBean;
import com.cnfol.emoj.util.MyLiveTextView;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.LogUtil;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.R;

import java.util.ArrayList;

/**
 * Created by Jaelyn on 16/2/24. 发现搜索
 * <p/>
 * Update @author FangDongzhang 2016/7/23
 */
public class ArticalSearchAdapter extends CommonBaseAdapter<ArticleModel> {
	/**
	 * 标记特殊的关键字
	 */
	private String specialStr = "";
	private LiveColorBean liveColorBean;
	private ArrayList<LiveColorBean> list;

	public ArticalSearchAdapter(Context context) {
		super(context, R.layout.artical_search_item);
	}


	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, ArticleModel model) {
		ImageView artical_logo_iv = viewHolderHelper.getImageView(R.id.artical_logo_iv);
		MyLiveTextView artical_title_iv = (MyLiveTextView) viewHolderHelper.getTextView(R.id.artical_title_tv);
		MyLiveTextView artical_disc_tv = (MyLiveTextView) viewHolderHelper.getTextView(R.id.artical_disc_tv);
		TextView classfy_name_tv = viewHolderHelper.getTextView(R.id.classfy_name_tv);
		MyLiveTextView author_tv = (MyLiveTextView) viewHolderHelper.getTextView(R.id.author_tv);
		TextView create_time_tv = viewHolderHelper.getTextView(R.id.create_time_tv);


		String picPath = model.getThumbnailPath();
		BitmapHelper.loadImage(mContext, picPath, artical_logo_iv, BitmapHelper.LoadImgOption.Square, true);
		artical_title_iv.insertGif(model.getArticleName(), list, new MyLiveTextView.SetSpanListener() {
			@Override
			public void updateDrawState(TextPaint ds) {
				LogUtil.d("sss", "click");
			}

			@Override
			public void onClick(View view, LiveColorBean liveColorBean) {
				LogUtil.d("sss", "click");
			}
		});

		String articalSummary = model.getArticleAbstract();
		if (TextUtils.isEmpty(articalSummary)) {
			artical_disc_tv.setVisibility(View.GONE);
		} else {
			artical_disc_tv.setVisibility(View.VISIBLE);
			int index = articalSummary.indexOf(specialStr);
			if (index > -1) {
				int totleSize = articalSummary.length();
				int startIndex = index - 5;
				if (totleSize - index < 14) {
					startIndex = index - 14;
				} else {
					startIndex = index - 5;
				}
				if (startIndex < 1) {
					startIndex = 1;
				}
				articalSummary = "……" + articalSummary.substring(startIndex - 1);
			}
			artical_disc_tv.insertGif(articalSummary, list,
					new MyLiveTextView.SetSpanListener() {
						@Override
						public void updateDrawState(TextPaint ds) {
							LogUtil.d("sss", "click");
						}

						@Override
						public void onClick(View view, LiveColorBean liveColorBean) {
							LogUtil.d("sss", "click");
						}
					});
		}


		author_tv.insertGif(model.getGroupName(), list,
				new MyLiveTextView.SetSpanListener() {
					@Override
					public void updateDrawState(TextPaint ds) {
						LogUtil.d("sss", "click");
					}

					@Override
					public void onClick(View view, LiveColorBean liveColorBean) {
						LogUtil.d("sss", "click");
					}
				});

		create_time_tv.setText(StringUtils.getTimeFormatYMd(model.getCreateTime()));
	}

	public void setSpecialStr(String specialStr) {
		this.specialStr = specialStr;
		liveColorBean = new LiveColorBean(specialStr, "#0000ff", "");
		list = new ArrayList<>();
		list.add(liveColorBean);
	}


//	public class FindSearchAdapterViewHolder {
//		@ViewInject(R.id.artical_logo_iv)
//		public ImageView artical_logo_iv;
//
//		@ViewInject(R.id.artical_title_tv)
//		public MyLiveTextView artical_title_iv;
//
//		@ViewInject(R.id.artical_disc_tv)
//		public MyLiveTextView artical_disc_tv;
//
//		@ViewInject(R.id.classfy_name_tv)
//		public TextView classfy_name_tv;
//
//		@ViewInject(R.id.author_tv)
//		public MyLiveTextView author_tv;
//
//		@ViewInject(R.id.create_time_tv)
//		public TextView create_time_tv;
//
//	}
}
