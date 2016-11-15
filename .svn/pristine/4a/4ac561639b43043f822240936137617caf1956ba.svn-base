package com.yishanxiu.yishang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.Utils;

import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FangDongzhang 填写退货信息
 * @date 2016/7/11
 */
public class ReturnActivity extends BaseUIActivity {

    @ViewInject(R.id.addressee)
    private TextView addressee;

    @ViewInject(R.id.phone)
    private TextView phone;

    @ViewInject(R.id.zip_code)
    private TextView zip_code;

    @ViewInject(R.id.addr)
    private TextView addr;

    @ViewInject(R.id.submit)
    private TextView submit;

    @ViewInject(R.id.logistic)
    private EditText logistic_e;
    private String logistic = "邮政包裹/平邮";

    @ViewInject(R.id.logistic_number)
    private EditText logistic_number_e;
    private String logistic_number;

    @ViewInject(R.id.spinner)
    private MaterialSpinner spinner;


    private String returnRefundOrderId;
    private JsonMap<String, Object> jsonMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        setCenter_title(R.string.fill_return_info);
        ViewUtils.inject(this);
        Intent intent = getIntent();
        String shopId = intent.getStringExtra("shopId");
        returnRefundOrderId = intent.getStringExtra("returnRefundOrderId");
        getServerData(shopId);


        spinner.setItems(getResources().getStringArray(R.array.logistics));
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + spinner.getText().toString().trim(), Snackbar.LENGTH_LONG).show();
                logistic = spinner.getText().toString().trim();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData(JsonMap<String, Object> jsonMap) {
        addressee.setText("收件人：" + jsonMap.getStringNoNull("contactPerson"));
        phone.setText("电话：" + jsonMap.getStringNoNull("phone"));
        // zip_code.setText("邮编：" + jsonMap.getStringNoNull("returnAddress"));
        addr.setText("地址：" + jsonMap.getStringNoNull("returnAddress"));
    }

    /**
     * 查询收货人地址
     */
    private void getServerData(String shopId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        GetDataQueue queue = new GetDataQueue();
        queue.setCallBack(callBack);
        queue.setActionName(GetDataConfing.Query_queryDefaultAddress);
        queue.setWhat(GetDataConfing.What_Query_queryDefaultAddress);
        queue.setParamsNoJson(params);
        getDataUtil.getData(queue);
    }

    @OnClick(R.id.submit)
    private void submit(View v) {
        if (submitJudge()) {
            refundOrderUpdate(jsonMap);
        }
    }

    private boolean submitJudge() {

        if (TextUtils.isEmpty(logistic)) {
            toast.showToast(R.string.logistic_null);
            return false;
        } else {
            numberJudge();
            return numberJudge();
        }
    }

    private boolean numberJudge() {
        boolean b = true;
        logistic_number = logistic_number_e.getText().toString().trim();
        if (TextUtils.isEmpty(logistic_number)) {
            toast.showToast(R.string.logistic_number);
            b = false;
        } else {
            b = true;
        }
        return b;

    }

    /**
     * 提交
     */
    private void refundOrderUpdate(JsonMap<String, Object> jsonMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("returnRefundLatestStatus", "8");
        params.put("returnRefundOrderId", returnRefundOrderId);
        params.put("userId", Utils.getUserId(mContext));
        params.put("logisticNumber", logistic_number);
        params.put("logistic", logistic);
        params.put("returnRefundType", "1");
        params.put("returnAddress", jsonMap.getStringNoNull("returnAddress"));
        params.put("contactPerson", jsonMap.getStringNoNull("contactPerson"));
        params.put("phone", jsonMap.getStringNoNull("phone"));
        GetDataQueue queue = new GetDataQueue();
        queue.setCallBack(callBack);
        queue.setActionName(GetDataConfing.RefundOrder_Update);
        queue.setWhat(GetDataConfing.What_RefundOrder_Update);
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
            if (entity.isOk()) {
                if (ShowGetDataError.isCodeSuccess(mContext, entity.getInfo())) {
                    if (entity.what == GetDataConfing.What_Query_queryDefaultAddress) {
                        jsonMap = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
                                JsonKeys.Key_Info);
                        initData(jsonMap);
                    } else if (entity.what == GetDataConfing.What_RefundOrder_Update) {
                        JsonMap<String, Object> data = JsonParseHelper.getJsonMap_JsonMap(entity.getInfo(),
                                JsonKeys.Key_Info);
                        toast.showToast("提交成功");
                        sendBroadcast(new Intent(Constant.ORDER_STATUS_CHANGE));
                        finish();
                    }
                }
            } else {
                ShowGetDataError.showNetError(mContext);
            }
        }
    };
}
