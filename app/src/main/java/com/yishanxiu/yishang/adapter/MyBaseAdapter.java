package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by tanghuan on 2015/6/11.
 */
public class MyBaseAdapter extends BaseAdapter {
	protected List<? extends Map<String, ?>> datas;
	public Context context;
	protected LayoutInflater layoutInflater;
	public MyBaseAdapter(Context context) {
		super();
		this.context=context;
		layoutInflater=LayoutInflater.from(context);
	}

	public MyBaseAdapter(Context context, List<? extends Map<String, ?>> datas) {
		this(context);
		this.datas = datas;

	}

	public void setDatas(List<? extends Map<String, ?>> datas) {
		this.datas = datas;
	}

    public List<? extends Map<String, ?>> getDatas() {
        return datas;
    }

    @Override
	public int getCount() {
		if(datas!=null){
			return datas.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int i) {
		if(datas!=null){
			return datas.get(i);
		}else {
			return null;
		}
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		return null;
	}


}
