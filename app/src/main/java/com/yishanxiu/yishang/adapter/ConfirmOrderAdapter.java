package com.yishanxiu.yishang.adapter;

import java.util.List;
import java.util.Map;

import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.view.ListViewNoScroll;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;
import com.yishanxiu.yishang.R;

/**
 * 2016/7/1 确认订单的适配器
 *
 * @author FangDongzhang
 */
public class ConfirmOrderAdapter extends MyBaseAdapter implements ShopProductListAdapter.ItemCompountClickListener {

	public ConfirmOrderAdapter(Context context) {
		super(context);
	}

	public ConfirmOrderAdapter(Context context, List<? extends Map<String, ?>> datas) {
		super(context, datas);
	}


	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ShopCartAdapterViewHolder shopCartAdapterViewHolder;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.confirm_order_item, null);
			shopCartAdapterViewHolder = new ShopCartAdapterViewHolder();
			ViewUtils.inject(shopCartAdapterViewHolder, view);
			view.setTag(shopCartAdapterViewHolder);
		} else {
			shopCartAdapterViewHolder = (ShopCartAdapterViewHolder) view.getTag();
		}
		setTags(i, shopCartAdapterViewHolder);
		bindDatas(i, shopCartAdapterViewHolder);
		return view;
	}

	private void setTags(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder) {
		shopCartAdapterViewHolder.lv_noScrool.setTag(i);
		shopCartAdapterViewHolder.brandName_tv.setTag(i);
	}


	//{"msg":"OK","code":"1","info":{"totalFreight":"","productTotalPrice":25.000,
	// "userReceiptAddress":{"addressId":1,"userId":1,"provinceId":116,"consignee":"Haohan","phone":"18516696739","cityId":1149,"districtId":15571,"provincesRegions":"上海上海市徐汇区","detail":"撒范德萨的餐桌上的the is the only one","consigneeAddress":"上海上海市徐汇区撒范德萨的餐桌上的the is the only one","isdefault":0},
	// "productTotalActivityPrice":25.000,"shopCartVoList":[{"shopId":1,"name":"阿迪达斯测试01",
	// "logoPath":"http://pic42.nipic.com/20140614/18876109_112038762782_2.jpg","shoptypeName":"自营", "shopFreight":0.0,"shopSelectedProductPrice":25.000,

	// "isAllSelected":null,"shopProductCount":1,"shopSelectedTotalActivityPrice":25.000}]}}
	private void bindDatas(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder) {
		JsonMap<String, Object> jsonMap = (JsonMap<String, Object>) datas.get(i);
		BitmapHelper.loadImage(context, jsonMap.getStringNoNull("logoPath"), shopCartAdapterViewHolder.brand_logo, BitmapHelper.LoadImgOption.BrandLogo);
		shopCartAdapterViewHolder.brandName_tv.setText(jsonMap.getStringNoNull("name"));
		double shopSelectedTotalActivityPrice=jsonMap.getDouble("shopSelectedTotalActivityPrice",0d);
		double shopFreight=jsonMap.getDouble("shopFreight",0d);
		shopCartAdapterViewHolder.shopFreight_tv.setText(StringUtils.getFormatMoneyWithSign(shopFreight));

		String tmpStr=context.getString(R.string.totle_pro_count);
		tmpStr=String.format(tmpStr,jsonMap.getStringNoNull("shopProductCount"));
		shopCartAdapterViewHolder.totleProductTv.setText(tmpStr);

		String totle_money_formart = String.format(context.getString(R.string.totle_money), StringUtils.getFormatMoneyWithSign(shopSelectedTotalActivityPrice));
		shopCartAdapterViewHolder.brand_totle_price.setText(totle_money_formart);

		bindGoodsList(i, shopCartAdapterViewHolder, jsonMap);

	}

	private void bindGoodsList(int i, ShopCartAdapterViewHolder shopCartAdapterViewHolder,
	                           JsonMap<String, Object> jsonMap) {
		ShopAddOrderProductAdapter goodsListAdapter = new ShopAddOrderProductAdapter(context);
		List<JsonMap<String, Object>> jsonMaps = jsonMap.getList_JsonMap("skuShopCartVo");
		goodsListAdapter.setDatas(jsonMaps);
		shopCartAdapterViewHolder.lv_noScrool.setAdapter(goodsListAdapter);
	}

	@Override
	public void itemCompountClick(int itemPosition, Constant.ShopCartItemCompontType type, int childIndex) {
		if (itemClickListener != null) {
			itemClickListener.onItemClick(itemPosition, type, childIndex);
		}
	}

	public class ShopCartAdapterViewHolder {
		@ViewInject(R.id.brand_logo)
		public ImageView brand_logo;

		@ViewInject(R.id.brandName_tv)
		public TextView brandName_tv;

		@OnClick(R.id.brandName_tv)
		public void brandName_tv_click(View view) {
			if (itemClickListener != null) {
				itemClickListener.onItemClick((Integer) lv_noScrool.getTag(),
						Constant.ShopCartItemCompontType.CLICK_BRAND_NAME, -1);
			}
		}

		@ViewInject(R.id.lv_noScrool)
		public ListViewNoScroll lv_noScrool;

		@OnItemClick(R.id.lv_noScrool)
		public void lv_noScrool_item_click(AdapterView<?> adapterView, View view, int i, long l) {
			if (itemClickListener != null) {
				itemClickListener.onItemClick((Integer) lv_noScrool.getTag(),
						Constant.ShopCartItemCompontType.CLICK_GOODS_ITEM, i);
			}
		}

		//总价
		@ViewInject(R.id.brand_totle_price)
		public TextView brand_totle_price;

		//留言
		@ViewInject(R.id.note_ed)
		public EditText note_ed;
		//运费
		@ViewInject(R.id.shopFreight)
		public TextView shopFreight_tv;

		//总商品数量
		@ViewInject(R.id.totle_product_tv)
		public TextView totleProductTv;

	}

	private OnItemClickListener itemClickListener;

	public interface OnItemClickListener {
		/**
		 * @param position         根位置
		 * @param shopCartitemType 类别
		 * @param index            点击具体的商品位置
		 */
		void onItemClick(int position, Constant.ShopCartItemCompontType shopCartitemType, int index);

		/**
		 * 编辑
		 */
		void onEditClick(int position, boolean s);

	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.itemClickListener = listener;
	}
}
