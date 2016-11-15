package com.yishanxiu.yishang.view.sidebar;

import com.yishanxiu.yishang.model.shopmall.BrandSortModel;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<BrandSortModel> {
//	前面两个if判断主要是将不是以汉字开头的数据放在后面

	public int compare(BrandSortModel o1, BrandSortModel o2) {
		//这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}

	}

}