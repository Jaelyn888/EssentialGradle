package com.yishanxiu.yishang.model.recommend;

import com.yishanxiu.yishang.model.BaseModel;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;

import java.util.List;

/**
 * Created by Jaelyn on 2016/9/23.
 * 推荐详情页返回数据
 */
public class RecommendDetailResponse extends BaseModel{
	private List<RecommendProModel> relatedProduct;
	private RecommendModel recommendDetail;

	public List<RecommendProModel> getRelatedProduct() {
		return relatedProduct;
	}

	public void setRelatedProduct(List<RecommendProModel> relatedProduct) {
		this.relatedProduct = relatedProduct;
	}

	public RecommendModel getRecommendDetail() {
		return recommendDetail;
	}

	public void setRecommendDetail(RecommendModel recommendDetail) {
		this.recommendDetail = recommendDetail;
	}
}
