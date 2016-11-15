package com.yishanxiu.yishang.activity;


import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.fragment.CollectionArticalFragment;
import com.yishanxiu.yishang.fragment.CollectionProductsFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * @author FangDongzhang 我的收藏2016/7/21
 */
public class MyCollectActivity2 extends BaseUIActivity {

	/**
	 * @author FangDongzhang 我的订单 2016/7/8
	 */
	private IndicatorViewPager indicatorViewPager;
	@ViewInject(id=R.id.moretab_indicator)
	private ScrollIndicatorView fixed_indicator;
	@ViewInject(id=R.id.moretab_viewPager)
	private ViewPager viewPager;
	private String[] strings = { "发现", "商品" };


	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indicator_viewpager_line_layout);
		setCenter_title(R.string.my_collect);
		initView();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		int indicatorColor=getResources().getColor(R.color.black_deep);
		fixed_indicator.setOnTransitionListener(
				new OnTransitionTextListener().setColorId(mContext,R.color.black_deep, R.color.darkGray));
		fixed_indicator.setScrollBar(new TextWidthColorBar(mContext, fixed_indicator, indicatorColor, 5));
		indicatorViewPager = new IndicatorViewPager(fixed_indicator, viewPager);
		indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
	}


	private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

		public MyAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public int getCount() {
			return strings.length;
		}

		@Override
		public View getViewForTab(int position, View convertView, ViewGroup container) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.tab_top, container, false);
			}
			TextView textView = (TextView) convertView;
			textView.setText(strings[position]);
			return convertView;
		}

		/**
		 * 获取每一个界面
		 */
		@Override
		public int getItemPosition(Object object) {
			// 这是ViewPager适配器的特点,有两个值
			// POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
			// 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
			return PagerAdapter.POSITION_UNCHANGED;
		}

		@Override
		public Fragment getFragmentForPage(int position) {
			if (position == 0) {
				CollectionArticalFragment articalFragment = new CollectionArticalFragment();
				return articalFragment;
			} else {
				CollectionProductsFragment collectionProductsFragment = new CollectionProductsFragment();
				return collectionProductsFragment;
			}
		}

	}
}
