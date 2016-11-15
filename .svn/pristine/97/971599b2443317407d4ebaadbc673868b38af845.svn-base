package com.yishanxiu.yishang.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.utils.Utils;


/**
 * Created by FangDongzhang on 2016/9/7.
 */
public class MyRecyclerViewAdapter extends BGARecyclerViewAdapter<ProductInfoModel> {


    public MyRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.recommend_pro_item);
    }


    @Override
    public int getItemCount() {
        return super.getItemCount()+1;
    }

    @Override
    public ProductInfoModel getItem(int position) {
        if(position>=getData().size()){
           return new ProductInfoModel();
        }
        return super.getItem(position);
    }

    @Override
    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, ProductInfoModel productInfoModel) {

        ImageView relation_goods_iv = viewHolderHelper.getImageView(R.id.relation_goods_iv);
        ImageView self_product_iv = viewHolderHelper.getImageView(R.id.self_product_iv);
        TextView product_name_tv = viewHolderHelper.getTextView(R.id.product_name_tv);
        TextView  brand_name_tv = viewHolderHelper.getTextView(R.id.brand_name_tv);
        TextView product_price_tv = viewHolderHelper.getTextView(R.id.product_price_tv);
        TextView product_price_discount_tv = viewHolderHelper.getTextView(R.id.product_price_discount_tv);
        int totleNum=getData().size();
        if (position < totleNum) {
            String picPath = productInfoModel.getPath();
            BitmapHelper.loadImage(mContext, picPath, relation_goods_iv, BitmapHelper.LoadImgOption.SquareMiddle, true);
            product_name_tv.setText(productInfoModel.getProductName());

            int isSelfProduct = productInfoModel.getLinkFrom();
            if (isSelfProduct>0) {
                self_product_iv.setVisibility(View.VISIBLE);
            } else {
                self_product_iv.setVisibility(View.GONE);
            }

            double marketPrice = productInfoModel.getCostPrice();
            int isDiscount = productInfoModel.getIsDiscount();

            if (isDiscount>0) {
                double discountPrice = productInfoModel.getDiscountPrice();
                product_price_discount_tv.setText(StringUtils.getFormatMoneyWithSign(discountPrice));
                product_price_tv.setText(StringUtils.getFormatMoneyWithSign(marketPrice));
                product_price_tv.setVisibility(View.VISIBLE);
                Utils.addDeleteLine(product_price_tv);
                product_price_tv.setSelected(true);
            } else {
                product_price_tv.setVisibility(View.GONE);
                product_price_discount_tv.setText(StringUtils.getFormatMoneyWithSign(marketPrice));
            }
            brand_name_tv.setText(productInfoModel.getBrandName());
        } else {
            brand_name_tv.setText("");
            product_name_tv.setText("");
            product_price_tv.setText("");
            product_price_discount_tv.setText("");
            relation_goods_iv.setImageResource(R.drawable.review_more);
        }

    }


}
