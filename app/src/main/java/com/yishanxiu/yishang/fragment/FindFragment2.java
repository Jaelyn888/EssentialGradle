package com.yishanxiu.yishang.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.TextWidthColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.ArticalSearchActivity;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.model.BaseResponse;
import com.yishanxiu.yishang.model.article.ArticleGroupModel;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.yishanxiu.yishang.utils.ModleUtils;

/**
 * 发现
 *
 * @author FangDongzhang 2016/7/22
 */
public class FindFragment2 extends LazyFragment {
	private IndicatorViewPager indicatorViewPager;
	private ScrollIndicatorView scrollIndicatorView;
	private ViewPager viewPager;
	private List<ArticleGroupModel> articleGroupModelList=new ArrayList<>();

	private ImageView find_artical_search;


	@Override
	protected void onCreateViewLazy(Bundle savedInstanceState) {
		super.onCreateViewLazy(savedInstanceState);
		setContentView(R.layout.fragment_find2);
		View view=getContentView();
		scrollIndicatorView= (ScrollIndicatorView) view.findViewById(R.id.moretab_indicator);
		viewPager= (ViewPager) view.findViewById(R.id.moretab_viewPager);
		find_artical_search= (ImageView) view.findViewById(R.id.find_artical_search);
		find_artical_search.setOnClickListener(onClickListener);
		loadingToast.show();
		getServerData();
		viewPager.setOffscreenPageLimit(6);
	}

	View.OnClickListener onClickListener=new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(activity, ArticalSearchActivity.class);
			intent.putExtra(ExtraKeys.Key_Msg1, "");
			activity.jumpTo(intent);
		}
	};

	/**
	 * 初始化控件
	 */
	private void initView() {
		int indicatorColor = getResources().getColor(R.color.black_deep);
		scrollIndicatorView.setOnTransitionListener(
				new OnTransitionTextListener().setColorId(activity, R.color.black_deep, R.color.darkGray));
		scrollIndicatorView.setScrollBar(new TextWidthColorBar(activity, scrollIndicatorView, indicatorColor, 5));
		indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
		indicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
	}



	/**
	 * 获取服务器端数据
	 */
	public void getServerData() {
		HashMap<String, Object> params = new HashMap<>();
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.GetAction_GetMoreRelationPersion);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_GetMoreRelationPersion);
		getDataUtil.getData(queue);
	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {
		@Override
		public void onLoaded(GetDataQueue entity) {
			loadingToast.dismiss();
			if (entity.what == GetDataConfing.What_GetMoreRelationPersion) {
				TypeToken<BaseResponse<List<ArticleGroupModel>>> typeToken = new TypeToken<BaseResponse<List<ArticleGroupModel>>>(){};
				BaseResponse<List<ArticleGroupModel>> baseResponse = new ModleUtils().mapTo(entity, typeToken);
				if (ShowGetDataError.isCodeSuccess(activity, baseResponse)) {
					ArticleGroupModel articleGroupModel = new ArticleGroupModel();
					articleGroupModel.setArticleGroupId("0");
					articleGroupModel.setGroupName("全部");
					articleGroupModelList.add(0, articleGroupModel);
					articleGroupModelList.addAll(baseResponse.getInfo());
					initView();
				}
			}
		}

	};

	private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

		public MyAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);

		}

		@Override
		public int getCount() {
			return articleGroupModelList.size();
		}

		@Override
		public View getViewForTab(int position, View convertView, ViewGroup container) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.tab_top, container, false);
			}
			TextView textView = (TextView) convertView;
			ArticleGroupModel articleGroupModel = articleGroupModelList.get(position);
			textView.setText(articleGroupModel.getGroupName());
			return convertView;
		}

		@Override
		public int getItemPosition(Object object) {
			// 这是ViewPager适配器的特点,有两个值
			// POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
			// 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
			return PagerAdapter.POSITION_UNCHANGED;
		}

		@Override
		public Fragment getFragmentForPage(int position) {
			ArticalFragment mainFragment = new ArticalFragment();
			ArticleGroupModel articleGroupModel = articleGroupModelList.get(position);
			Bundle bundle = new Bundle();
			bundle.putString(Constant.ARTICAL_GROUPID, articleGroupModel.getArticleGroupId());
			mainFragment.setArguments(bundle);
			return mainFragment;
		}

	}

}