package com.yishanxiu.yishang.activity;

import java.io.File;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu.ChatInputMenuListener;
import com.hyphenate.easeui.widget.EaseChatPrimaryMenu;
import com.hyphenate.util.PathUtil;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.utils.ExtraKeys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.annotation.view.ViewInject;
import com.yishanxiu.yishang.R;

public class PublishedActivity extends BaseUIActivity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private TextView activity_selectimg_send;
	private PopupWindows popupWindows;
	private ImageView select_photo;
//	@ViewInject(R.id.forward_msg_ll)
	
	@ViewInject(id = R.id.forward_msg_ll)
	private LinearLayout linearLayout;
	private EditText publisher;
	
	protected EaseChatInputMenu inputMenu;
	private boolean status = false; 

	/**
	 * @author FangDongzhang 2016/4/8 发送博客页面
	 * 
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selectimg);

		status = getIntent().getExtras().getBoolean(ExtraKeys.forward_Msg);
		if(status == true){
			linearLayout.setVisibility(View.VISIBLE);
		}else if(status == false){
			linearLayout.setVisibility(View.GONE);
		}
		//EaseChatPrimaryMenu e = new EaseChatPrimaryMenu(this, 1);

		init();
		setBtn_backListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideSoftKeyboard();
				finish();
			}
		});
	}

	@OnClick(R.id.select_photo_pop)
	public void select_photo(View v) {
		popupWindows = new PopupWindows(PublishedActivity.this, noScrollgridview);
	}
	
	public void init() {
		publisher = (EditText) findViewById(R.id.publisher_edt);
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == 1) {
					popupWindows = new PopupWindows(PublishedActivity.this, noScrollgridview);
				} else {
//					Intent intent = new Intent(PublishedActivity.this, PhotoActivity.class);
//					intent.putExtra("ID", arg2);
//					startActivity(intent);
				}
			}
		});

		setCenter_title("写博客");
		setBtn_menuText(R.string.publish, new OnClickListener() {

			public void onClick(View v) {

			}
		});

		select_photo = (ImageView) findViewById(R.id.select_photo_pop);
		select_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindows = new PopupWindows(PublishedActivity.this, noScrollgridview);
			}
		});

		inputMenu = (EaseChatInputMenu) findViewById(R.id.input_menu);
		inputMenu.init(null);
		inputMenu.setChatInputMenuListener(new ChatInputMenuListener() {

			@Override
			public void onSendMessage(String content) {
				// 发送文本消息
				// sendTextMessage(content);

				// Toast.makeText(PublishedActivity.this, "123",
				// Toast.LENGTH_SHORT).show();

				popupWindows = new PopupWindows(PublishedActivity.this, noScrollgridview);
			}

			@Override
			public void onBigExpressionClicked(EaseEmojicon emojicon) {

			}

			@Override
			public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
				return false;
			}

//			@Override
//			public void onButtonClicked() {
//				popupWindows = new PopupWindows(PublishedActivity.this, noScrollgridview);
//
//			}

			// @Override
			// public boolean onPressToSpeakBtnTouch(View v, MotionEvent event)
			// {
			// return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new
			// EaseVoiceRecorderCallback() {
			//
			// @Override
			// public void onVoiceRecordComplete(String voiceFilePath, int
			// voiceTimeLength) {
			// // 发送语音消息
			// sendVoiceMessage(voiceFilePath, voiceTimeLength);
			// }
			// });
			// }
			//
			// @Override
			// public void onBigExpressionClicked(EaseEmojicon emojicon) {
			// //发送大表情(动态表情)
			// sendBigExpressionMessage(emojicon.getName(),
			// emojicon.getIdentityCode());
			// }
		});
		// 替换Editview
		EaseChatPrimaryMenu e = (EaseChatPrimaryMenu) inputMenu.getPrimaryMenu();
		//e.setInputEditView(publisher);
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			//loading();
		}

		public int getCount() {
			return 1;
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		@SuppressLint("NewApi")
		public View getView(final int position, View convertView, ViewGroup parent) {
			@SuppressWarnings("unused")
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
				holder.delete = (ImageView) convertView.findViewById(R.id.delete_img);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

//			if (BitmpUtil.bmp.size() == 0) {
//				holder.image.setVisibility(View.GONE);
//			} else {
//				holder.image.setVisibility(View.VISIBLE);
//
//			}
//
//			if (position == BitmpUtil.bmp.size()) {
//				holder.delete.setVisibility(View.GONE);
//				holder.image
//						.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
//				// holder.image.setBackground(getResources().getDrawable(R.drawable.icon_addpic_unfocused));
//				if (position == 9) {
//					holder.image.setVisibility(View.GONE);
//				}
//			} else {
//				holder.delete.setVisibility(View.VISIBLE);
//				holder.image.setImageBitmap(BitmpUtil.bmp.get(position));
//			}

			holder.delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Toast.makeText(mContext, "delete" + position + "",
					// Toast.LENGTH_SHORT).show();
					//BitmpUtil.bmp.remove(position);
					notifyDataSetChanged();
				}
			});
			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
			public ImageView delete;
		}


	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View.inflate(mContext, R.layout.item_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			// setBackgroundDrawable(new BitmapDrawable());
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();


			TextView camera = (TextView) view.findViewById(R.id.select_photo_camera_bt);
			TextView photo = (TextView) view.findViewById(R.id.select_photo_local_bt);
			TextView cancel = (TextView) view.findViewById(R.id.item_popupwindows_cancel);

			RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rlt_pop);
			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dismiss();
				}
			});

			camera.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					photo();
					dismiss();
				}
			});
			photo.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
//					Intent intent = new Intent(PublishedActivity.this, PicActivity.class);
//					startActivity(intent);
//					dismiss();
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		File file = new File(Environment.getExternalStorageDirectory() + "/myimage/",
//				String.valueOf(System.currentTimeMillis()) + ".jpg");
		File file = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
		path = file.getPath();
		Uri imageUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:

			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		hideSoftKeyboard();
	}

	
}
