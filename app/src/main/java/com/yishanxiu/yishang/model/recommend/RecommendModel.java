package com.yishanxiu.yishang.model.recommend;

import com.yishanxiu.yishang.model.BaseModel;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;

import java.util.List;

/**
 * Created by Jaelyn on 2016/9/22.
 * 推荐
 */
public class RecommendModel extends BaseModel {
	private String recommendId;
	private String recommendName;
	private int recommendType;
	private String recommendIntroduction;
	private String recommendDescription;
	private String recommendTitle;
	private String recommendNumber;
	private int recommendSort;
	private int status;
	private String statusTime;
	private String createTime;
	private String mainPicPath;
	private String coverPic;
	private int advertisingPositionFlag;
	private int advertisingPositionSort;

	private List<ProductInfoModel> relatedProductList;

	public List<ProductInfoModel> getRelatedProductList() {
		return relatedProductList;
	}

	public void setRelatedProductList(List<ProductInfoModel> relatedProductList) {
		this.relatedProductList = relatedProductList;
	}

	public String getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public int getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(int recommendType) {
		this.recommendType = recommendType;
	}

	public String getRecommendIntroduction() {
		return recommendIntroduction;
	}

	public void setRecommendIntroduction(String recommendIntroduction) {
		this.recommendIntroduction = recommendIntroduction;
	}

	public String getRecommendDescription() {
		return recommendDescription;
	}

	public void setRecommendDescription(String recommendDescription) {
		this.recommendDescription = recommendDescription;
	}

	public String getRecommendTitle() {
		return recommendTitle;
	}

	public void setRecommendTitle(String recommendTitle) {
		this.recommendTitle = recommendTitle;
	}

	public String getRecommendNumber() {
		return recommendNumber;
	}

	public void setRecommendNumber(String recommendNumber) {
		this.recommendNumber = recommendNumber;
	}

	public int getRecommendSort() {
		return recommendSort;
	}

	public void setRecommendSort(int recommendSort) {
		this.recommendSort = recommendSort;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMainPicPath() {
		return mainPicPath;
	}

	public void setMainPicPath(String mainPicPath) {
		this.mainPicPath = mainPicPath;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public int getAdvertisingPositionFlag() {
		return advertisingPositionFlag;
	}

	public void setAdvertisingPositionFlag(int advertisingPositionFlag) {
		this.advertisingPositionFlag = advertisingPositionFlag;
	}

	public int getAdvertisingPositionSort() {
		return advertisingPositionSort;
	}

	public void setAdvertisingPositionSort(int advertisingPositionSort) {
		this.advertisingPositionSort = advertisingPositionSort;
	}
}
