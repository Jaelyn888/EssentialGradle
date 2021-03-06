package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.StringUtils;

import net.tsz.afinal.json.JsonMap;

import java.util.List;
import java.util.Map;

/**
 * Created by Jaelyn on 2015/9/19 0019 购物车商品列表显示
 */
public class ShopProductListAdapter extends MyBaseAdapter {
    /**
     * 父类的位置
     */
    private int parentPosition = -1;

    private int pronum;

    public int getParentPosition() {
        return parentPosition;
    }

    public void setParentPosition(int parentPosition) {
        this.parentPosition = parentPosition;
    }

    public ShopProductListAdapter(Context context) {
        super(context);
    }

    public ShopProductListAdapter(Context context, List<? extends Map<String, ?>> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GoodsListAdapterViewHolder goodsListAdapterViewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.shopcart_goods_item, viewGroup, false);
            goodsListAdapterViewHolder = new GoodsListAdapterViewHolder();
            ViewUtils.inject(goodsListAdapterViewHolder, view);
            view.setTag(goodsListAdapterViewHolder);
        } else {
            goodsListAdapterViewHolder = (GoodsListAdapterViewHolder) view.getTag();
        }
        setItemTag(i, goodsListAdapterViewHolder);
        bindDatas(i, goodsListAdapterViewHolder);
        setListener(i, goodsListAdapterViewHolder);
        return view;
    }

    @SuppressWarnings("unchecked")
    private void bindDatas(int i, GoodsListAdapterViewHolder goodsListAdapterViewHolder) {
        JsonMap<String, Object> jsonMap = (JsonMap<String, Object>) datas.get(i);
        String pic_path = jsonMap.getStringNoNull("path");
        BitmapHelper.loadImage(context, pic_path, goodsListAdapterViewHolder.goods_logo,
                BitmapHelper.LoadImgOption.BrandLogo, true);

        int productCount = jsonMap.getInt("productCount");
        if (productCount > 0) {
            int stock = jsonMap.getInt("saleStatus");
            if (stock == 1 && jsonMap.getInt("productSkuIsDeleted") != 1 && jsonMap.getInt("productSpuIsDeleted") != 1
                    && (jsonMap.getInt("productskuCount") - jsonMap.getInt("productCount") >= 0)) {
                goodsListAdapterViewHolder.select_goods_cb.setSelected(jsonMap.getBoolean("isSelected"));
                goodsListAdapterViewHolder.select_goods_cb.setEnabled(true);
                goodsListAdapterViewHolder.sales_over.setVisibility(View.GONE);
            } else {
                goodsListAdapterViewHolder.select_goods_cb.setSelected(false);
                goodsListAdapterViewHolder.select_goods_cb.setEnabled(false);
                goodsListAdapterViewHolder.sales_over.setVisibility(View.VISIBLE);
                goodsListAdapterViewHolder.sales_over.setText(R.string.sales_off);

            }
        } else {
            goodsListAdapterViewHolder.select_goods_cb.setEnabled(false);
            goodsListAdapterViewHolder.sales_over.setVisibility(View.VISIBLE);
            goodsListAdapterViewHolder.sales_over.setText(R.string.salve_over);
        }

        goodsListAdapterViewHolder.goods_name_tv.setText(jsonMap.getStringNoNull("productName"));

        if (jsonMap.getStringNoNull("activityPrices").equals("")) {
            goodsListAdapterViewHolder.goods_price_tv
                    .setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("prices")));
        } else {
            goodsListAdapterViewHolder.goods_price_tv
                    .setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("activityPrices")));
        }

        goodsListAdapterViewHolder.goods_discribe_tv.setText(jsonMap.getStringNoNull("specificationValue"));
        goodsListAdapterViewHolder.num_tv.setText(jsonMap.getStringNoNull("productCount"));
        goodsListAdapterViewHolder.productCount.setText("× " + jsonMap.getStringNoNull("productCount"));
        goodsListAdapterViewHolder.et_product_pronum.setText(jsonMap.getStringNoNull("productCount"));

        if (jsonMap.getInt("select_delete") == 1) {
            goodsListAdapterViewHolder.t_delete.setVisibility(View.VISIBLE);
            goodsListAdapterViewHolder.add_sub_llt.setVisibility(View.VISIBLE);
            goodsListAdapterViewHolder.goods_name_tv.setVisibility(View.GONE);
            goodsListAdapterViewHolder.productCount.setVisibility(View.GONE);
        } else if (jsonMap.getInt("select_delete") == 2) {
            goodsListAdapterViewHolder.t_delete.setVisibility(View.GONE);
            goodsListAdapterViewHolder.add_sub_llt.setVisibility(View.GONE);
            goodsListAdapterViewHolder.goods_name_tv.setVisibility(View.VISIBLE);
            goodsListAdapterViewHolder.productCount.setVisibility(View.VISIBLE);

        }

    }

    private void setItemTag(int i, GoodsListAdapterViewHolder goodsListAdapterViewHolder) {
        goodsListAdapterViewHolder.select_goods_cb.setTag(i);
        //goodsListAdapterViewHolder.goods_logo.setTag(i);
        goodsListAdapterViewHolder.goods_name_tv.setTag(i);
        goodsListAdapterViewHolder.goods_price_tv.setTag(i);
        goodsListAdapterViewHolder.goods_discribe_tv.setTag(i);
        goodsListAdapterViewHolder.num_sub_tv.setTag(i);
        goodsListAdapterViewHolder.num_add_tv.setTag(i);
        goodsListAdapterViewHolder.num_tv.setTag(i);
        goodsListAdapterViewHolder.t_delete.setTag(i);
        goodsListAdapterViewHolder.iv_pronum_jia.setTag(i);
        goodsListAdapterViewHolder.iv_pronum_jian.setTag(i);
        goodsListAdapterViewHolder.add_sub_llt.setTag(i);
    }

    private void setListener(int i, GoodsListAdapterViewHolder goodsListAdapterViewHolder) {
        goodsListAdapterViewHolder.et_product_pronum.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pronum = Integer.parseInt(s.toString().trim());

            }
        });
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
        CheckBox select_goods_cb;

        @OnClick(R.id.select_goods_cb)
        public void select_goods_cb_click(View view) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(parentPosition,
                        Constant.ShopCartItemCompontType.SELECT_GOODS_ITEM, (Integer) view.getTag());
            }
        }

        /**
         * 商品图标
         */
        @ViewInject(R.id.goods_logo)
        ImageView goods_logo;
        @ViewInject(R.id.product_name_tv)
        TextView goods_name_tv;

        @ViewInject(R.id.goods_price_tv)
        TextView goods_price_tv;
        @ViewInject(R.id.productCount)
        TextView productCount;
        @ViewInject(R.id.productBrand_tv)
        TextView goods_discribe_tv;
        @ViewInject(R.id.num_sub_tv)
        TextView num_sub_tv;

        @OnClick(R.id.num_sub_tv)
        public void num_sub_tv_click(View view) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(parentPosition, Constant.ShopCartItemCompontType.SUB_NUM,
                        (Integer) view.getTag());
            }
        }

        @ViewInject(R.id.num_tv)
        TextView num_tv;

        @OnClick(R.id.num_tv)
        public void num_tv_click(View view) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(parentPosition, Constant.ShopCartItemCompontType.NUM_EDIT,
                        (Integer) view.getTag());
            }
        }

        @ViewInject(R.id.num_add_tv)
        TextView num_add_tv;

        @OnClick(R.id.num_add_tv)
        public void num_add_tv_click(View view) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(parentPosition, Constant.ShopCartItemCompontType.ADD_NUM,
                        (Integer) view.getTag());
            }
        }

        @ViewInject(R.id.t_delete)
        TextView t_delete;

        @OnClick(R.id.t_delete)
        public void shopCartDelete(View v) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(parentPosition,
                        Constant.ShopCartItemCompontType.DELETE_GOODS_ITEM, (Integer) v.getTag());
            }
        }

        @ViewInject(R.id.add_sub_llt)
        LinearLayout add_sub_llt;
        @ViewInject(R.id.i_s_et_product_pronum)
        EditText et_product_pronum;

        @ViewInject(R.id.i_s_iv_pronum_jia)
        ImageView iv_pronum_jia;

        @OnClick(R.id.i_s_iv_pronum_jia)
        public void add(View v) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(parentPosition, Constant.ShopCartItemCompontType.ADD_NUM,
                        (Integer) v.getTag());
            }

        }

        @ViewInject(R.id.i_s_iv_pronum_jian)
        ImageView iv_pronum_jian;

        @OnClick(R.id.i_s_iv_pronum_jian)
        public void sub(View v) {
            if (itemCompountClickListener != null) {
                itemCompountClickListener.itemCompountClick(parentPosition, Constant.ShopCartItemCompontType.SUB_NUM,
                        (Integer) v.getTag());
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
         * @param itemPosition 第几个条目
         * @param type         类型
         */
        void itemCompountClick(int itemPosition, Constant.ShopCartItemCompontType type, int childIndex);
    }

}
