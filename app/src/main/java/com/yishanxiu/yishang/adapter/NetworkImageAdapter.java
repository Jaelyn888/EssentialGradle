package com.yishanxiu.yishang.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.yishanxiu.yishang.R;

import java.util.List;

import com.squareup.picasso.Picasso;
import com.yishanxiu.yishang.adapter.BlogAdapter.ItemCompontClickListener;
import com.yishanxiu.yishang.utils.BitmapHelper;

/**
 * Created by FangDongzhang on 16/4/12.
 */
public class NetworkImageAdapter extends DefaultAdapter<String> implements ItemCompontClickListener{
	
	ItemCompontClickListener itemCompontClickListener;

    public NetworkImageAdapter(Context context, List<String> t) {
        super(context, t);
    }

    
    @Override
    public View getView(int positon, View recycleView) {
        String url = getItem(positon) ;
        ImageView imageView ;

        if (recycleView == null) {
            imageView = generialDefaultImageView() ;
        } else {
            imageView = (ImageView) recycleView;
        }
        
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        BitmapHelper.loadImage(context,url,imageView);
        
        return imageView;
    }


	@Override
	public void onItemCompontClickListener(int position, int shopCartitemType, int index) {
		// TODO Auto-generated method stub
//		if (itemCompontClickListener != null) {
//            itemCompontClickListener.onItemCompontClickListener((Integer) view.getTag(), Constant.BlogItemCompontType.SCAN_BIG_IMAGE, position);
//        }
	}
}
