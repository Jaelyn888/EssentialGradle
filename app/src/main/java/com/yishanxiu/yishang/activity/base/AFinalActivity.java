/**
 * Copyright (c) 2012-2013, Michael Yang 杨福海 (www.yangfuhai.com).
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Aym 我修改了错误的输出方式  将错误的输出方式转换为AymUtilLog的展示方式 以防止某些人查看运行日志记录
 */
package com.yishanxiu.yishang.activity.base;

import java.lang.reflect.Field;

import android.support.v7.app.AppCompatActivity;
import cn.jpush.android.api.JPushInterface;
import com.umeng.analytics.MobclickAgent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import net.tsz.afinal.annotation.view.EventListener;
import net.tsz.afinal.annotation.view.Select;
import net.tsz.afinal.annotation.view.ViewInject;

public abstract class AFinalActivity extends AppCompatActivity {
	/**
	 * log标签
	 */
	public final String TAG = this.getClass().getSimpleName();

	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		// //透明状态栏
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// //透明导航栏
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		initView();
	}

	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
	}

	public void setContentView(View view) {
		super.setContentView(view);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}


	private void initView() {
		Field[] fields = getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					field.setAccessible(true);

					if (field.get(this) != null)
						continue;

					ViewInject viewInject = field.getAnnotation(ViewInject.class);
					if (viewInject != null) {

						int viewId = viewInject.id();
						field.set(this, findViewById(viewId));

						setListener(field, viewInject.click(), Method.Click);
						setListener(field, viewInject.longClick(), Method.LongClick);
						setListener(field, viewInject.itemClick(), Method.ItemClick);
						setListener(field, viewInject.itemLongClick(), Method.itemLongClick);

						Select select = viewInject.select();
						if (!TextUtils.isEmpty(select.selected())) {
							setViewSelectListener(field, select.selected(), select.noSelected());
						}

					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		}
	}

	private void setViewSelectListener(Field field, String select, String noSelect) throws Exception {
		Object obj = field.get(this);
		if (obj instanceof View) {
			((AbsListView) obj).setOnItemSelectedListener(new EventListener(this).select(select).noSelect(noSelect));
		}
	}

	private void setListener(Field field, String methodName, Method method) throws Exception {
		if (methodName == null || methodName.trim().length() == 0)
			return;

		Object obj = field.get(this);

		switch (method) {
		case Click:
			if (obj instanceof View) {
				((View) obj).setOnClickListener(new EventListener(this).click(methodName));
			}
			break;
		case ItemClick:
			if (obj instanceof AbsListView) {
				((AbsListView) obj).setOnItemClickListener(new EventListener(this).itemClick(methodName));
			}
			break;
		case LongClick:
			if (obj instanceof View) {
				((View) obj).setOnLongClickListener(new EventListener(this).longClick(methodName));
			}
			break;
		case itemLongClick:
			if (obj instanceof AbsListView) {
				((AbsListView) obj).setOnItemLongClickListener(new EventListener(this).itemLongClick(methodName));
			}
			break;
		default:
			break;
		}
	}

	public enum Method {
		Click, LongClick, ItemClick, itemLongClick
	}

}
