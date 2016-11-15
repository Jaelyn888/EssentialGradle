package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.utils.Utils;

import net.tsz.afinal.json.JsonMap;

import java.util.List;
import java.util.Map;

/**
 * Created by Jaelyn on 2015/10/10 0010.
 * <p>
 * update 2016/7/6
 *
 * @author FangDongzhang
 */
public class ShopAddOrderProductAdapter extends MyBaseAdapter {
    public ShopAddOrderProductAdapter(Context context) {
        super(context);
    }

    public ShopAddOrderProductAdapter(Context context, List<? extends Map<String, ?>> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_shopping_addrder, viewGroup, false);
            shopCartAdapterViewHolder = new ShopAddOrderGoodsAdapterViewHolder();
            ViewUtils.inject(shopCartAdapterViewHolder, view);
            view.setTag(shopCartAdapterViewHolder);
        } else {
            shopCartAdapterViewHolder = (ShopAddOrderGoodsAdapterViewHolder) view.getTag();
        }
        bindDatas(i, shopCartAdapterViewHolder);
        return view;
    }

    // "skuShopCartVo":[{"shopcartId":116,"userId":1,"productSkuId":493,"productCount":1,"isSelected":1,
    // "isDeleted":0,"createTime":"1469617243324","createUser":1,
    // "modifyTime":"1469617243324","modifyUser":111,"productId":127,
    // "shopId":1,"specificationValue":"中国码:38","prices":25.000,"
    // path":"http://essential-test.oss-cn-shanghai.aliyuncs.com/20160727/product/14696172053476f9abfdaf.jpg",
    // "productName":"df","saleStatus":1,"activityPrices":null}],
    private void bindDatas(int i, ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder) {
        JsonMap<String, Object> jsonMap = (JsonMap<String, Object>) datas.get(i);
        shopCartAdapterViewHolder.i_s_a_o_tv_productname.setText(jsonMap.getStringNoNull("productName"));
        shopCartAdapterViewHolder.i_s_a_o_tv_productnum.setText(
                context.getString(R.string.shopping_tv_text_productnum_qz) + jsonMap.getStringNoNull("productCount"));
        shopCartAdapterViewHolder.productSpecification.setText(jsonMap.getStringNoNull("specificationValue"));


        String activityPrices = jsonMap.getStringNoNull("activityPrices");
        String prices = jsonMap.getStringNoNull("prices");
        if (TextUtils.isEmpty(activityPrices) || activityPrices.equalsIgnoreCase("0") && activityPrices.equals(prices)) {
            shopCartAdapterViewHolder.goods_price_tv.setText(prices);
        } else {
            shopCartAdapterViewHolder.goods_price_tv.setText(StringUtils.getFormatMoneyWithSign(activityPrices));
            shopCartAdapterViewHolder.product_price_tv.setText(StringUtils.getFormatMoneyWithSign(prices));
            shopCartAdapterViewHolder.product_price_tv.setVisibility(View.VISIBLE);
            Utils.addDeleteLine(shopCartAdapterViewHolder.product_price_tv);
            shopCartAdapterViewHolder.product_price_tv.setSelected(true);
        }


        String str = jsonMap.getStringNoNull("path");
        str = StringUtils.get_MImage(str);
        BitmapHelper.loadImage(context, str, shopCartAdapterViewHolder.i_s_pl_aiv_pic1, BitmapHelper.LoadImgOption.BrandLogo);
    }

    class ShopAddOrderGoodsAdapterViewHolder {
        @ViewInject(R.id.i_s_a_o_tv_productname)
        TextView i_s_a_o_tv_productname;
        @ViewInject(R.id.i_s_a_o_tv_productnum)
        TextView i_s_a_o_tv_productnum;
        @ViewInject(R.id.productSpecification)
        TextView productSpecification;
        @ViewInject(R.id.i_s_pl_aiv_pic1)
        ImageView i_s_pl_aiv_pic1;
        @ViewInject(R.id.goods_price_tv)
        TextView goods_price_tv;
        @ViewInject(R.id.product_price_tv)
        private TextView product_price_tv; // 原价
    }
}
