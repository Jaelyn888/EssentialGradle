package com.yishanxiu.yishang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.Utils;
import com.yishanxiu.yishang.view.WheelpickerLinkagePopupWindows;

import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FangDongzhang
 * @TODO新建收获人
 * @date 2016/7/25
 */
public class NewRecipientActivity extends BaseUIActivity {

    private List<String> provinces = new ArrayList<>();
    private List<String> provinceIds = new ArrayList<>();
    private String provinceId_t;
    private List<List<String>> citys = new ArrayList<>();
    private List<List<String>> cityIds = new ArrayList<>();
    private String cityIds_t;
    private List<List<List<String>>> districtVos = new ArrayList<>();
    private List<List<List<String>>> districtVoIds = new ArrayList<>();
    private String districtVoIds_t;
    @ViewInject(id = R.id.llt_all_province, click = "llt_all_province_click")
    private LinearLayout llt_all_province;
    @ViewInject(id = R.id.recipient)
    private EditText recipient;
    private String recipientName;
    @ViewInject(id = R.id.recipient_phone)
    private EditText recipient_phone;
    private String recipientPhone;
    @ViewInject(id = R.id.recipient_all_addr)
    private TextView recipient_all_addr;
    private String recipientAllAddr ;
    @ViewInject(id = R.id.recipient_addr_detial)
    private EditText recipient_addr_detial;
    private String recipientAddrDetial;

    private int arg = 0;
    private int argd = 0;
    private String addressId = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        setCenter_title(R.string.add_addr);
        setBtn_menuText(R.string.save, clickListener);
        Intent intent = getIntent();
        String data = intent.getStringExtra(ExtraKeys.Key_Msg1);
        if (!TextUtils.isEmpty(data)) {
            setCenter_title(R.string.modify_msg);
            JsonMap<String, Object> dataJson = JsonParseHelper.getJsonMap(data);
            addressId = dataJson.getStringNoNull("addressId");
            recipient.setText(dataJson.getStringNoNull("consignee"));
            recipient_phone.setText(dataJson.getStringNoNull("phone"));
            recipient_all_addr.setText(dataJson.getStringNoNull("provincesRegions"));
            recipient_addr_detial.setText(dataJson.getStringNoNull("detail"));
            provinceId_t = dataJson.getStringNoNull("provinceId");
            cityIds_t = dataJson.getStringNoNull("cityId");
            districtVoIds_t = dataJson.getStringNoNull("districtId");
        }

        getDataProvince();
    }

    /**
     * @save 保存按钮监听事件
     */
    private OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (judgeNameNo()) {
                saveProvince();
            }
        }
    };

    public boolean judgeNameNo() {
        boolean isjudge = true;
        recipientName = recipient.getText().toString();
        if (recipientName != null && !recipientName.equals("")) {
            return judgeMobileNo();
        } else if (recipientName == null || "".equals(recipientName)) {
            toast.showToast(R.string.recipientName);
            isjudge = false;
        }
        return isjudge;
    }

    public boolean judgeMobileNo() {
        boolean isjudge = true;
        recipientPhone = recipient_phone.getText().toString();
        if (recipientPhone == null || "".equals(recipientPhone)) {
            toast.showToast(R.string.register_name_null);
            isjudge = false;
        } else if (recipientPhone.length() < 11) {
            toast.showToast(R.string.phone_formal_error);
            isjudge = false;
        } else {
            return judgeAddrNo();
        }
        return isjudge;
    }

    public boolean judgeAddrNo() {
        boolean isjudge = true;
        recipientAllAddr = recipient_all_addr.getText().toString();
        if (recipientAllAddr == null || "".equals(recipientAllAddr)) {
            toast.showToast(R.string.recipientAddr);
            isjudge = false;
        } else {
            return judgeAddrDetailNo();
        }
        return isjudge;
    }

    public boolean judgeAddrDetailNo() {
        boolean isjudge = true;
        recipientAddrDetial = recipient_addr_detial.getText().toString();
        if (recipientAddrDetial == null || "".equals(recipientAddrDetial)) {
            toast.showToast(R.string.recipientAddrDetail);
            isjudge = false;
        }
        return isjudge;
    }

    /**
     * 保存 新收货人
     */
    private void saveProvince() {
        loadingToast.show();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", Utils.getUserId(mContext));
        params.put("consignee", recipientName);
        params.put("phone", recipientPhone);
        params.put("provinceId", provinceId_t);
        params.put("cityId", cityIds_t);
        if (addressId != null && !addressId.equals("")) {
            params.put("addressId", addressId);
        }
        params.put("districtId", districtVoIds_t);
        params.put("detail", recipientAddrDetial);
        params.put("provincesRegions", recipientAllAddr);
        params.put("consigneeAddress", recipientAllAddr + recipientAddrDetial);
        GetDataQueue queue = new GetDataQueue();
        queue.setCallBack(callBack);
        queue.setParamsNoJson(params);
        queue.setActionName(GetDataConfing.Action_userInserAddress);
        queue.setWhat(GetDataConfing.What_Action_userInserAddress);
        getDataUtil.getData(queue);
    }

    /**
     * 查询可配送的省
     */
    private void getDataProvince() {
        loadingToast.show();
        GetDataQueue queue = new GetDataQueue();
        queue.setActionName(GetDataConfing.Action_userGetProvince);
        queue.setParams(null);
        queue.setCallBack(callBack);
        queue.setWhat(GetDataConfing.What_Action_userGetProvince);
        getDataUtil.getData(queue);
    }

    /**
     * 获取服务器信息的返回
     */
    private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {

        @Override
        public void onLoaded(GetDataQueue entity) {
            loadingToast.dismiss();
            if (entity.isOk()) {
                if (entity.what == GetDataConfing.What_Action_userGetProvince) {
                    List<JsonMap<String, Object>> datas = JsonParseHelper.getJsonMap_List_JsonMap(entity.getInfo(),
                            JsonKeys.Key_Info);
                    dataUtils(datas);

                } else if (entity.what == GetDataConfing.What_Action_userInserAddress) {
                    setResult(RESULT_OK);
                    finish();
                }
            } else {

                ShowGetDataError.showNetError(mContext);
            }

        }
    };

    /**
     * 处理网络请求数据
     */
    private void dataUtils(List<JsonMap<String, Object>> datas) {
        for (int i = 0; i < datas.size(); i++) {

            JsonMap<String, Object> jsonMap = datas.get(i);
            List<JsonMap<String, Object>> cityDatas = datas.get(i).getList_JsonMap("cityList");
            List<String> listcitys = new ArrayList<String>();
            List<String> listcityIds = new ArrayList<String>();
            List<List<String>> listdiatricts = new ArrayList<List<String>>();
            List<List<String>> listdistrictVoIds = new ArrayList<List<String>>();

            for (int j = 0; j < cityDatas.size(); j++) {

                List<String> listvs = new ArrayList<String>();
                List<String> listvsId = new ArrayList<String>();
                JsonMap<String, Object> jsonMapc = cityDatas.get(j);
                List<JsonMap<String, Object>> districtVoList = cityDatas.get(j).getList_JsonMap("districtVoList");
                listcitys.add(j, jsonMapc.getStringNoNull("name"));
                listcityIds.add(j, jsonMapc.getStringNoNull("parentId"));

                for (int v = 0; v < districtVoList.size(); v++) {
                    JsonMap<String, Object> jsonMapv = districtVoList.get(v);
                    listvs.add(v, jsonMapv.getStringNoNull("name"));
                    listvsId.add(v, jsonMapv.getStringNoNull("parentId"));
                }

                listdiatricts.add(listvs);
                listdistrictVoIds.add(listvsId);
                // cityIds.get(j).add(jsonMapc.getStringNoNull("parentId"));
            }
            citys.add(listcitys);
            cityIds.add(listcityIds);
            districtVos.add(listdiatricts);
            districtVoIds.add(listdistrictVoIds);
            provinces.add(jsonMap.getStringNoNull("name"));
            provinceIds.add(jsonMap.getStringNoNull("provinceId"));
        }
    }

    /**
     * @listview监听事件 click
     */
    private WheelpickerLinkagePopupWindows popupWindows = null;


    public void llt_all_province_click(View v) {
        hideSoftKeyboard();
        if (provinces.size() > 0 && citys.size() > 0 && districtVos.size() > 0) {

            popupWindows = new WheelpickerLinkagePopupWindows(mContext, v, provinces, citys.get(0),
                    districtVos.get(0).get(0));
            popupWindows.setOnItemSelectedListener(onItemSelectedListen);

            recipient_all_addr.setText(
                    provinces.get(0) + citys.get(0).get(0) + districtVos.get(0).get(0).get(0));
            provinceId_t = provinceIds.get(0);
            cityIds_t = cityIds.get(0).get(0);
            districtVoIds_t = districtVoIds.get(arg).get(argd).get(0);


        } else {
            toast.showToast("获取省市区数据失败");
        }
    }

    private WheelpickerLinkagePopupWindows.OnItemSelectedListen onItemSelectedListen = new WheelpickerLinkagePopupWindows.OnItemSelectedListen() {

        @Override
        public void onItemSelect(int type, int position) {
            if (type == 1) {
                popupWindows.setDatas(provinces, citys.get(position), districtVos.get(position).get(0), position, 0);
                arg = position;
                provinceId_t = provinceIds.get(position);
                cityIds_t = cityIds.get(position).get(0);
                districtVoIds_t = districtVoIds.get(position).get(0).get(0);
                recipient_all_addr.setText(
                        provinces.get(position) + citys.get(position).get(0) + districtVos.get(position).get(0).get(0));
            } else if (type == 2) {
                argd = position;
                cityIds_t = cityIds.get(arg).get(position);
                districtVoIds_t = districtVoIds.get(arg).get(position).get(0);
                popupWindows.setDatas(provinces, citys.get(arg), districtVos.get(arg).get(position), arg, position);
                recipient_all_addr.setText(
                        provinces.get(arg) + citys.get(arg).get(position) + districtVos.get(arg).get(position).get(0));

            } else if (type == 3) {
                // popupWindows.setDatas(provinces, citys.get(arg),
                // districtVos.get(arg).get(position), arg, position);
                districtVoIds_t = districtVoIds.get(arg).get(argd).get(position);
                recipient_all_addr.setText(
                        provinces.get(arg) + citys.get(arg).get(argd) + districtVos.get(arg).get(argd).get(position));
            }
        }

    };
}
