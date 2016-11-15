package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.tsz.afinal.json.JsonMap;

import com.yishanxiu.yishang.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Jaelyn on 16/2/22.
 * Simple Menu Adapter
 */
public class MenuAdapter extends MyBaseAdapter {
    public MenuAdapter(Context context, List<? extends Map<String, ?>> datas) {
        super(context, datas);
    }



    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        MenuAdapterViewHolder holder = null;
        if (convertView == null) {
            holder = new MenuAdapterViewHolder();
            convertView = layoutInflater.inflate(R.layout.checkbox_item, null, false);
            holder.itemTextView = (TextView) convertView.findViewById(R.id.itemTextView);
            convertView.setTag(holder);
        } else {
            holder = (MenuAdapterViewHolder) convertView.getTag();
        }
        JsonMap jsonMap= (JsonMap) datas.get(position);
        holder.itemTextView.setText(jsonMap.getStringNoNull("categoryName"));
        return convertView;
    }

    private class MenuAdapterViewHolder {
       private TextView itemTextView;
    }
}
