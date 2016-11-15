package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.R;

/**
 * Created by Jaelyn on 2015/12/14 0014.
 * 申请退货 显示商品信息
 */
public class ApplyReturnProductAdapter extends MyBaseAdapter {
    public ApplyReturnProductAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GoodsListAdapterViewHolder goodsListAdapterViewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.shopcart_goods_item, null);
            goodsListAdapterViewHolder = new GoodsListAdapterViewHolder();
            ViewUtils.inject(goodsListAdapterViewHolder, view);
            view.setTag(goodsListAdapterViewHolder);
        } else {
            goodsListAdapterViewHolder = (GoodsListAdapterViewHolder) view.getTag();
        }

        setItemTag(i, goodsListAdapterViewHolder);
        bindDatas(i, goodsListAdapterViewHolder);
        return view;
    }

    //	{"Code":0,"Msg":"ok","Info":[{"ProductId":（商品编号）," Name（商品名称）"ProductUrl":null," +
//			""FactPrice（价格）"Path（图片路径）Amount（数量）speStrName（规格）,"Skuid"（规格ID）}]}
    private void bindDatas(int i, GoodsListAdapterViewHolder goodsListAdapterViewHolder) {
        JsonMap jsonMap = (JsonMap) datas.get(i);
        String pic_path = StringUtils.get_SImage(jsonMap.getStringNoNull("picPath"));
        BitmapHelper.loadImage(context, pic_path, goodsListAdapterViewHolder.goods_logo, BitmapHelper.LoadImgOption.Square);
        goodsListAdapterViewHolder.sales_over.setVisibility(View.GONE);
        goodsListAdapterViewHolder.select_goods_cb.setVisibility(View.GONE);
        goodsListAdapterViewHolder.goods_name_tv.setText(jsonMap.getStringNoNull("salesName"));
        goodsListAdapterViewHolder.goods_price_tv.setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("salePrice")));
        goodsListAdapterViewHolder.goods_discribe_tv.setText(jsonMap.getStringNoNull("speStrName"));
        goodsListAdapterViewHolder.num_tv.setText(jsonMap.getString("returnCount"));
    }

    private void setItemTag(int i, GoodsListAdapterViewHolder goodsListAdapterViewHolder) {
        //goodsListAdapterViewHolder.goods_logo.setTag(i);
        goodsListAdapterViewHolder.goods_name_tv.setTag(i);
        goodsListAdapterViewHolder.goods_price_tv.setTag(i);
        goodsListAdapterViewHolder.goods_discribe_tv.setTag(i);
        goodsListAdapterViewHolder.num_sub_tv.setTag(i);
        goodsListAdapterViewHolder.num_add_tv.setTag(i);
        goodsListAdapterViewHolder.num_tv.setTag(i);
    }

    private class GoodsListAdapterViewHolder {
        /**
         * 已售罄
         */
        @ViewInject(R.id.sales_over)
        TextView sales_over;

        /**
         * 是否选中
         */
        @ViewInject(R.id.select_goods_cb)
        ImageView select_goods_cb;
        /**
         * 商品图标
         */
        @ViewInject(R.id.goods_logo)
        ImageView goods_logo;
        @ViewInject(R.id.product_name_tv)
        TextView goods_name_tv;

        @ViewInject(R.id.goods_price_tv)
        TextView goods_price_tv;
        @ViewInject(R.id.productBrand_tv)
        TextView goods_discribe_tv;
        @ViewInject(R.id.num_sub_tv)
        TextView num_sub_tv;

        @OnClick(R.id.num_sub_tv)
        public void num_sub_tv_click(View view) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(Constant.ShopCartItemCompontType.SUB_NUM, (Integer) view.getTag());
            }
        }

        @ViewInject(R.id.num_tv)
        TextView num_tv;

        @OnClick(R.id.num_tv)
        public void num_tv_click(View view) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(Constant.ShopCartItemCompontType.NUM_EDIT, (Integer) view.getTag());
            }
        }

        @ViewInject(R.id.num_add_tv)
        TextView num_add_tv;

        @OnClick(R.id.num_add_tv)
        public void num_add_tv_click(View view) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(Constant.ShopCartItemCompontType.ADD_NUM, (Integer) view.getTag());
            }
        }
    }

    private ItemCompountClickListener itemCompountClickListener;

    /**
     * 设置控件的点击事件
     *
     * @param itemCompountClickListener
     */
    public void setItemCompountClickListener(ItemCompountClickListener itemCompountClickListener) {
        this.itemCompountClickListener = itemCompountClickListener;
    }

    public interface ItemCompountClickListener {
        /**
         * 点击事件的回调接口
         *
         * @param type 类型
         */
        void itemCompountClick(Constant.ShopCartItemCompontType type, int childIndex);
    }
}
