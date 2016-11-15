package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.widget.TextView;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.model.article.ArticleCommentModel;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.StringUtils;
import com.yishanxiu.yishang.view.CircleImageView;

/**
 * Created by Jaelyn on 16/3/3. 文章评论
 */
public class ArticleCommentsAdapter extends CommonBaseAdapter<ArticleCommentModel> {

	private int flag;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}


	public ArticleCommentsAdapter(Context context) {
		super(context,R.layout.artical_comments_item);
	}



	@Override
	protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, ArticleCommentModel model) {

		 CircleImageView user_photo_iv= (CircleImageView) viewHolderHelper.getImageView(R.id.user_photo_iv);
		 TextView userName_tv=viewHolderHelper.getTextView(R.id.userName_tv);
		 TextView create_time_tv=viewHolderHelper.getTextView(R.id.create_time_tv);
		 TextView userComments_tv=viewHolderHelper.getTextView(R.id.userComments_tv);
		userName_tv.setText(model.getUserNickname());

		create_time_tv.setText(StringUtils.getTimeFormatFull(model.getCreateTime()));

		BitmapHelper.setUserIcon(mContext,model, user_photo_iv);
		userComments_tv.setText(model.getCommentContent());
	}
}
