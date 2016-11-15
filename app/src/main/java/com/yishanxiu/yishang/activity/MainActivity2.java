package com.yishanxiu.yishang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.IndicatorViewPager.IndicatorFragmentPagerAdapter;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.model.ApkInfo;
import com.yishanxiu.yishang.fragment.FindFragment2;
import com.yishanxiu.yishang.fragment.ShopCartFragment;
import com.yishanxiu.yishang.fragment.ShopMallFragment2;
import com.yishanxiu.yishang.fragment.UserCenterFragment2;
import com.yishanxiu.yishang.receiver.CheckVersionReceiver;
import com.yishanxiu.yishang.utils.ExtraKeys;

/**
 * @author FangDongzhang
 *         <p>
 *         2016/8/4
 */
public class MainActivity2 extends BaseActivity {
    private IndicatorViewPager indicatorViewPager;
    private FixedIndicatorView indicator;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_tabmain);
        SViewPager viewPager = (SViewPager) findViewById(R.id.tabmain_viewPager);
        indicator = (FixedIndicatorView) findViewById(R.id.tabmain_indicator);
        indicator.setOnTransitionListener(
                new OnTransitionTextListener().setColorId(mContext, R.color.black_deep, R.color.darkGray));
        // 这里可以添加一个view，作为centerView，会位于Indicator的tab的中间
        // centerView = getLayoutInflater().inflate(R.layout.tab_main_center,
        // indicator, false);
        // indicator.setCenterView(centerView);
        // centerView.setOnClickListener(onClickListener);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        // 禁止viewpager的滑动事件
        viewPager.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);

        Intent intent = getIntent();
        if (intent != null) {
            String apkUrl = intent.getStringExtra(ExtraKeys.Key_Msg1);
            if (!TextUtils.isEmpty(apkUrl)) {
                Intent newIntent = new Intent(CheckVersionReceiver.Essential_Download_APK_Action);
                ApkInfo apkInfo = new ApkInfo();
                apkInfo.setShowNotification(true);
                apkInfo.setAutoInstall(true);
                apkInfo.setVersionFilePath(apkUrl);
                sendBroadcast(newIntent);
            }
        }
    }

    public int firstPageIndex = -1;// 主页选项
    public int secondPageIndex = -1;// 二级嵌套页

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        firstPageIndex = intent.getIntExtra(ExtraKeys.Key_Msg1, 0);
        secondPageIndex = intent.getIntExtra(ExtraKeys.Key_Msg2, 0);
        indicatorViewPager.setCurrentItem(firstPageIndex, true);
    }

    public void setCurrentItem(int firstPageIndex, int secondPageIndex) {

        this.firstPageIndex = firstPageIndex;
        this.secondPageIndex = secondPageIndex;
        indicatorViewPager.setCurrentItem(firstPageIndex, true);
    }

    // private View.OnClickListener onClickListener = new View.OnClickListener()
    // {
    // @Override
    // public void onClick(View v) {
    // if (v == centerView) {
    // // 还可以移除哦
    // // indicator.removeCenterView();
    // Toast.makeText(getApplicationContext(), "点击了CenterView",
    // Toast.LENGTH_SHORT).show();
    // }
    // }
    // };

    UserCenterFragment2 userCenterFragment;

    private class MyAdapter extends IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"发现", "商店", "购物袋", "我的"};
        private int[] tabIcons = {R.drawable.tab_find_bg, R.drawable.tab_shop_mall_bg, R.drawable.tab_community_bg,
                R.drawable.tab_user_center_bg};
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            // FirstLayerFragment mainFragment = new FirstLayerFragment();
            // Bundle bundle = new Bundle();
            // bundle.putString(FirstLayerFragment.INTENT_STRING_TABNAME,
            // tabNames[position]);
            // bundle.putInt(FirstLayerFragment.INTENT_INT_INDEX, position);
            // mainFragment.setArguments(bundle);
            // return mainFragment;
            if (position == 0) {
                Bundle bundle = new Bundle();
                FindFragment2 findFragment = new FindFragment2();
                bundle.putInt("position", position);
                findFragment.setArguments(bundle);
                return findFragment;
            } else if (position == 1) {
                ShopMallFragment2 shopMallFragment = new ShopMallFragment2();
                return shopMallFragment;
            } else if (position == 2) {
                Bundle bundle = new Bundle();
                ShopCartFragment shoppingBrandFragment = new ShopCartFragment();
                bundle.putBoolean("back_status", false);
                shoppingBrandFragment.setArguments(bundle);
                return shoppingBrandFragment;
            } else {
                userCenterFragment = new UserCenterFragment2();
                return userCenterFragment;
            }
        }
    }

    @Override
    public void onBackPressed() {
        int currentItem = indicatorViewPager.getCurrentItem();
        if (currentItem == 3 && userCenterFragment != null) {
            if (userCenterFragment.backKeyClick()) {

            } else {
                indicatorViewPager.setCurrentItem(0, true);
            }

        } else {
            if (currentItem != 0){
                indicatorViewPager.setCurrentItem(0, true);
            }else{
                MainActivity2.this.finish();
            }
        }

    }

}