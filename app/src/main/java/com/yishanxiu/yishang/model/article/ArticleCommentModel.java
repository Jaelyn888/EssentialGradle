package com.yishanxiu.yishang.model.article;

import com.yishanxiu.yishang.model.user.UserBaseInfoModel;

/**
 * Created by Jaelyn on 2016/9/21.
 * 文章评论
 */
public class ArticleCommentModel extends UserBaseInfoModel {
	private String commentId;

	private String commentContent;

	private String toUserId;

	private String createTime;
	/**
	 * 回复人昵称
	 */
	private String toUserNickname;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getToUserNickname() {
		return toUserNickname;
	}

	public void setToUserNickname(String toUserNickname) {
		this.toUserNickname = toUserNickname;
	}
}
