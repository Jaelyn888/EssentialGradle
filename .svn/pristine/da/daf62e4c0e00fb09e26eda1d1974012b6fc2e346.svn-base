package com.yishanxiu.yishang.receiver;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.yishanxiu.yishang.activity.*;
import com.yishanxiu.yishang.model.article.ArticleModel;
import com.yishanxiu.yishang.model.recommend.RecommendModel;
import com.yishanxiu.yishang.model.shopmall.BrandModel;
import com.yishanxiu.yishang.model.shopmall.ProductInfoModel;
import com.yishanxiu.yishang.model.user.PushMsgModel;
import com.yishanxiu.yishang.utils.Constant;
import com.yishanxiu.yishang.utils.ExtraKeys;
import com.yishanxiu.yishang.utils.LogUtil;
import com.yishanxiu.yishang.utils.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyJPushReceiver extends BroadcastReceiver {
    private final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
       // LogUtil.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            bindUser(context);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            processPushNotify(context,extra);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            if (connected) {
                bindUser(context);
            }
        } else {
        }
    }

    //退送消息类型pushType（0：文章，1：精选，2：店铺，3：商品，4：日志，5：品牌,6：日志评论，7：文章评论，8：订单发货，9：退款退货）
    private void processPushNotify(Context context, String extra) {
        PushMsgModel pushMsgModel=new Gson().fromJson(extra,PushMsgModel.class);
        int pushType = pushMsgModel.getPushTypeId();
        Intent intent = new Intent();
        switch (pushType) {
            case 0:
               intent.setClass(context,ArticalDetailActivity.class);
                ArticleModel articleModel=new ArticleModel();
                articleModel.setArticleId(pushMsgModel.getMainId());
                intent.putExtra(ExtraKeys.Key_Msg1,articleModel);
                break;
            case 1:
                intent.setClass(context, RecommendDerailActivity.class) ;
                RecommendModel recommendModel=new RecommendModel();
                recommendModel.setRecommendId(pushMsgModel.getMainId());
                intent.putExtra(ExtraKeys.Key_Msg1,recommendModel);
                break;
            case 2:
                intent.setClass(context, SplashActivity.class) ;
                break;
            case 3:
                intent.setClass(context,ProductDetail2.class);
                ProductInfoModel productInfoModel=new ProductInfoModel();
                productInfoModel.setProductId(pushMsgModel.getMainId());
                intent.putExtra(ExtraKeys.Key_Msg1,productInfoModel);
                break;
            case 4:
            case 5:
                intent.setClass(context,ShopActivity2.class);
                BrandModel brandModel=new BrandModel();
                brandModel.setBrandId(pushMsgModel.getMainId());
                intent.putExtra(ExtraKeys.Key_Msg1,brandModel);
                break;
            case 6:
            case 7:
                intent.setClass(context,ShopActivity2.class);
                break;
            case 8:
                String expressName=pushMsgModel.getAttr1();
                String expressNum=pushMsgModel.getThreeId();
                intent.putExtra("logisticNumber",expressNum);
                intent.putExtra("logistic",expressName);
                intent.setClass(context,LogisticActivity.class) ;
                break;
            case 9:
                intent.putExtra("returnRefundOrderId",pushMsgModel.getMainId());
                intent.setClass(context, QueryProgressActivity.class) ;
                break;
//            case 10:
//                break;
            default:
                intent.setClass(context, SplashActivity.class) ;
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);

    }

    private void bindUser(Context context) {
        if (Utils.isHasLogin(context)){
            Set<String> tagSet = new LinkedHashSet<String>();
            tagSet.add(Utils.getUserLevel(context)+"");
            JPushInterface.setAlias(context,Utils.getUserId(context).toString(),new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    LogUtil.d(TAG, ""+s);
                }
            });
            JPushInterface.setTags(context,tagSet,new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    LogUtil.d(TAG, ""+s);
                }
            });
        }
    }
}
