package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.yishanxiu.yishang.utils.BitmapHelper;
import com.yishanxiu.yishang.view.CircleImageView;

import net.tsz.afinal.json.JsonMap;
import com.yishanxiu.yishang.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Jaelyn on 16/3/18.
 * 相关人员适配器
 */
public class RelationPersonAdapter extends MyBaseAdapter {
    private ItemAttentionChange attentionChange;

    public void setAttentionChange(ItemAttentionChange attentionChange) {
        this.attentionChange = attentionChange;
    }

    public RelationPersonAdapter(Context context) {
        super(context);
    }

    public RelationPersonAdapter(Context context, List<? extends Map<String, ?>> datas) {
        super(context, datas);
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RelationPersonAdapterViewHolder relationPersonAdapterViewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.more_relation_person_item, null);
            relationPersonAdapterViewHolder=new RelationPersonAdapterViewHolder(view);
            view.setTag(relationPersonAdapterViewHolder);
        } else {
             relationPersonAdapterViewHolder= (RelationPersonAdapterViewHolder) view.getTag();
        }
        relationPersonAdapterViewHolder.tv_if_attention.setTag(i);
//        bindData(i,relationPersonAdapterViewHolder);
        return view;
    }

    /**
     * 绑定数据
     * @param i
     * @param relationPersonAdapterViewHolder
     */
    private void bindData(int i, RelationPersonAdapterViewHolder relationPersonAdapterViewHolder) {
        JsonMap jsonMap= (JsonMap) datas.get(i);
        BitmapHelper.setUserIcon(context,jsonMap,relationPersonAdapterViewHolder.user_photo_iv);
        relationPersonAdapterViewHolder.userName_tv.setText(jsonMap.getStringNoNull("userName"));
        relationPersonAdapterViewHolder.user_discri_tv.setText(jsonMap.getStringNoNull("userSignature"));
        int focusedType=jsonMap.getInt("focusedType",0);
        if(focusedType==0){
            relationPersonAdapterViewHolder.tv_if_attention.setText(R.string.attention);
        } else if (focusedType == 1) {
            relationPersonAdapterViewHolder.tv_if_attention.setText(R.string.attentioned);
        } else if (focusedType == 2) {
            relationPersonAdapterViewHolder.tv_if_attention.setText(R.string.inter_attention);
        }
    }

    private  class RelationPersonAdapterViewHolder {
        @ViewInject(R.id.user_photo_iv)
        private CircleImageView user_photo_iv;
        /**
         * 昵称
         */
        @ViewInject(R.id.userName_tv)
        private TextView userName_tv;

        /**
         * 描述
         */
        @ViewInject(R.id.user_discri_tv)
        private TextView user_discri_tv;
        /**
         * 关注
         */
        @ViewInject(R.id.tv_if_attention)
        private TextView tv_if_attention;

        @OnClick(R.id.tv_if_attention)
        public void attention_tv_click(View view) {
            if (attentionChange != null) {
                attentionChange.onStatusChange((Integer) view.getTag(), (TextView) view);
            }
        }

        public RelationPersonAdapterViewHolder(View view) {
            ViewUtils.inject(this, view);
        }
    }

    public interface ItemAttentionChange{
        void onStatusChange(int position, TextView tv_if_attention);
    }
}
