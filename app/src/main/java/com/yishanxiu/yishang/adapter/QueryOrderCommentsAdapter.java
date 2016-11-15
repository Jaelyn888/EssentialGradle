package com.yishanxiu.yishang.adapter;

import java.util.List;
import java.util.Map;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.StringUtils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;

/**
 * 查看订单评论 2016/8/22
 * 
 * @author FangDongzhang
 */
public class QueryOrderCommentsAdapter extends MyBaseAdapter {

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

	public QueryOrderCommentsAdapter(Context context) {
		super(context);
	}

	public QueryOrderCommentsAdapter(Context context, List<? extends Map<String, ?>> datas) {
		super(context, datas);
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {

		ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.item_query_order_commens, viewGroup, false);
			shopCartAdapterViewHolder = new ShopAddOrderGoodsAdapterViewHolder();
			ViewUtils.inject(shopCartAdapterViewHolder, view);
			view.setTag(shopCartAdapterViewHolder);
		} else {
			shopCartAdapterViewHolder = (ShopAddOrderGoodsAdapterViewHolder) view.getTag();
		}
		bindDatas(i, shopCartAdapterViewHolder);
		setItemTag(i, shopCartAdapterViewHolder);
		return view;
	}

	private void bindDatas(int i, ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder) {
		JsonMap<String, Object> jsonMap = (JsonMap<String, Object>) datas.get(i);
		shopCartAdapterViewHolder.i_s_a_o_tv_productname.setText(jsonMap.getStringNoNull("productName"));
		shopCartAdapterViewHolder.commentContent.setText(jsonMap.getStringNoNull("commentContent"));
		shopCartAdapterViewHolder.createTime.setText(StringUtils.getTimeFormatFull(jsonMap.getStringNoNull("createTime")));
		shopCartAdapterViewHolder.i_s_a_o_tv_productnum.setText(
				context.getString(R.string.shopping_tv_text_productnum_qz) + jsonMap.getStringNoNull("productCount"));
		shopCartAdapterViewHolder.productSpecification.setText(jsonMap.getStringNoNull("productSpecification"));
		shopCartAdapterViewHolder.goods_price_tv
				.setText(StringUtils.getFormatMoneyWithSign(jsonMap.getStringNoNull("productPrice")));
		String str = jsonMap.getStringNoNull("productPic");
		BitmapHelper.loadImage(context, str, shopCartAdapterViewHolder.i_s_pl_aiv_pic1,
				BitmapHelper.LoadImgOption.BrandLogo);

		int returnRefundStatus = jsonMap.getInt("returnRefundStatus");
		if (returnRefundStatus == 21 || returnRefundStatus == 23 || returnRefundStatus == 24 || returnRefundStatus == 41
				|| returnRefundStatus == 43 || returnRefundStatus == 44) {

			shopCartAdapterViewHolder.goods_discribe_tv.setText("退款处理中");
		} else if (returnRefundStatus == 22 || returnRefundStatus == 42) {
			shopCartAdapterViewHolder.goods_discribe_tv.setText("退款商家驳回");

		} else if (returnRefundStatus == 25 || returnRefundStatus == 45) {
			shopCartAdapterViewHolder.goods_discribe_tv.setText("退款平台驳回");
		} else if (returnRefundStatus == 26 || returnRefundStatus == 46) {
			shopCartAdapterViewHolder.goods_discribe_tv.setText("退款完成");

		} else if (returnRefundStatus == 11 || returnRefundStatus == 13 || returnRefundStatus == 14 || returnRefundStatus == 18
				|| returnRefundStatus == 31 || returnRefundStatus == 33 || returnRefundStatus == 34 || returnRefundStatus == 38) {

			shopCartAdapterViewHolder.goods_discribe_tv.setText("退货处理中");
		} else if (returnRefundStatus == 12 || returnRefundStatus == 32) {
			shopCartAdapterViewHolder.goods_discribe_tv.setText("退货商家驳回");
		} else if (returnRefundStatus == 19 || returnRefundStatus == 39) {
			shopCartAdapterViewHolder.goods_discribe_tv.setText("退货商品驳回");

		} else if (returnRefundStatus == 15 || returnRefundStatus == 35) {
			shopCartAdapterViewHolder.goods_discribe_tv.setText("退货平台驳回");
		} else if (returnRefundStatus == 16 || returnRefundStatus == 36) {
			shopCartAdapterViewHolder.goods_discribe_tv.setText("退货完成");
		} else if (returnRefundStatus == 17 || returnRefundStatus == 37) {
			shopCartAdapterViewHolder.goods_discribe_tv.setText("待买家发货");

		}
		// switch (returnRefundStatus) {
		// case 1:
		// shopCartAdapterViewHolder.goods_discribe_tv.setText("");
		// break;
		// case 2:
		// shopCartAdapterViewHolder.goods_discribe_tv.setText("不可退货/退款");
		// break;
		// case 3:
		// shopCartAdapterViewHolder.goods_discribe_tv.setText("退货审核中");
		// break;
		// case 4:
		// shopCartAdapterViewHolder.goods_discribe_tv.setText("退款审核中");
		// break;
		//
		// default:
		// shopCartAdapterViewHolder.goods_discribe_tv.setText("");
		// break;
		// }
	}

	private void setItemTag(int i, ShopAddOrderGoodsAdapterViewHolder shopCartAdapterViewHolder) {
		shopCartAdapterViewHolder.goods_discribe_tv.setTag(i);
	}

	class ShopAddOrderGoodsAdapterViewHolder {
		@ViewInject(R.id.i_s_a_o_tv_productname)
		TextView i_s_a_o_tv_productname;
		@ViewInject(R.id.i_s_a_o_tv_productnum)
		TextView i_s_a_o_tv_productnum;
		@ViewInject(R.id.productBrand_tv)
		TextView goods_discribe_tv;
		@ViewInject(R.id.productSpecification)
		TextView productSpecification;
		@ViewInject(R.id.i_s_pl_aiv_pic1)
		ImageView i_s_pl_aiv_pic1;
		@ViewInject(R.id.goods_price_tv)
		TextView goods_price_tv;
		@ViewInject(R.id.createTime)
		TextView createTime;
		@ViewInject(R.id.commentContent)
		TextView commentContent;
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
		 * @param itemPosition
		 *            第几个条目
		 * @param type
		 *            类型
		 */
		void itemCompountClick(int itemPosition, Constant.ShopCartItemCompontType type, int childIndex);
	}
}
