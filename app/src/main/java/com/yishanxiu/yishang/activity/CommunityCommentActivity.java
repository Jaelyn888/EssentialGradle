package com.yishanxiu.yishang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import net.tsz.afinal.annotation.view.ViewInject;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.CommunityCommentAdapter;

/**
 * 社区评论
 * 
 * @author FangDongzhang 2016/4/11
 */
public class CommunityCommentActivity extends BaseUIActivity
		implements AdapterView.OnItemClickListener, StickyListHeadersListView.OnHeaderClickListener {
	@ViewInject(id = R.id.comment_layout)
	private LinearLayout linearLayout;
	
	@ViewInject(id = R.id.list)
	private StickyListHeadersListView stickyList;
	CommunityCommentAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_comments);
		setCenter_title(R.string.comment);
		setBtn_menu(R.drawable.more_white, null);

		stickyList.setOnItemClickListener(this);
		stickyList.setOnHeaderClickListener(this);
		
		stickyList.setDrawingListUnderStickyHeader(true);
	    stickyList.setAreHeadersSticky(true);
		adapter = new CommunityCommentAdapter(CommunityCommentActivity.this);
		stickyList.addHeaderView(getLayoutInflater().inflate(R.layout.community_comment_addhead, null));
//		listView.setAdapter(adapter);
	    stickyList.setAdapter(adapter);
	    stickyList.setPadding(0, 0, 0, 0);
	    stickyList.setSelection(1);//设置stickyList默认显示
	}

	@Override
	public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId,
			boolean currentlySticky) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
	
	
}
