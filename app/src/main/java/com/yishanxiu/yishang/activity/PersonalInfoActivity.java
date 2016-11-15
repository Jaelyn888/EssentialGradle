package com.yishanxiu.yishang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.WheelPicker.OnItemSelectedListener;
import com.google.gson.reflect.TypeToken;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.user.ProvinceModel;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.ModleUtils;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.utils.Utils;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 个人资料
 * 
 * @author FangDongzhang 2016/7/19
 *
 */
public class PersonalInfoActivity extends BaseUIActivity {

	@ViewInject(id=R.id.user_nickname_tv)
	 private TextView user_nickname_tv;
	@ViewInject(id=R.id.user_nickname_layout,click = "user_nickname_layout_click")
	private LinearLayout user_nickname_layout;

	@ViewInject(id=R.id.sex_tv)
	private TextView sex_tv;
	@ViewInject(id=R.id.sex_layout,click = "sex_layout_click")
	private LinearLayout sex_layout;

	@ViewInject(id=R.id.birthday_tv)
	private TextView birthday_tv;
	@ViewInject(id=R.id.birthday_layout,click = "birthday_layout_click")
	private LinearLayout birthday_layout;

	@ViewInject(id=R.id.user_addr_tv)
	private TextView user_addr_tv;
	@ViewInject(id=R.id.user_addr_layout,click = "user_addr_layout_click")
	private LinearLayout user_addr_layout;

	@ViewInject(id=R.id.user_discri_tv)
	private TextView user_discri_tv;
	@ViewInject(id=R.id.user_discri_layout,click = "user_discri_layout_click")
	private LinearLayout user_discri_layout;

	@ViewInject(id=R.id.rigist_time_tv)
	private TextView rigist_time_tv;
	@ViewInject(id=R.id.rigist_time_layout,click = "rigist_time_layout_click")
	private LinearLayout rigist_time_layout;


	private List<String> provinces = new ArrayList<>();
	private List<String> provinceIds = new ArrayList<>();
	private List<String> sex = new ArrayList<>();
	private String provinceId;
	private String provinceName;
	private String userSex;
	private String birthday;
	private int popwindowType =1;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info);
		setCenter_title(getResources().getString(R.string.personal_info));
		initData();
		getServerData();
		sex.add("男");
		sex.add("女");
	}

	//修改昵称
	public void user_nickname_layout_click(View view){
		Intent intent=new Intent(mContext,ModifyInfoActivity.class);
		intent.putExtra(ExtraKeys.Key_Msg1,"1");
		startActivityForResult(intent,1);
	}

	//修改sex
	public void sex_layout_click(View view){
		 new PopupWindows(mContext, view, 1);
	}
	//修改出生日期
	public void birthday_layout_click(View view){
		new PopupWindows(mContext, view, 2);
	}
	//修改所在地
	public void user_addr_layout_click(View view){
		if(provinceIds.size()>0) {
			new PopupWindows(mContext, view, 3);
		} else {
			toast.showToast(R.string.province_data_loading);
		}
	}
	//修改注册时间
	public void rigist_time_layout_click(View view){

	}
	//修改简介
	public void user_discri_layout_click(View view){
		Intent intent=new Intent(mContext,ModifyInfoActivity.class);
		intent.putExtra(ExtraKeys.Key_Msg1,"5");
		startActivityForResult(intent,1);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		user_nickname_tv.setText(Utils.getUserNickName(mContext));
		if (Utils.getUserSex(mContext) == 0) {
			sex_tv.setText("男");
		} else if (Utils.getUserSex(mContext) == 1) {
			sex_tv.setText("女");
		}
		birthday_tv.setText(Utils.getUserBrithday(mContext));
		user_addr_tv.setText(Utils.getProvinceName(mContext));
		user_discri_tv.setText(Utils.getUserDiscri(mContext));
		rigist_time_tv.setText(StringUtils.getTimeFormatYMd(Utils.getRegisterTime(mContext)));

	}

	/**
	 * 修改个人信息
	 */
	private void modifyInfo(String requestType, String key, String value) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("requestType", requestType);
		params.put("userId", Utils.getUserId(mContext));
		params.put(key, value);
		if (key.equals("provinceName")) {
			if (provinceId == null) {
				provinceId = provinceIds.get(0);
			}
			params.put("provinceId", provinceId);
		}
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setTag(requestType);
		queue.setActionName(GetDataConfing.UserInfo_ModUserBaseInfo);
		queue.setWhat(GetDataConfing.What_UserInfo_ModUserBaseInfo);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 获得省
	 *
	 */
	private void getServerData() {
		Map<String, Object> params = new HashMap<String, Object>();
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.GetAction_GetProvince);
		queue.setWhat(GetDataConfing.what_GetProvince);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 获取服务器数据的返回信息
	 */
	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.what == GetDataConfing.What_UserInfo_ModUserBaseInfo) {
				BaseResponse baseResponse = new ModleUtils().mapTo(entity);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					toast.showToast(baseResponse.getMsg());
					saveData((String)(entity.getTag()));
				}
			}else if (entity.what == GetDataConfing.what_GetProvince) {
				TypeToken<BaseResponse<List<ProvinceModel>>> typeToken = new TypeToken<BaseResponse<List<ProvinceModel>>>() {};
				BaseResponse<List<ProvinceModel>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(mContext, baseResponse)) {
					List<ProvinceModel> provinceModelList = baseResponse.getInfo();

					for (ProvinceModel provinceModel : provinceModelList) {
						provinces.add(provinceModel.getName());
						provinceIds.add(provinceModel.getProvinceId());
					}
				}
			}
		}
	};

	private void saveData(String requestType) {
		if (requestType.equalsIgnoreCase("2")) {
			Utils.setUserSex(mContext, Integer.valueOf(userSex));
		} else if (requestType.equalsIgnoreCase("3")) {
			Utils.setUserBrithday(mContext, birthday);
		}else if (requestType.equalsIgnoreCase("4")) {
			Utils.setProvinceName(mContext, provinceName);
		}
		initData();
	}

	/**
	 * listview监听事件
	 */

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent, int type) {
			View view = null;
			popwindowType=type;
			switch (type) {
			case 1:
				view = View.inflate(mContext, R.layout.item_popupwindows_wheelpicker, null);
				break;
			case 2:
				view = View.inflate(mContext, R.layout.item_popupwindows_number, null);
				break;
			case 3:
				view = View.inflate(mContext, R.layout.item_popupwindows_wheelpicker, null);
				break;

			default:
				break;
			}
			view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			TextView fixed = (TextView) view.findViewById(R.id.fixed);
			if (type == 3 || type == 1) {
				WheelPicker wheel_province = (WheelPicker) view.findViewById(R.id.wheel_province);
				wheel_province.setOnItemSelectedListener(itemSelectedListener);
				if (type == 3) {
					wheel_province.setData(provinces);
				} else if (type == 1) {
					wheel_province.setData(sex);
					wheel_province.setCyclic(false);
					wheel_province.setSelectedItemPosition(0);

				}
				if (type == 3) {
					fixed.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							dismiss();
							/**
							 * 默认不选时
							 */
							if (provinceName == null || provinceName.equals("")) {
								provinceName = provinces.get(0);
								provinceId = provinceIds.get(0);
							}

							modifyInfo("4", "provinceName", provinceName);

						}
					});
				} else if (type == 1) {
					fixed.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							dismiss();
							modifyInfo("2", "userSex", userSex);
						}
					});

				}
			} else if (type == 2) {
				DatePicker date = (DatePicker) view.findViewById(R.id.datePicker);
				date.init(date.getYear(), date.getMonth(), date.getDayOfMonth(), new OnDateChangedListener() {

					public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						birthday = String.valueOf(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
					}
				});
				fixed.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						dismiss();
						if (birthday == null || birthday.equals("")) {
							birthday = Utils.getUserBrithday(getApplicationContext());

						}
						modifyInfo("3", "birthday", birthday);

					}
				});
			}
			RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rlt_pop);
			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});

			TextView cancel = (TextView) view.findViewById(R.id.cancel);
			cancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	/**
	 * WheelPicker滑动结束监听器
	 */
	private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(WheelPicker picker, Object data, int position) {
			// toast.showToast("" + data);
			if (popwindowType == 1) {
				userSex = String.valueOf(position);
			} else if (popwindowType == 3) {
				provinceName = provinces.get(position);
				provinceId = provinceIds.get(position);
			}

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			initData();
		}
	}
}