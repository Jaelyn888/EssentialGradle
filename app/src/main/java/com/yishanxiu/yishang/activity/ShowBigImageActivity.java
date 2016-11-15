package com.yishanxiu.yishang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.*;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.model.shopmall.ProductPicModel;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ImageLoaderBitmapCallBack;

import net.tsz.afinal.annotation.view.ViewInject;
import com.yishanxiu.yishang.R;


import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class ShowBigImageActivity extends BaseActivity {

	private static final String STATE_POSITION = "STATE_POSITION";
	@ViewInject(id = R.id.pager)
	private ViewPager mPager;

	/**
	 * 传过来的图片位置
	 */
	private int position;
	/**
	 * 传过来的图片信息
	 */
	private ArrayList<ProductPicModel> data_imageList;

	/**
	 * 返回键
	 */
	@ViewInject(id = R.id.back)
	private ImageView back;

	/**
	 * 更多
	 */
	@ViewInject(id = R.id.more)
	private ImageView more;


	@ViewInject(id = R.id.indicator)
	private TextView indicator;

	PopupWindows popupWindows;


	@SuppressWarnings({"deprecation"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// flag = 1 ;
		setContentView(R.layout.show_bigimage_activity);
		Intent intent = getIntent();
		position = intent.getIntExtra(Constant.IMAGE_POSITION, 0);
		data_imageList = (ArrayList<ProductPicModel>) intent.getSerializableExtra(Constant.IMAGE_JSON_STR);
		back.setOnClickListener(new OnClickListener() {


			@Override

			public void onClick(View v) {

				finish();

			}

		});
		more.setOnClickListener(new OnClickListener() {
			                        @Override
			                        public void onClick(View v) {
				                        if(popupWindows==null) {
					                        popupWindows = new PopupWindows(mContext);
					                        popupWindows.setOutsideTouchable(true);
					                        popupWindows.showAtLocation(v, Gravity.BOTTOM, 0, 0);
					                        popupWindows.update();
				                        } else if (!popupWindows.isShowing()) {
					                        popupWindows.showAtLocation(v, Gravity.BOTTOM, 0, 0);
					                        popupWindows.update();
				                        }else {
					                        popupWindows.dismiss();
				                        }
			                        }

		                        }

		);
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mAdapter);
		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
		indicator.setText(text);
		// 更新下标
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			                               @Override
			                               public void onPageScrollStateChanged(int arg0) {
			                               }

			                               @Override
			                               public void onPageScrolled(int arg0, float arg1, int arg2) {
			                               }

			                               @Override
			                               public void onPageSelected(int arg0) {
				                               CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, data_imageList.size());
				                               indicator.setText(text);
			                               }
		                               }
		);

		if (savedInstanceState != null) {
			position = savedInstanceState.getInt(STATE_POSITION);
		}
		mPager.setCurrentItem(position);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentStatePagerAdapter {
		public ImagePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return data_imageList == null ? 0 : data_imageList.size();
		}


		@Override
		public Fragment getItem(int position) {
			return ImageDetailFragment.newInstance(data_imageList.get(position).getPath());
		}
	}

	public static final class ImageDetailFragment extends Fragment {
		private String mImageUrl;
		private PhotoView mImageView;

		public static ImageDetailFragment newInstance(String imageUrl) {
			final ImageDetailFragment f = new ImageDetailFragment();
			final Bundle args = new Bundle();
			args.putString("url", imageUrl);
			f.setArguments(args);
			return f;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View v = inflater.inflate(R.layout.show_big_image_item, container, false);
			mImageView = (PhotoView) v.findViewById(R.id.image);
			mImageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
				@Override
				public void onPhotoTap(View view, float x, float y) {
					getActivity().finish();
				}
			});
			return v;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			BitmapHelper.loadImage(getActivity(), mImageUrl, mImageView, BitmapHelper.LoadImgOption.Square, true, new ImageLoaderBitmapCallBack());
		}
	}

	class PopupWindows extends PopupWindow {
		public PopupWindows(Context mContext) {
			View view = View.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));
			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.MATCH_PARENT);
			setBackgroundDrawable(null);
			setContentView(view);
			TextView camera = (TextView) view.findViewById(R.id.select_photo_camera_bt);
			TextView photo = (TextView) view.findViewById(R.id.select_photo_local_bt);
			TextView cancel = (TextView) view.findViewById(R.id.item_popupwindows_cancel);

			photo.setVisibility(View.GONE);
			//Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
			camera.setText(mContext.getResources().getString(R.string.save_img));
			//bt2.setText(mContext.getResources().getString(R.string.transmit));
			camera.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {

					dismiss();
				}
			});
//			bt2.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					dismiss();
//				}
//			});
//			bt3.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					dismiss();
//				}
//			});
		}

	}

}

