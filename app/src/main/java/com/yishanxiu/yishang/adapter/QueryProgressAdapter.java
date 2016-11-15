package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.StringUtils;

import net.tsz.afinal.json.JsonMap;

import java.util.List;
import java.util.Map;

/**
 * 查询进度适配器
 *
 * @author FangDongzhang 2016/7/11
 */
public class QueryProgressAdapter extends MyBaseAdapter {

    private JsonMap<String, Object> jsonMap = null;

    public QueryProgressAdapter(Context context) {
        super(context);
    }

    public QueryProgressAdapter(Context context, List<? extends Map<String, ?>> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.query_progress_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        setTag(i, viewHolder);
        bindDatas(i, viewHolder);
        return view;

    }

    private void bindDatas(int i, ViewHolder viewHolder) {
        jsonMap = (JsonMap<String, Object>) datas.get(i);
        if (jsonMap.getInt("returnRefundType") == 1) {
            switch (jsonMap.getInt("returnRefundStatusItemId")) {
                case 1:
                    viewHolder.progress_name.setText("用户申请退货");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setVisibility(View.VISIBLE);
                    viewHolder.phone.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setText(
                            "退款金额：" + StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("refundTotalMoney")));
                    viewHolder.phone.setText("退款说明：" + jsonMap.getStringNoNull("attr1"));
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 7:
                    viewHolder.progress_name.setText("商家同意退货申请");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setVisibility(View.VISIBLE);
                    viewHolder.phone.setVisibility(View.VISIBLE);
                    viewHolder.zip_code.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setText("收件人：" + jsonMap.getStringNoNull("contactPerson"));
                    viewHolder.phone.setText("电话：" + jsonMap.getStringNoNull("phone"));
                    viewHolder.zip_code.setText("地址：" + jsonMap.getStringNoNull("returnAddress"));
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 2:
                    viewHolder.progress_name.setText("商家驳回退货申请");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setText("商家驳回：" + jsonMap.getStringNoNull("returnRefundRejectReason"));
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 8:
                    viewHolder.progress_name.setText("买家退货");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setVisibility(View.VISIBLE);
                    viewHolder.phone.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setText("物流公司：" + jsonMap.getStringNoNull("logistic"));
                    viewHolder.phone.setText("运单号：" + jsonMap.getStringNoNull("logisticNumber"));
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 3:
                    viewHolder.progress_name.setText("商家确定收货");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.GONE);
                    viewHolder.addressee.setVisibility(View.GONE);
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 9:
                    viewHolder.progress_name.setText("商品驳回");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setText("驳回理由：" + jsonMap.getStringNoNull("returnRefundRejectReason"));
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 4:
                    viewHolder.progress_name.setText("平台同意退货申请");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.GONE);
                    viewHolder.addressee.setVisibility(View.GONE);
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 5:
                    viewHolder.progress_name.setText("平台驳回退货申请");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setText("驳回理由：" + jsonMap.getStringNoNull("returnRefundRejectReason"));
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 6:
                    viewHolder.progress_name.setText(context.getResources().getString(R.string.refund_finish));
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.GONE);
                    viewHolder.addressee.setVisibility(View.GONE);
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;

                default:
                    break;
            }
        } else if (jsonMap.getInt("returnRefundType") == 2) {
            switch (jsonMap.getInt("returnRefundStatusItemId")) {
                case 1:
                    viewHolder.progress_name.setText("用户申请退款");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setVisibility(View.VISIBLE);
                    viewHolder.phone.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setText(
                            "退款金额：" + StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("refundTotalMoney")));
                    viewHolder.phone.setText("退款说明：" + jsonMap.getStringNoNull("attr1"));
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 3:
                    viewHolder.progress_name.setText("商家同意退款申请");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.GONE);
                    viewHolder.addressee.setVisibility(View.GONE);
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 2:
                    viewHolder.progress_name.setText("商家驳回退款申请");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setText("驳回理由：" + jsonMap.getStringNoNull("returnRefundRejectReason"));
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 4:
                    viewHolder.progress_name.setText("平台同意退款申请");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.GONE);
                    viewHolder.addressee.setVisibility(View.GONE);
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 5:
                    viewHolder.progress_name.setText("平台驳回退款申请");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setVisibility(View.VISIBLE);
                    viewHolder.addressee.setText("驳回理由：" + jsonMap.getStringNoNull("returnRefundRejectReason"));
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;
                case 6:
                    viewHolder.progress_name.setText("退款已完成");
                    viewHolder.progress_time
                            .setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("returnRefundStatusTime")));
                    viewHolder.llt_q.setVisibility(View.GONE);
                    viewHolder.addressee.setVisibility(View.GONE);
                    viewHolder.phone.setVisibility(View.GONE);
                    viewHolder.zip_code.setVisibility(View.GONE);
                    viewHolder.addr.setVisibility(View.GONE);
                    break;

                default:
                    break;
            }
        }
    }

    private void setTag(int i, ViewHolder viewHolder) {

    }

    private class ViewHolder {
        private TextView progress_name;
        private TextView progress_time;
        private TextView addressee;
        private TextView phone;
        private TextView zip_code;
        private TextView addr;
        private LinearLayout llt_q;

        public ViewHolder(View view) {
            progress_name = (TextView) view.findViewById(R.id.progress_name);
            progress_time = (TextView) view.findViewById(R.id.progress_time);
            addressee = (TextView) view.findViewById(R.id.addressee);
            phone = (TextView) view.findViewById(R.id.t_phone);
            zip_code = (TextView) view.findViewById(R.id.zip_code);
            addr = (TextView) view.findViewById(R.id.addr);
            llt_q = (LinearLayout) view.findViewById(R.id.llt_q);
        }
    }
}
