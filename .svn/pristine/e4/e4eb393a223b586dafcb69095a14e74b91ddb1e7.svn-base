package com.yishanxiu.yishang.fragment;


import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.utils.Constant;
import android.widget.Toast;

/**
 * 我的消息
 * 
 * @author FangDongzhang
 *
 */
public class MyMsgFragment extends EaseConversationListFragment {

	@Override
	protected void initView() {
		super.initView();
		//registerForContextMenu(conversationListView);
		setConversationListItemClickListener(new EaseConversationListItemClickListener() {
			@Override
			public void onListItemClicked(EMConversation conversation) {
				itentChat(conversation);
			}
		});
	}

	public void itentChat(EMConversation conversation) {
		String username = conversation.getUserName();
		if (username.equals(EMClient.getInstance().getCurrentUser()))
			Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
		else {
			// 进入聊天页面
			if (conversation.isGroup()) {
				if (conversation.getType() == EMConversationType.ChatRoom) {
					// it's group chat
					((BaseActivity)getActivity()).contactCustomService(username,Constant.CHATTYPE_CHATROOM);
				} else {
					((BaseActivity)getActivity()).contactCustomService(username,Constant.CHATTYPE_GROUP);
				}

			} else {
				((BaseActivity)getActivity()).contactCustomService(username,Constant.CHATTYPE_SINGLE);
			}


		}

	}
	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// /**
	// * @我的
	// * @param v
	// */
	// case R.id.my:
	// Intent intent = new Intent(getActivity(), MyActivity.class);
	// startActivity(intent);
	// break;
	// /**
	// * @评论
	// * @param v
	// */
	// case R.id.comment:
	// Intent intent2 = new Intent(getActivity(), CommentsActivity.class);
	// startActivity(intent2);
	// break;
	// /**
	// * @系统消息
	// * @param v
	// */
	// case R.id.system_msg:
	// Intent intent3 = new Intent(getActivity(), null);
	// startActivity(intent3);
	// break;
	//
	// default:
	// break;
	// }
	// }
}
