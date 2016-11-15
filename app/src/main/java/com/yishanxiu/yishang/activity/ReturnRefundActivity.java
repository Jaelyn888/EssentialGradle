package com.yishanxiu.yishang.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.Manifest;
import android.support.annotation.NonNull;
import android.widget.*;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.GridViewAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.utils.*;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.yishanxiu.yishang.view.GridViewNoScroll;
import net.tsz.afinal.annotation.view.ViewInject;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author FangDongzhang 退货退款
 * @date 2016/7/8
 */
public class ReturnRefundActivity extends BaseUIActivity implements EasyPermissions.PermissionCallbacks {

	@ViewInject(id=R.id.recyclerView)
	private GridViewNoScroll recyclerView;
	private GridViewAdapter adapter;
	private ArrayList<ImageItem> selImageList;

	@ViewInject(id = R.id.forward_msg_ll)
	private LinearLayout linearLayout;

	@ViewInject(id = R.id.price)
	private EditText price;

	@ViewInject(id = R.id.radiogroup_return)
	private RadioGroup radioGroup_return_refund;

	@ViewInject(id = R.id.radio_refund)
	private RadioButton radio_refund;

	@ViewInject(id = R.id.radio_return)
	private RadioButton radio_return;

	//提交申请
	@ViewInject(id = R.id.commitApplayTv, click = "commitApplayTvClick")
	private TextView commitApplayTv;

	@ViewInject(id = R.id.refund_money)
	private TextView refund_money;

	@ViewInject(id = R.id.application_service)
	private TextView application_service;

	//留言
	@ViewInject(id = R.id.tv_msg)
	private EditText tv_msg;
	private String orderId;
	private String orderProductId;
	//1：退货   2：退款操作
	private String returnRefundType = "1";
	private int orderStatus;
	private int returnRefundStatus;
	private String productPrice;
	private Drawable drawable;
	private String path = "";
	private final int select_local_requestcode = 1;
	private final int select_camera_requestcode = 3;
	private int maxImgCount = 3;

	private final int camera_permission_requestcode = 100;
	private final int cropPhoto_permission_requestcode = 101;
	private String[] camera_permissions = {Manifest.permission.CAMERA};
	private ImagePicker imagePicker;
	public static final int IMAGE_ITEM_ADD = -1;
	public static final int REQUEST_CODE_PREVIEW = 101;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_refund);
		setCenter_title("申请售后");
		imagePicker = ImagePicker.getInstance();
		imagePicker.setMultiMode(true);
		imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
		imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
		imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
		imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
		imagePicker.setOutPutX(640);                         //保存文件的宽度。单位像素
		imagePicker.setOutPutY(960);                         //保存文件的高度。单位像素
		Intent intent = getIntent();
		orderStatus = intent.getIntExtra("orderStatus", -1);
		returnRefundStatus = intent.getIntExtra("returnRefundStatus", -1);
		productPrice = intent.getStringExtra("productPrice");
		orderId = intent.getStringExtra("orderId");
		orderProductId = intent.getStringExtra("orderProductId");
		init();
		drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.check_mark, null);
		//initListener();
		setData();
	}

	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == radio_refund.getId()) {
				returnRefundType = "2";
				radio_return.setCompoundDrawables(null, null, null, null);
				radio_refund.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
			}
			if (checkedId == radio_return.getId()) {
				returnRefundType = "1";
				radio_refund.setCompoundDrawables(null, null, null, null);
				radio_return.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
			}
		}
	};


	/**
	 * 初始化组建默认数据 / 状态
	 */
	private void setData() {
		switch (orderStatus) {
			case 2:
				returnRefundType = "2";
				price.setEnabled(false);
				radio_refund.setEnabled(false);
				radio_return.setEnabled(false);
				price.setText(StringUtils.getFormatMoney(productPrice));
				price.setBackgroundColor(ContextCompat.getColor(mContext, R.color.trans_white_color));
				break;
			case 3:
				returnRefundType = "2";
				price.setEnabled(false);
				radio_refund.setEnabled(false);
				radio_return.setEnabled(false);
				price.setText(StringUtils.getFormatMoney(productPrice));
				price.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
				break;
			case 4:
				returnRefundType = "2";
				price.setEnabled(true);
				radio_refund.setEnabled(true);
				radio_return.setEnabled(true);
				radioGroup_return_refund.setOnCheckedChangeListener(checkedChangeListener);
				price.setText(StringUtils.getFormatMoney(productPrice));
				price.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
				break;
			default:
				break;
		}
	}

	/**
	 * 初始化组建
	 */
	public void init() {
		String value = "申请服务*";
		String value2 = "退款金额*";
		int fstart = value.indexOf("*");
		int fend = fstart + "*".length();
		SpannableStringBuilder style = new SpannableStringBuilder(value);
		SpannableStringBuilder style2 = new SpannableStringBuilder(value2);
		style.setSpan(new ForegroundColorSpan(Color.RED), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		style2.setSpan(new ForegroundColorSpan(Color.RED), fstart, fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		refund_money.setText(style2);
		application_service.setText(style);


		selImageList = new ArrayList<>();
		adapter = new GridViewAdapter(this, selImageList, maxImgCount);
		adapter.setOnItemClickListener(imageItemClickListener);

		//recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
		//recyclerView.setHasFixedSize(true);
		recyclerView.setAdapter(adapter);
	}


	GridViewAdapter.OnRecyclerViewItemClickListener imageItemClickListener = new GridViewAdapter.OnRecyclerViewItemClickListener() {
		@Override
		public void onItemClick(View view, int position, int type) {
			switch (position) {
				case IMAGE_ITEM_ADD:
					//打开选择,本次允许选择的数量
					popupWindows = new PopupWindows(mContext, recyclerView);
					break;
				default:
					//打开预览
					if (type == -1) {
						selImageList.remove(position);
						adapter.setImages(selImageList);
						adapter.notifyDataSetChanged();
					} else {
						Intent intentPreview = new Intent(mContext, ImagePreviewDelActivity.class);
						intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, selImageList);
						intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
						startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
					}
					break;
			}
		}
	};

	private PopupWindow popupWindows;

	public class PopupWindows extends PopupWindow {

		public PopupWindows(final Context mContext, View parent) {
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
					dismiss();
					checkCameraPermission();
				}
			});
			photo.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
					startSelectLocalPhoto();
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private void startSelectLocalPhoto() {
		Intent intent = new Intent(mContext, ImageGridActivity.class);
		startActivityForResult(intent, select_local_requestcode);
	}


	public void startCamera() {
		ImagePicker.getInstance().takePicture(this, select_camera_requestcode);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		// EasyPermissions handles the request result.
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@AfterPermissionGranted(camera_permission_requestcode)
	private void checkCameraPermission() {
		if (EasyPermissions.hasPermissions(mContext, camera_permissions)) {
			startCamera();
		} else {
			// Request one permission
			EasyPermissions.requestPermissions(this, getString(R.string.camera_permission_tips), camera_permission_requestcode, camera_permissions);
		}
	}

	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
		if (requestCode == camera_permission_requestcode) {
			if (perms.size() == camera_permissions.length) {
				startCamera();
			}
		}
	}

	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
		EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
				getString(R.string.camera_permission_tips),
				R.string.setting, R.string.cancel, perms);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
			//添加图片返回
			if (data != null && requestCode == select_local_requestcode) {
				ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
				selImageList=images;
				adapter.setImages(selImageList);
				adapter.notifyDataSetChanged();
			}
		} else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
			//预览图片返回                                                    n
			if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
				ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
				selImageList = images;
				adapter.setImages(selImageList);
				adapter.notifyDataSetChanged();
			}
		} else if (resultCode == RESULT_OK && requestCode == select_camera_requestcode) {
			//发送广播通知图片增加了
			ImagePicker.galleryAddPic(this, imagePicker.getTakeImageFile());
			ImageItem imageItem = new ImageItem();
			imageItem.path = imagePicker.getTakeImageFile().getAbsolutePath();
			imagePicker.clearSelectedImages();
			imagePicker.addSelectedImageItem(0, imageItem, true);
			selImageList = imagePicker.getSelectedImages();
			adapter.setImages(selImageList);
			adapter.notifyDataSetChanged();

		} else if (resultCode == RESULT_OK && requestCode == EasyPermissions.SETTINGS_REQ_CODE) {
			boolean hasSomePermissions = EasyPermissions.hasPermissions(mContext, camera_permissions);
			if (hasSomePermissions) {
				startCamera();
			}
		}
	}


	public void commitApplayTvClick(View view) {
		//上传图片
		String returnPrice = price.getText().toString();
		double price = Double.parseDouble(returnPrice);
		if (price < 0.01) {
			toast.showToast(R.string.refund_amount_error);
			return;
		}
		if (price > Double.parseDouble(productPrice)) {
			toast.showToast(getString(R.string.refund_amount_over) + productPrice);
			return;
		}
		loadingToast.show();
		int num = selImageList.size();
		if (num > 1) {
			if (num > maxImgCount) {
				num = maxImgCount;
			}
			Map<String, Object> fileMap = new HashMap<>();
			fileMap.put("key", "file");
			ArrayList<File> fileList = new ArrayList<>();
			for (int i = 0; i < num ; i++) {
				String filePath = selImageList.get(i).path;
				File file = new File(BitmapHelper.compressBitmapWithStoreCache(mContext, filePath, 640, 960, 1 * 1024*1024, true));
				fileList.add(file);
			}
			//int num=fileList.size();
			//fileList.remove(num-1);
			fileMap.put("fileList", fileList);
			Map<String, Object> params = new HashMap<>();
			params.put("imgType", String.valueOf(2));
			GetDataQueue queue = new GetDataQueue();
			queue.setActionName(GetDataConfing.GetAction_uploadPictureOrFile);
			queue.setParamsNoJson(params);
			queue.setFileMap(fileMap);
			queue.setMediaType(GetDataQueue.MediaType.MUTILPART);
			queue.setWhat(GetDataConfing.what_uploadPictureOrFile);
			queue.setCallBack(callBack);
			getDataUtil.getData(queue);
		} else {
			upLoadApplay(new ArrayList<String>());
		}
	}

	IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.getWhat() == GetDataConfing.what_uploadPictureOrFile) {
				TypeToken<BaseResponse<List<String>>> typeToken = new TypeToken<BaseResponse<List<String>>>() {};
				BaseResponse<List<String>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					List<String> pics = baseResponse.getInfo();
					upLoadApplay(pics);
				}
			} else if (entity.what == GetDataConfing.what_GetProdctInfo) {
				TypeToken<BaseResponse> typeToken = new TypeToken<BaseResponse>() {};
				BaseResponse baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					toast.showToast("申请售后成功");
					sendBroadcast(new Intent(Constant.ORDER_STATUS_CHANGE));
					finish();
				}
			}
		}
	};


	private void upLoadApplay(List<String> pics) {
		Map<String, Object> orderMap = new HashMap<>();
		orderMap.put("userId", Utils.getUserId(mContext));
		orderMap.put("orderId", orderId);
		orderMap.put("orderProductId", orderProductId);
		orderMap.put("returnRefundType", returnRefundType);
		orderMap.put("refundTotalMoney", price.getText().toString());
		orderMap.put("attr1", tv_msg.getText().toString());
		orderMap.put("picIdentity", pics.size() > 0 ? "1" : "2");
		HashMap<String, Object> map = new HashMap<>();
		map.put("pics", pics);
		map.put("returnRefundOrderAuto", orderMap);
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_GetProdctInfo);
		queue.setParamsNoJson(map);
		queue.setWhat(GetDataConfing.what_GetProdctInfo);
		queue.setCallBack(callBack);
		queue.setMediaType(GetDataQueue.MediaType.JSON);
		getDataUtil.getData(queue);

	}

	@Override
	public void onBackPressed() {
		if (popupWindows != null && popupWindows.isShowing()) {
			popupWindows.dismiss();
		} else {
			super.onBackPressed();
		}
	}

}
