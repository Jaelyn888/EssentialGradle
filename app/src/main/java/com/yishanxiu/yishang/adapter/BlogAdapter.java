package com.yishanxiu.yishang.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.activity.BlogDetailActivity;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.view.CircleImageView;
import com.yishanxiu.yishang.view.NineGridView;
import com.yishanxiu.yishang.view.NineGridView.OnImageClickListener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.json.JsonMap;
import com.yishanxiu.yishang.R;

/**
 * Created by Jaelyn on 16/3/8. 日志数据适配
 * 
 * FangDongzhang update 2016/4/12 update 2016/4/19 add MyDailyActivity加载的UI限制
 */
public class BlogAdapter extends MyBaseAdapter {
	ItemCompontClickListener itemCompontClickListener;

	private Moment moment;
	private int flag = 1;
	private ArrayList<Moment> moments = new ArrayList<Moment>(9);
	

	/**
	 * 虚拟数据 FangDongzhang
	 */
//	public static String[] urls = new String[] { "http://img.zcool.cn/sucaiori/D1858BDB-2205-1EA6-2C31-6F141E94C9E2.jpg@700w_0e_1l.jpg",
//			"http://img.zcool.cn/sucaiori/391A2B0F-411E-D505-0465-51D73048A782.jpg@700w_0e_1l.jpg",
//			"http://img.zcool.cn/sucaiori/FCBD73F4-C69F-82C6-35C4-E5CB753598BF.jpg@700w_0e_1l.jpg",
//			"http://img.zcool.cn/sucaiori/AB7C372A-9551-4C4D-2CFD-02212F92ECC2.jpg@700w_0e_1l.jpg",
//			"http://img.zcool.cn/sucaiori/B2F9CFCD-83A2-BA12-1548-80C26D038B29.jpg@700w_0e_1l.jpg",
//			"http://img.zcool.cn/sucaiori/B0CF5B34-E6CA-A410-7C4B-9154852C6833.jpg@700w_0e_1l.jpg",
//			"http://img.zcool.cn/sucaiori/E57CD2CA-7FCF-F637-3278-F1700E656227.jpg@700w_0e_1l.jpg",
//			"http://img.zcool.cn/sucaiori/44DAC10A-9427-0683-83AC-6783AA12092B.jpg@700w_0e_1l.jpg",
//			"http://img.zcool.cn/sucaiori/804F0E03-B9A9-1E53-7A68-62199C5FFBCF.jpg@700w_0e_1l.jpg" };
	
	public static String[] urls = new String[] { 
			"http://www.010lf.com/pic/0/10/15/65/10156526_411751.jpg",
			"http://img2.zjolcdn.com/pic/0/14/88/33/14883306_190779.jpg",
			"http://scimg.jb51.net/allimg/151015/14-151015101215Y5.jpg",
			"http://img2.cache.netease.com/ent/2015/10/8/20151008074238a9459.jpg",
			"http://d.3987.com/wk_151207/desk_007.jpg",
			"http://img2.zjolcdn.com/pic/0/14/88/33/14883311_995428.jpg",
			"http://gb.cri.cn/mmsource/images/2015/11/22/58/16971444805761210990.jpg",
			"http://img3.douban.com/img/celebrity/large/1397298352.83.jpg",
			"http://star.2liang.net/d/file/star/dlmx/2015-10/b05926042f305d1749ba2ae245b3444c.jpg"};

	/**
	 * 后期替换成真实数据，取消在此初始化数据方法 FangDongzhang
	 */
	private void initData() {

		for (int i = 0; i < 9; i++) {
			Moment moment = new Moment();
			moment.content = "content:" + (i + 1);

			Integer[] resource = new Integer[(i + 1)];
			String[] address = new String[(i + 1)];
			for (int j = 0; j < resource.length; j++) {
				// resource[j] = images[j] ;
				address[j] = urls[j];
			}

			moment.resource = resource;
			moment.address = address;
			moments.add(moment);
		}

		// listview = (ListView) findViewById(R.id.listview);
		// listview.setAdapter(new MyAdapter());
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setItemCompontClickListener(ItemCompontClickListener itemCompontClickListener) {
		this.itemCompontClickListener = itemCompontClickListener;
	}

	public BlogAdapter(Context context) {
		super(context);

		initData();
	}

	public BlogAdapter(Context context, List<? extends Map<String, ?>> datas) {
		super(context, datas);

		initData();
	}

	@Override
	public int getCount() {
		return 9;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		moment = moments.get(i);
		BlogAdapterViewHolder blogAdapterViewHolder;
		JsonMap jsonMap = new JsonMap();// = (JsonMap) datas.get(i);
		if (view == null) {
			blogAdapterViewHolder = new BlogAdapterViewHolder();
			view = layoutInflater.inflate(R.layout.blog_item, null);
			ViewUtils.inject(blogAdapterViewHolder, view);
			view.setTag(blogAdapterViewHolder);
		} else {
			blogAdapterViewHolder = (BlogAdapterViewHolder) view.getTag();
		}

		setTag(i, blogAdapterViewHolder);
		bindData(i, blogAdapterViewHolder, jsonMap, moment);

		blogAdapterViewHolder.blog_content_image_gv.setOnImageClickListener(new OnImageClickListener() {

			@Override
			public void onImageCilcked(int position, View view) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "onItemClick" + position, Toast.LENGTH_SHORT).show();
				if (itemCompontClickListener != null) {
					itemCompontClickListener.onItemCompontClickListener(9, Constant.BlogItemCompontType.SCAN_BIG_IMAGE,
							position);
				}
			}
		});
		setVisibilityAttentionOptionImg(blogAdapterViewHolder);
		return view;
	}

	// private void bindData(int i, BlogAdapterViewHolder blogAdapterViewHolder,
	// JsonMap jsonMap) {
	// BlogPicAdapter blogPicAdapter = new BlogPicAdapter(context);
	// blogAdapterViewHolder.blog_content_image_gv.setAdapter(blogPicAdapter);
	// blogAdapterViewHolder.blog_content_image_gv.setVisibility(View.VISIBLE);
	// }
	/**
	 * 数据绑定
	 * @param i
	 * @param blogAdapterViewHolder
	 * @param jsonMap
	 * @param moment
	 */
	private void bindData(int i, BlogAdapterViewHolder blogAdapterViewHolder, JsonMap jsonMap, Moment moment) {
		NetworkImageAdapter blogPicAdapter = new NetworkImageAdapter(context, Arrays.asList(moment.address));
		blogAdapterViewHolder.blog_content_image_gv.setAdapter(blogPicAdapter);
		blogAdapterViewHolder.blog_content_image_gv.setVisibility(View.VISIBLE);
	}

	private void setTag(int i, BlogAdapterViewHolder blogAdapterViewHolder) {
		blogAdapterViewHolder.attention_option_tv.setTag(i);
		blogAdapterViewHolder.blog_user_photo_iv.setTag(i);
		blogAdapterViewHolder.attention_tv.setTag(i);
		blogAdapterViewHolder.attentioned_option_iv.setTag(i);
		blogAdapterViewHolder.blog_content_image_gv.setTag(i);
		blogAdapterViewHolder.transmit_layout.setTag(i);
		blogAdapterViewHolder.comment_layout.setTag(i);
	}

	private class BlogAdapterViewHolder {
		@ViewInject(R.id.blog_recommend_title_layout)
		private LinearLayout blog_recommend_title_layout;
		@ViewInject(R.id.attention_option_tv)
		private TextView attention_option_tv;

		/**
		 * 推荐日志 选项操作
		 *
		 * @param view
		 */
		@OnClick(R.id.attention_option_tv)
		public void attention_option_tv_click(View view) {
			if (itemCompontClickListener != null) {
//				Toast.makeText(context, "attention_option_tv_click", Toast.LENGTH_SHORT).show();
				buildDialog();
				 itemCompontClickListener.onItemCompontClickListener((Integer)
				 view.getTag(), Constant.BlogItemCompontType.RECOMMAND_OPTION,
				 -1);
//				 PopupWindow popupWindow = PersonalHomeActivity.getInstance().makePopupWindow(context) ;
			//   popupWindow.showAtLocation(R.id.attention_option_tv,,x, y);
//				 popupWindow.showAsDropDown(view);//(attention_option_tv);
			}
		}

		@ViewInject(R.id.blog_user_photo_iv)
		private CircleImageView blog_user_photo_iv;

		@SuppressWarnings("static-access")
		@OnClick(R.id.blog_user_photo_iv)
		public void user_photo_iv_click(View view) {
			if (itemCompontClickListener != null && BlogDetailActivity.getInstance().head_click == false) {
				itemCompontClickListener.onItemCompontClickListener((Integer) view.getTag(),
						Constant.BlogItemCompontType.USER_PHOTO, -1);
			}
		}

		@ViewInject(R.id.userName_tv)
		private TextView userName_tv;

		@ViewInject(R.id.create_time_tv)
		private TextView create_time_tv;

		@ViewInject(R.id.attention_tv)
		private TextView attention_tv;

		@OnClick(R.id.attention_tv)
		public void attention_tv_click(View view) {
			if (itemCompontClickListener != null) {
				itemCompontClickListener.onItemCompontClickListener((Integer) view.getTag(),
						Constant.BlogItemCompontType.ATTENTION, -1);
			}
		}

		@ViewInject(R.id.attentioned_option_iv)
		private ImageView attentioned_option_iv;

		@OnClick(R.id.attentioned_option_iv)
		public void attentioned_option_iv_click(View view) {
			if (itemCompontClickListener != null)
				itemCompontClickListener.onItemCompontClickListener((Integer) view.getTag(),
						Constant.BlogItemCompontType.ATTENTIONED_OPTION, -1);
			PopupWindows popupWindows = new PopupWindows(context, view);
		}

		@ViewInject(R.id.blog_content_txt_tv)
		private TextView blog_content_txt_tv;

		@ViewInject(R.id.blog_content_image_gv)
		private NineGridView blog_content_image_gv;

		// @OnItemClick(R.id.blog_content_image_gv)
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// Toast.makeText(context, "onItemClick", Toast.LENGTH_SHORT).show();
		// if (itemCompontClickListener != null) {
		// itemCompontClickListener.onItemCompontClickListener((Integer)
		// parent.getTag(), Constant.BlogItemCompontType.SCAN_BIG_IMAGE,
		// position);
		// }
		// }

		@ViewInject(R.id.transmit_layout)
		private LinearLayout transmit_layout;

		@OnClick(R.id.transmit_layout)
		public void transmit_layout_click(View view) {
			if (itemCompontClickListener != null) {
				itemCompontClickListener.onItemCompontClickListener((Integer) view.getTag(),
						Constant.BlogItemCompontType.TRANSMIT, -1);
			}
		}

		@ViewInject(R.id.comment_layout)
		private LinearLayout comment_layout;

		@OnClick(R.id.comment_layout)
		public void comment_layout_click(View view) {
			if (itemCompontClickListener != null) {
				itemCompontClickListener.onItemCompontClickListener((Integer) view.getTag(),
						Constant.BlogItemCompontType.COMMENT, -1);
			}
		}

	}
	/**
	 * 区分我的日志中布局显示
	 * @param blogAdapterViewHolder
	 * @author FangDongzhang
	 */
	@SuppressWarnings("static-access")
	private void setVisibilityAttentionOptionImg(BlogAdapterViewHolder blogAdapterViewHolder) {
		//if (MyDailyActivity.getInstance().atention_status == true)
			blogAdapterViewHolder.attentioned_option_iv.setVisibility(View.GONE);
		blogAdapterViewHolder.attention_option_tv.setText("");
		blogAdapterViewHolder.attention_tv.setCompoundDrawables(null, null, null, null);
		blogAdapterViewHolder.attention_tv.setText("123\n阅读");

//		if (MyDailyActivity.getInstance().atention_status == false) {
//
//			blogAdapterViewHolder.attention_option_tv.setText("推荐");
//			Drawable drawable = context.getResources().getDrawable(R.drawable.cus_attention_tag);
//			drawable.setBounds(0, 0, drawable.getMinimumHeight(), drawable.getMinimumWidth());
//			blogAdapterViewHolder.attention_tv.setCompoundDrawables(drawable, null, null, null);
//			blogAdapterViewHolder.attention_tv.setText("关注");
//			blogAdapterViewHolder.attentioned_option_iv.setVisibility(View.VISIBLE);
//		}

	}

	public interface ItemCompontClickListener {
		/**
		 * @param position
		 *            根位置
		 * @param shopCartitemType
		 *            类别
		 * @param index
		 *            点击具体的商品位置
		 */
		void onItemCompontClickListener(int position, int shopCartitemType, int index);
	}

	static class Moment {
		public String content;
		public Integer[] resource; //
		public String[] address;
	}
	
	private void buildDialog(){
		final String items[] = {"收藏","举报"};
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setItems(items, clickListener );
		builder.create().show();
	}
	
	private DialogInterface.OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View
					.inflate(mContext, R.layout.item_blog_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.WRAP_CONTENT);
//			setBackgroundDrawable(new BitmapDrawable());
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

//			Button camera = (Button) view
//					.findViewById(R.id.item_popupwindows_camera);
//			Button photo = (Button) view
//					.findViewById(R.id.item_popupwindows_Photo);
			Button cancel = (Button) view
					.findViewById(R.id.item_blog_pop_cancel);
			
			RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rlt_blog_pop);
			layout.setOnClickListener(new android.view.View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismiss();
				}
			});
			
//			camera.setOnClickListener(new android.view.View.OnClickListener() {
//				public void onClick(View v) {
//					dismiss();
//				}
//			});
//			photo.setOnClickListener(new android.view.View.OnClickListener() {
//				public void onClick(View v) {
//					dismiss();
//				}
//			});
			cancel.setOnClickListener(new android.view.View.OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}
}
