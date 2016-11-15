/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */
package com.yishanxiu.yishang.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.AddrListAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;

/**
 * tanghuan 订单收货人信息 2014年12月3日
 *
 * @Update @author FangDongzhang 2016/7/25
 */
public class RecipientActivity extends BaseUIActivity implements AdapterView.OnItemClickListener {
	/**
	 * 订单收货人信息列表
	 */
	@ViewInject(id = R.id.ptr_list)
	private ListView ptr_list;
	@ViewInject(id = R.id.add_address_layout, click = "add_address_layout_click")
	private LinearLayout add_address_layout;
	/**
	 * 收货人列表数据
	 */
	private List<JsonMap<String, Object>> data_shouhuaren;
	/**
	 * 收货人列表数据适配器
	 */
	private AddrListAdapter adapter_shouhuaren;

	private String addressId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_user_recipient_list);
		setCenter_title(R.string.user_msg);
		Intent intent = getIntent();

		initView();
		getDataRecipientList();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		//ptr_list.setMode(PullToRefreshBase.Mode.DISABLED);
		ptr_list.setOnItemClickListener(this);

		adapter_shouhuaren = new AddrListAdapter(mContext);
		adapter_shouhuaren.setType(1);
		adapter_shouhuaren.setSelectID(-1);
		ptr_list.setAdapter(adapter_shouhuaren);
		adapter_shouhuaren.setItemCompountClickListener(itemCompountClickListener);
	}

	private AddrListAdapter.ItemCompountClickListener itemCompountClickListener = new AddrListAdapter.ItemCompountClickListener() {

		@Override
		public void itemCompountClick(int parentPosition, Constant.UserRecipientType type, int childIndex) {

			if (type == Constant.UserRecipientType.EDIT_ADDR) {
				Intent intent = new Intent(mContext, NewRecipientActivity.class);
				intent.putExtra(ExtraKeys.Key_Msg1, data_shouhuaren.get(childIndex).toJson());
				startActivityForResult(intent, 1);
			} else if (type == Constant.UserRecipientType.DELETE_ADDR) {
				addressId = data_shouhuaren.get(childIndex).getStringNoNull("addressId");
				showDialog();
			} else if (type== Constant.UserRecipientType.SET_DEFAULT) {
				addressId = data_shouhuaren.get(childIndex).getStringNoNull("addressId");
				setDefaultAddress(addressId);
			}

		}
	};

	/**
	 * 设置默认收货地址
	 * @param addressId
	 */
	private void setDefaultAddress(String addressId) {
		loadingToast.show();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", Utils.getUserId(mContext));
		params.put("addressId",addressId);
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.GetAction_setDefaultAddress);
		queue.setWhat(GetDataConfing.what_setDefaultAddress);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			getDataRecipientList();
		}
	}


	/**
	 * 为收货人列表设定数据适配器
	 */
	private void setAdatper_shouHuoRenList(List<JsonMap<String, Object>> data) {
		this.data_shouhuaren = data;
		if (data_shouhuaren != null) {
			adapter_shouhuaren.setDatas(data);
			adapter_shouhuaren.notifyDataSetChanged();
		}
	}

	/**
	 * 获取服务器收货人列表
	 */
	private void getDataRecipientList() {
		loadingToast.show();

		Map<String, Object> params = new HashMap<>();
		params.put("userId", Utils.getUserId(mContext));
		GetDataQueue queue = new GetDataQueue();
		queue.setCallBack(callBack);
		queue.setActionName(GetDataConfing.GetAction_GetAddresslistByUsername);
		queue.setWhat(GetDataConfing.what_GetAddresslistByUsername);
		queue.setParamsNoJson(params);
		getDataUtil.getData(queue);
	}

	/**
	 * 调用服务删除地址信息
	 */
	private void deleteRecipient() {
		// if (addressId.equals(activity_parameter)) {
		// toast.showToast(R.string.address_using);
		// return;
		// }
		loadingToast.show();
		Map<String, Object> params = new HashMap<>();
		params.put("addressId", addressId);

		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_userDeleteAddressById);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_userDeleteAddressById);
		getDataUtil.getData(queue);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {

		@Override
		public void onLoaded(GetDataQueue entity) {
			if (entity.isOk()) {
				if (ShowGetDataError.isCodeSuccess(mContext, entity.getInfo())) {
					if (entity.what == GetDataConfing.what_GetAddresslistByUsername) {
						setAdatper_shouHuoRenList(
								JsonParseHelper.getJsonMap_List_JsonMap(entity.getInfo(), JsonKeys.Key_Info));
					} else if (entity.what == GetDataConfing.What_userDeleteAddressById) {
						JsonMap<String, Object> jsonMap = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(), JsonKeys.Key_Info);
						toast.showToast(jsonMap.getStringNoNull(JsonKeys.Key_Msg));
						removeItem(jsonMap.getStringNoNull("addressId"));
					} else if (entity.what == GetDataConfing.what_setDefaultAddress) {
						setItemDefault();
					}
				}
			} else {
				ShowGetDataError.showNetError(mContext);
			}
			loadingToast.dismiss();
		}
	};

	/**
	 * 更改数据 设置默认item
	 */
	private void setItemDefault() {
		for(int i=0;i<data_shouhuaren.size();i++){
			JsonMap<String,Object> jsonMap=data_shouhuaren.get(i);
			String addressIdTmp=jsonMap.getStringNoNull("addressId");
			if(addressId.equalsIgnoreCase(addressIdTmp)){
				jsonMap.put("isdefault",1);
			} else {
				jsonMap.put("isdefault",0);
			}
		}
		adapter_shouhuaren.setDatas(data_shouhuaren);
		adapter_shouhuaren.notifyDataSetChanged();

	}

	private void removeItem(String addressId) {
		int index=-1;
		for(int i=0;i<data_shouhuaren.size();i++){
			JsonMap<String,Object> jsonMap=data_shouhuaren.get(i);
			String addressIdTmp=jsonMap.getStringNoNull("addressId");
			if(addressId.equalsIgnoreCase(addressIdTmp)){
				index=i;
				break;
			}
		}
		if(index>=0){
			data_shouhuaren.remove(index);
			adapter_shouhuaren.notifyDataSetChanged();
		}
	}

	/**
	 * 添加地址的事件监听
	 */
	public void add_address_layout_click(View v) {
		Intent intent = new Intent(mContext, NewRecipientActivity.class);
		startActivityForResult(intent, 1);
	}


	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.user_like_confirm_cancle_delete_address);
		builder.setNegativeButton(R.string.cancel,dialogClickListener);
		builder.setPositiveButton(R.string.ensure,dialogClickListener);
		builder.show();
	}

	private AlertDialog.OnClickListener dialogClickListener = new AlertDialog.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(which== Dialog.BUTTON_POSITIVE){
				deleteRecipient();
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
		// startActivityForResult(intent, 1);
	}
}
