package com.yishanxiu.yishang.activity;

import java.util.HashMap;
import java.util.Map;

import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 文章发表评论
 * 
 * @author FangDongzhang
 *
 *         2016/7/30
 */
public class ArticalPubCommentActivity extends BaseUIActivity {

	/**
	 * 评论输入框，和发送按钮
	 */
	@ViewInject(id=R.id.comment_edit)
	private EditText pro_et_comment;

	private String articalId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.artical_pub_comments_layout);
		setBtn_menuText(R.string.send, onClickListener);
		setCenter_title(R.string.pub_comments);
		Intent intent = getIntent();
		articalId = intent.getStringExtra("articalId");
		pro_et_comment.setFocusableInTouchMode(true);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			/**
			 * 提交评论
			 *
			 * @param view
			 */
			if (TextUtils.isEmpty(pro_et_comment.getText())) {
				// toast.showToast(R.string.comment_content_isnull);
				return;
			}
			if (Utils.isHasLogin(mContext)) {
				getAddComment();
			} else {
				gotoLogin();
			}
		}
	};

	/**
	 * 添加评论 {DiscoverId:1,Content:"xxxxxx",UserInfoId:9392}
	 *
	 * @param
	 * @return void 返回类型
	 * @throws @Title:
	 *             getAddComment
	 * @Description: TODO作用：评论
	 */
	public void getAddComment() {
		loadingToast.show();
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("articleId", articalId);
		params.put("createUserId", Utils.getUserId(mContext));
		params.put("commentContent", pro_et_comment.getText().toString());
		GetDataQueue queue = new GetDataQueue();
		queue.setActionName(GetDataConfing.Action_SendArticalComment);
		queue.setParamsNoJson(params);
		queue.setCallBack(callBack);
		queue.setWhat(GetDataConfing.What_SendArticalComment);
		getDataUtil.getData(queue);

	}

	private IGetServicesDataCallBack callBack = new IGetServicesDataCallBack() {

		@Override
		public void onLoaded(GetDataQueue entity) {
			if (entity.isOk()) {
				// if (ShowGetDataError.isCodeSuccess(mContext,
				// entity.getInfo())) {
				if (entity.what == GetDataConfing.What_SendArticalComment) {
					pro_et_comment.setText("");
					pro_et_comment.setHint(R.string.comment_submit_hint);
					// reId = "0";
					// iscomment = 1;
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(pro_et_comment.getWindowToken(), 0);

					finish();
				}
			} else {
				ShowGetDataError.showNetError(mContext);
			}
			loadingToast.dismiss();
		}

	};
}
