package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.view.ListViewNoScroll;

import net.tsz.afinal.json.JsonMap;

import java.util.List;
import java.util.Map;

/**
 * Created by Jaelyn on 2015/9/19 0019. 购物车的适配器
 * <p/>
 * Updata 2106/6/24 布局文件修改
 *
 * @author FangDongzhang
 */
public class ShopCartAdapter extends MyBaseAdapter implements ShopProductListAdapter.ItemCompountClickListener {

    public ShopCartAdapter(Context context) {
        super(context);
    }

    boolean t_edit_status = false;

    public void setEdit(boolean click) {
        this.t_edit_status = click;
    }

    public ShopCartAdapter(Context context, List<? extends Map<String, ?>> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShopCartAdapterViewHolder shopCartAdapterViewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.shopcart_item, viewGroup, false);
            shopCartAdapterViewHolder = new ShopCartAdapterViewHolder();
            ViewUtils.inject(shopCartAdapterViewHolder, view);
            view.setTag(shopCartAdapterViewHolder);
        } else {
            shopCartAdapterViewHolder = (ShopCartAdapterViewHolder) view.getTag();
        }
        setTags(i, shopCartAdapterViewHolder);
        bindDatas(i, shopCartAdapterViewHolder);
        /**
         * 点选店铺的
         *
         * @param view
         */
        // shopCartAdapterViewHolder.select_goods_cb.setOnCheckedChangeListener(new
        // OnCheckedChangeListener() {
        //
        // @Override
        // public void onCheckedChanged(CompoundButton buttonView, boolean
        // isChecked) {
        // if (itemClickListener != null) {
        // if (isChecked) {
        // itemClickListener.onItemClick((Integer) buttonView.getTag(),
        // Constant.ShopCartItemCompontType.SELECT_GOODS_BRAND, 1);
        // } else {
        // itemClickListener.onItemClick((Integer) buttonView.getTag(),
        // Constant.ShopCartItemCompontType.SELECT_GOODS_BRAND, 2);
        // }
        // }
        // }
        // });
        return view;
    }

    private void setTags(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder) {
        shopCartAdapterViewHolder.select_goods_cb.setTag(i);
        shopCartAdapterViewHolder.lv_noScrool.setTag(i);
        shopCartAdapterViewHolder.brandName_tv.setTag(i);
        shopCartAdapterViewHolder.t_edit.setTag(i);
    }

    private List<JsonMap<String, Object>> jsonMaps;
    private JsonMap<String, Object> jsonMap;
    ShopProductListAdapter goodsListAdapter;

    @SuppressWarnings("unchecked")
    private void bindDatas(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder) {
        jsonMap = (JsonMap<String, Object>) datas.get(i);
        shopCartAdapterViewHolder.select_goods_cb.setSelected(jsonMap.getBoolean("isAllSelected"));
        BitmapHelper.loadImage(context, jsonMap.getStringNoNull("logoPath"),
                shopCartAdapterViewHolder.brand_logo, BitmapHelper.LoadImgOption.BrandLogo, true);

        shopCartAdapterViewHolder.brandName_tv.setText(jsonMap.getStringNoNull("name"));

//        if (jsonMap.getStringNoNull("activityPrices").equals("")){
            shopCartAdapterViewHolder.brand_totle_price
                    .setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("orderPrice")));
//        }else{
//            shopCartAdapterViewHolder.brand_totle_price
//                    .setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("activityPrices")));
//        }


        if (jsonMap.getInt("editstatus") == 1) {
            shopCartAdapterViewHolder.t_edit.setVisibility(View.INVISIBLE);
            // shopCartAdapterViewHolder.t_edit.setText(context.getResources().getString(R.string.finish));
        } else if (jsonMap.getInt("editstatus") == 2) {
            // shopCartAdapterViewHolder.t_edit.setText(context.getResources().getString(R.string.edit));
            shopCartAdapterViewHolder.t_edit.setVisibility(View.VISIBLE);

        }

        shopCartAdapterViewHolder.t_edit.setText(t_edit_status ? R.string.finish : R.string.edit);

        bindGoodsList(i, shopCartAdapterViewHolder, jsonMap);

    }

    private void bindGoodsList(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder,
                               JsonMap<String, Object> jsonMap) {
        goodsListAdapter = new ShopProductListAdapter(context);
        goodsListAdapter.setParentPosition(i);
        goodsListAdapter.setItemCompountClickListener(this);
        jsonMaps = jsonMap.getList_JsonMap("skuShopCartVo");
        goodsListAdapter.setDatas(jsonMaps);
        shopCartAdapterViewHolder.lv_noScrool.setAdapter(goodsListAdapter);
        int sNum = 0;
        for (int j = 0; j < jsonMaps.size(); j++) {
            if (jsonMaps.get(j).getInt("isSelected") == 1) {
                sNum++;
            }
        }

        if (sNum == jsonMaps.size()) {
            shopCartAdapterViewHolder.select_goods_cb.setSelected(true);
        } else {
            shopCartAdapterViewHolder.select_goods_cb.setSelected(false);

        }

    }

    @Override
    public void itemCompountClick(int itemPosition, Constant.ShopCartItemCompontType type, int childIndex) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(itemPosition, type, childIndex);
        }
    }

    private class ShopCartAdapterViewHolder {

        @ViewInject(R.id.select_goods_cb)
        CheckBox select_goods_cb;

        @OnClick(R.id.select_goods_cb)
        public void select_goods_cb(View view) {
            if (itemClickListener != null) {
                if (select_goods_cb.isSelected() == false) {
                    select_goods_cb.setSelected(true);
                    itemClickListener.onItemClick((Integer) view.getTag(),
                            Constant.ShopCartItemCompontType.SELECT_GOODS_BRAND, 1);
                } else if (select_goods_cb.isSelected() == true) {
                    select_goods_cb.setSelected(false);
                    itemClickListener.onItemClick((Integer) view.getTag(),
                            Constant.ShopCartItemCompontType.SELECT_GOODS_BRAND, 2);
                }
            }
        }

        @ViewInject(R.id.brand_logo)
        ImageView brand_logo;

        @ViewInject(R.id.brandName_tv)
        TextView brandName_tv;

        @OnClick(R.id.brandName_tv)
        public void brandName_tv_click(View view) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick((Integer) lv_noScrool.getTag(),
                        Constant.ShopCartItemCompontType.CLICK_BRAND_NAME, -1);
            }
        }

        @ViewInject(R.id.lv_noScrool)
        ListViewNoScroll lv_noScrool;

        @OnItemClick(R.id.lv_noScrool)
        public void lv_noScrool_item_click(AdapterView<?> adapterView, View view, int i, long l) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick((Integer) lv_noScrool.getTag(),
                        Constant.ShopCartItemCompontType.CLICK_GOODS_ITEM, i);
            }
        }

        @ViewInject(R.id.brand_totle_price)
        TextView brand_totle_price;

        /**
         * @author FangDongzhang 编辑
         */
        @ViewInject(R.id.t_edit)
        TextView t_edit;


        @OnClick(R.id.t_edit)
        public void tEditClick(View v) {

            if (t_edit_status == false) {
                t_edit_status = true;
                itemClickListener.onItemClick((Integer) t_edit.getTag(),
                        Constant.ShopCartItemCompontType.CLICK_EDIT_ITEM, 0);

            } else {
                t_edit_status = false;
                itemClickListener.onItemClick((Integer) t_edit.getTag(),
                        Constant.ShopCartItemCompontType.CLICK_EDIT_ITEM, 1);
            }
            t_edit.setText(t_edit_status ? R.string.finish : R.string.edit);
        }

    }

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        /**
         * @param position         根位置
         * @param shopCartitemType 类别
         * @param index            点击具体的商品位置
         */
        void onItemClick(int position, Constant.ShopCartItemCompontType shopCartitemType, int index);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
