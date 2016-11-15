package com.yishanxiu.yishang.activity.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yishanxiu.yishang.R;

/**
 * Created by Administrator on 2015/8/17 0017.
 */
public class BaseUIActivity extends BaseActivity {
    /**
     * 头部展示的标题
     */
    protected TextView tv_title;
    /**
     * 返回按钮和功能按钮
     */
    protected ImageView left_menu_iv, btn_back, btn_menu;
    protected TextView menu_tv;
    protected RelativeLayout titleLayout;
    protected LinearLayout menu_layout;
    protected RelativeLayout topLayout;
    /**
     * 当前activity的默认布局 只有头部信息 内容信息是没有的
     */
    protected View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.all_activity_view, null);
    }

    /**
     * 设置标题
     *
     * @param tv_title
     */
    public void setCenter_title(String tv_title) {
        this.tv_title.setText(tv_title);
    }


    public void setCenter_title(String tv_title, boolean isShowBack) {
        if (isShowBack) {
            btn_back.setVisibility(View.VISIBLE);
        } else {
            btn_back.setVisibility(View.GONE);
        }
        this.tv_title.setText(tv_title);
    }

    /**
     * 设置标题
     *
     * @param center_title_id
     */
    public void setCenter_title(int center_title_id) {
        this.tv_title.setText(center_title_id);
    }

    /**
     * 设置标题,是否启用返回键功能
     *
     * @param center_title_id
     */
    public void setCenter_title(int center_title_id, boolean isback) {
        this.tv_title.setText(center_title_id);
        if (isback) {
            setBtn_backListener(clickListener);
        } else {
            btn_back.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置返回键的图标
     *
     * @param resId
     */
    public void setBtn_back(int resId) {
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setImageResource(resId);
        //titleLayout.setBackgroundResource();
    }

    /**
     * 设置返回键的点击事件
     *
     * @param onClickListener
     */
    public void setBtn_backListener(View.OnClickListener onClickListener) {
        btn_back.setVisibility(View.VISIBLE);
        this.btn_back.setOnClickListener(onClickListener);
    }

    /**
     * 设置返回键的样式及点击事件
     *
     * @param resId
     * @param onClickListener
     */
    public void setBtn_back(int resId, View.OnClickListener onClickListener) {
        setBtn_back(resId);
        setBtn_backListener(onClickListener);
    }

    /**
     * 显示左侧的menu按钮
     *
     * @param onClickListener
     */
    public void setShowLeftMenu(View.OnClickListener onClickListener) {
        left_menu_iv.setVisibility(View.VISIBLE);
        left_menu_iv.setOnClickListener(onClickListener);
    }

    /**
     * 设置menu的样式及点击事件
     *
     * @param resMenuId
     * @param onClickListener
     */
    public void setBtn_menu(int resMenuId, View.OnClickListener onClickListener) {
        btn_menu.setVisibility(View.VISIBLE);
        btn_menu.setImageResource(resMenuId);
        btn_menu.setOnClickListener(onClickListener);
    }

    /**
     * 设置menu的样式及点击事件
     *
     */
    public void setBtn_menu(int resMenuId1, View.OnClickListener onClickListener1,int resMenuId2, View.OnClickListener onClickListener2) {
        menu_layout.removeAllViews();
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView imageView1=new ImageView(mContext);
        imageView1.setOnClickListener(onClickListener1);
        imageView1.setImageResource(resMenuId1);
        imageView1.setPadding(getResources().getDimensionPixelSize(R.dimen.comon_half_margin),0,getResources().getDimensionPixelSize(R.dimen.comon_half_margin),0);
        menu_layout.addView(imageView1,params);
        ImageView imageView2=new ImageView(mContext);
        imageView2.setOnClickListener(onClickListener2);
        imageView2.setImageResource(resMenuId2);
        imageView2.setPadding(getResources().getDimensionPixelSize(R.dimen.comon_half_margin),0,getResources().getDimensionPixelSize(R.dimen.common_margin),0);
        menu_layout.addView(imageView2,params);
    }

    public void setBtn_menuText(int res, View.OnClickListener onClickListener) {
        menu_tv.setVisibility(View.VISIBLE);
        btn_menu.setVisibility(View.GONE);
        menu_tv.setText(res);
        menu_tv.setOnClickListener(onClickListener);

    }


    /**
     * 设置titlebar的显示
     *
     * @param isShowLeft         左侧是否显示
     * @param leftResId          左侧的图标的id    -1:标示使用默认的back键
     * @param leftClickListener  左侧的点击事件   null：使用默认的 结束当前的activity
     * @param centerResId        标题的显示
     * @param rightResId         右侧menu的图标
     * @param rightClickListener 右侧的menu点击事件
     */
    public void initActivity(boolean isShowLeft, int leftResId, View.OnClickListener leftClickListener, int centerResId, int rightResId, View.OnClickListener rightClickListener) {
        if (isShowLeft) {
            if (leftResId != -1) {
                btn_back.setImageResource(leftResId);
            } else {
                btn_back.setImageResource(R.drawable.em_mm_title_back);
            }
            if (leftClickListener != null) {
                btn_back.setOnClickListener(leftClickListener);
            } else {
                btn_back.setOnClickListener(clickListener);
            }
        } else {
            btn_back.setVisibility(View.INVISIBLE);
        }
        tv_title.setText(centerResId);
        if (rightResId == -1) {
            btn_menu.setVisibility(View.INVISIBLE);
        } else {
            btn_menu.setImageResource(rightResId);
            btn_menu.setOnClickListener(rightClickListener);
        }
    }


    // 设定后退按钮事件
    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            BaseUIActivity.this.finish();
            hideSoftKeyboard();
        }
    };

    /**
     * 重新父类的设置主view的方法
     */
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        setActivityView(view, null);
    }


    /**
     * 重新父类的设置主view的方法 将新的view添加到center中
     */
    @Override
    public void setContentView(View view) {
        setActivityView(view, null);
    }

    /**
     * 重新父类的设置主view的方法
     */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        setActivityView(view, params);
    }

    private void setActivityView(View v, ViewGroup.LayoutParams params) {

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.center);

        if (params == null) {
            layout.addView(v, new ViewGroup.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        } else {
            layout.addView(v, params);
        }
        initTitleBarCompont();
        super.setContentView(view);
    }

    private void initTitleBarCompont() {
        topLayout = (RelativeLayout) view.findViewById(R.id.title_layout);
        menu_layout = (LinearLayout) view.findViewById(R.id.menu_layout);
        titleLayout = (RelativeLayout) view.findViewById(R.id.title_layout);
        btn_back = (ImageView) view.findViewById(R.id.all_layout_back);
        left_menu_iv = (ImageView) view.findViewById(R.id.left_menu_iv);
        btn_menu = (ImageView) view.findViewById(R.id.title_menu_iv);
        menu_tv = (TextView) view.findViewById(R.id.menu_tv);
        tv_title = (TextView) view.findViewById(R.id.head_title);
        btn_back.setOnClickListener(clickListener);
    }
    
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
}