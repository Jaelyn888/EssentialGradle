package com.yishanxiu.yishang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yishanxiu.yishang.activity.base.BaseUIActivity;
import com.yishanxiu.yishang.adapter.RelationPersonAdapter;
import com.yishanxiu.yishang.getdata.GetDataConfing;
import com.yishanxiu.yishang.getdata.GetDataQueue;
import com.yishanxiu.yishang.getdata.IGetServicesDataCallBack;
import com.yishanxiu.yishang.getdata.JsonKeys;
import com.yishanxiu.yishang.getdata.ShowGetDataError;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.json.JsonMap;
import net.tsz.afinal.json.JsonParseHelper;
import com.yishanxiu.yishang.R;

/**
 * Created by Jaelyn on 16/3/18.
 * 更多相关人员
 */
public class MoreRelationPersonActivity extends BaseUIActivity {


    @ViewInject(id = R.id.ptr_list)
    private PullToRefreshListView ptr_list;

    @ViewInject(id = R.id.nodata_layout)
    private LinearLayout nodata_layout;

    private List<JsonMap<String, Object>> dataList = new ArrayList<JsonMap<String, Object>>();
    private RelationPersonAdapter relationPersonAdapter;
    private int nPage = 0;

    /**
     * 请求类型
     */
    private int requestType = 0;
    private String articalId="0";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_relation_person_layout);
        requestType=getIntent().getIntExtra(ExtraKeys.Key_Msg1,0);
        if(requestType==0) {
            setCenter_title(R.string.relation_persion, true);
             articalId=getIntent().getStringExtra(ExtraKeys.Key_Msg2);
        } else {

        }
        relationPersonAdapter = new RelationPersonAdapter(mContext, dataList);
        ptr_list.setAdapter(relationPersonAdapter);

        ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
        ptr_list.setOnRefreshListener(loadMoreListener);
        ptr_list.setOnItemClickListener(onItemClickListener);

        loadingToast.show();
        getServerData();
    }


    /**
     * item  点击监听
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int itemPosition, long id) {
            if (itemPosition > 0) {
                JsonMap jsonMap_temp = (JsonMap) dataList.get(itemPosition - 1);
                String discoreId = jsonMap_temp.getStringNoNull("articalId");
                Intent intent = new Intent(mContext, ArticalDetailActivity.class);
                intent.putExtra(Constant.ID, discoreId);
                intent.putExtra(Constant.NAME, jsonMap_temp.getStringNoNull("articalTitle"));
                jumpTo(intent, false);
            }
        }
    };

    private PullToRefreshBase.OnRefreshListener2 loadMoreListener = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            nPage=0;
            getServerData();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            getServerData();
        }
    };

    /**
     * 开始搜索
     */
    private void getServerData() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userId", Utils.getUserId(mContext));
        if(requestType==0){
            params.put("articleId", articalId);
        }
        params.put("page", nPage + 1);
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
            ptr_list.onRefreshComplete();
            if (entity.isOk()) {
                if (ShowGetDataError.isCodeSuccess(mContext, entity.getInfo())) {
                    if (entity.getWhat() == GetDataConfing.What_GetMoreRelationPersion) {
                        ArrayList<JsonMap<String, Object>> temp_data = (ArrayList<JsonMap<String, Object>>) JsonParseHelper.getJsonMap_List_JsonMap(entity.getInfo(), JsonKeys.Key_Info);
                        setAdapterData(temp_data);
                        validateData();
                    }
                }
            } else {
                ShowGetDataError.showNetError(mContext);
            }
            loadingToast.dismiss();
        }

    };

    /**
     * 校验数据 显示无数据等
     * 是否请求正常 网络错误且数据为空的话显示网络异常界面 提供刷新的接口
     *
     * @param
     */
    private void validateData() {

        if (dataList == null || dataList.size() == 0) {
            ptr_list.setVisibility(View.GONE);
            nodata_layout.setVisibility(View.VISIBLE);
        } else {
            ptr_list.setVisibility(View.VISIBLE);
            nodata_layout.setVisibility(View.GONE);
        }

    }

    private void setAdapterData(ArrayList<JsonMap<String, Object>> temp_data) {
        if (temp_data.size() == 0) {
            ptr_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            ptr_list.setMode(PullToRefreshBase.Mode.BOTH);
        }
        if (nPage == 0) {
            dataList.clear();
        }
        dataList.addAll(temp_data);
        relationPersonAdapter.setDatas(dataList);
        relationPersonAdapter.notifyDataSetChanged();

        nPage++;
    }

}