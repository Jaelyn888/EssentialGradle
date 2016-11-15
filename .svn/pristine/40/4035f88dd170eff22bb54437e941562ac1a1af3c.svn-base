/**
 * Copyright (c) 2013 An Yaming,  All Rights Reserved
 */
package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;
import com.yishanxiu.yishang.R;

import java.util.List;
import java.util.Map;

/**
 * @author tanghuan
 * @ClassName: ProductCommentAdapter
 * @Description: TODO(商品的评论的数据适配器)
 * @date 2014年12月3日 下午8:00:27
 */
public class PaymentListAdapter extends MyBaseAdapter {


    private int selectId = -1;

    public PaymentListAdapter(Context context) {
        super(context);
    }

    public PaymentListAdapter(Context context, List<? extends Map<String, ?>> datas) {
        super(context, datas);
    }

    public void setSelected(int str) {
        selectId = str;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PaymentListAdapterViewHolder paymentListAdapterViewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_shopping_zhifu_peisong_way, null);
            paymentListAdapterViewHolder = new PaymentListAdapterViewHolder();
            convertView.setTag(paymentListAdapterViewHolder);
            paymentListAdapterViewHolder.payLogo_iv = (ImageView) convertView.findViewById(R.id.pay_logo);
            paymentListAdapterViewHolder.payName_tv = (TextView) convertView.findViewById(R.id.item_shopping_addorder_gvns_payment);
            paymentListAdapterViewHolder.pay_selected_iv = (CheckBox) convertView.findViewById(R.id.pay_select_iv);
        } else {
            paymentListAdapterViewHolder = (PaymentListAdapterViewHolder) convertView.getTag();
        }
        if (selectId == position) {
            //设置背景（就是选中行的背景）
            paymentListAdapterViewHolder.pay_selected_iv.setChecked(true);
        } else {
            //设置背景（就是未选中行的背景）
            paymentListAdapterViewHolder.pay_selected_iv.setChecked(false);
        }
        JsonMap map = (JsonMap) datas.get(position);
        paymentListAdapterViewHolder.payLogo_iv.setImageResource(map.getInt("payLogo"));
        paymentListAdapterViewHolder.payName_tv.setText(map.getStringNoNull("payName"));
        return convertView;
    }

    class PaymentListAdapterViewHolder {
        ImageView payLogo_iv;
        TextView payName_tv;
        CheckBox pay_selected_iv;
    }
}
