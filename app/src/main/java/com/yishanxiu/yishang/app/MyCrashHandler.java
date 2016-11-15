package com.yishanxiu.yishang.app;

import android.content.Context;
import android.os.Environment;

import java.io.*;
import java.lang.Thread.UncaughtExceptionHandler;

import com.yishanxiu.yishang.utils.Constant;


public class MyCrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";

	//系统默认的UncaughtException处理类
	private UncaughtExceptionHandler mDefaultHandler;
	//CrashHandler实例
	private static MyCrashHandler INSTANCE = new MyCrashHandler();
	//程序的Context对象
	private Context mContext;

	/** 保证只有一个CrashHandler实例 */
	private MyCrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static MyCrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * 初始化
	 *
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		//获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		//设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		saveCrashInfo2File(ex);
		//MobclickAgent.reportError(mContext,ex);

		//mContext.startActivity(new Intent(mContext, SplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 保存错误信息到文件中
	 *
	 * @param ex
	 * @return  返回文件名称,便于将文件传送到服务器
	 */
	private String saveCrashInfo2File(Throwable ex) {

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();

		return result;
	}
}