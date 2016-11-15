package com.yishanxiu.yishang.activity;

import android.view.View;
import android.widget.TextView;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import android.os.Bundle;
import net.tsz.afinal.annotation.view.ViewInject;

public class MyMsgActivity extends BaseUIActivity {

	@ViewInject(id=R.id.sys_msg_tv,click = "sysMsgTvClick")
	private TextView sys_msg_tv;
	/**
	 * @author FangDongzhang 2016/3/25 我的消息
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_msg2);
		setCenter_title(R.string.my_msg);
	}


	/**
	 * 系统消息
	 * @param view
	 */
	public void sysMsgTvClick(View view){
		jumpTo(SystemMsgActivity.class);
	}
	@Override
	protected void onStart() {
		super.onStart();
		// 注册一个监听连接状态的listener
		//EMClient.getInstance().addConnectionListener(new MyConnectionListener());

	}

	// 实现ConnectionListener接口
//	private class MyConnectionListener implements EMConnectionListener {
//		@Override
//		public void onConnected() {
//		}
//
//		@Override
//		public void onDisconnected(final int error) {
//			runOnUiThread(new Runnable() {
//
//				@Override
//				public void run() {
//					if (error == EMError.USER_REMOVED) {
//						// 显示帐号已经被移除
//					} else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//						// 显示帐号在其他设备登录
//					} else {
//						if (NetUtils.hasNetwork(MyMsgActivity.this)) {
//							// 连接不到聊天服务器
//						} else {
//							// 当前网络不可用，请检查网络设置
//						}
//					}
//				}
//			});
//		}
//	}

}
