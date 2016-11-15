package com.yishanxiu.yishang.app;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;
import com.hyphenate.chatuidemo.DemoApplication;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cookie.store.PersistentCookieStore;
import com.lzy.okhttputils.model.HttpHeaders;
import com.yishanxiu.yishang.getdata.GetServicesDataUtil;
import com.yishanxiu.yishang.utils.*;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import net.tsz.afinal.toast.ToastUtil;

/**
 * Created by Administrator on 2015/8/17 0017.
 */
public class MyApplication extends DemoApplication {
	private static MyApplication mInstance = null;

	/**
	 * 调用服务器接口获取服务器数据的工具
	 */
	public static MyApplication getInstance() {
		if (mInstance == null) {
			mInstance = new MyApplication();
		}
		return mInstance;
	}

	/**
	 * 购物车商品数量
	 */
	private int shopcart_num = 0;

	public int getShopcart_num() {
		return shopcart_num;
	}

	public void setShopcart_num(int shopcart_num) {
		this.shopcart_num = shopcart_num;
	}

	public void addShopCartNum() {
		shopcart_num++;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
//		if (LogUtil.isDebug) {
//			MyCrashHandler crashHandler = MyCrashHandler.getInstance();
//			crashHandler.init(getApplicationContext());
//		}
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		Point point = new Point();
		wm.getDefaultDisplay().getRealSize(point);
		DisplayUtil.screenHeight = point.y;
		DisplayUtil.screenWidth = point.x;

		//UserProfilePreferenceManager.init(mInstance);
		initHttp();
		JPushInterface.init(this);

		ShareSocialUtils.init();
		Constant.productChachePath = getExternalCacheDir().getAbsolutePath() + File.separator;
		initImagePicker();
	}

	private void initImagePicker() {
		ImagePicker imagePicker = ImagePicker.getInstance();
		imagePicker.setImageLoader(new CustomImageLoader());   //设置图片加载器
		imagePicker.setShowCamera(true);  //显示拍照按钮
		imagePicker.setCrop(true);        //允许裁剪（单选才有效）
		imagePicker.setSaveRectangle(true); //是否按矩形区域保存
		imagePicker.setSelectLimit(9);    //选中数量限制
		imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
		imagePicker.setFocusWidth(500);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
		imagePicker.setFocusHeight(500);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
		imagePicker.setOutPutX(500);//保存文件的宽度。单位像素
		imagePicker.setOutPutY(500);//保存文件的高度。单位像素
	}

	private void initHttp() {
		HttpHeaders headers = new HttpHeaders();
		headers.put("User-Agent", "Android"); // header不支持中文
		// 必须调用初始化
		try {
			OkHttpUtils.init(this);
			OkHttpUtils.getInstance()
					// 如果使用默认的 60秒,以下三行不需要传
					.setConnectTimeout(30 * 1000) // 全局的连接超时时间
					.setReadTimeOut(30 * 1000) // 全局的读取超时时间
					.setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS) // 全局的写入超时时间
					// .setCookieStore(new MemoryCookieStore())
					// //cookie使用内存缓存（app退出后，cookie消失）
					.setCookieStore(new PersistentCookieStore()) // cookie持久化存储，如果cookie不过期，则一直有效
					.addCommonHeaders(headers); // 设置全局公共参数
			if (LogUtil.isDebug) {
				OkHttpUtils.getInstance().debug("httpUtils");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
