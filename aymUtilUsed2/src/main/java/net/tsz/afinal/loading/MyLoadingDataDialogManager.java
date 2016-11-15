package net.tsz.afinal.loading;

import android.app.ProgressDialog;
import android.content.Context;
import com.example.aymutilused2.R;

/**
 * Created by admin on 15/6/26.
 */
public class MyLoadingDataDialogManager {
	private final String TAG = MyLoadingDataDialogManager.class.getSimpleName();
	private int index = 0;
	ProgressDialog progressDialog;
	private boolean isCancelable = true;
	private Context context;

	private MyLoadingDataDialogManager(Context context, String loadingMsg) {
		this.context = context;
		//this.progressDialog = new LoadingDataDialog(context);
		progressDialog= new ProgressDialog(context);//,R.style.cusProgressDialog);
		this.progressDialog.setCanceledOnTouchOutside(isCancelable);
		if (loadingMsg == null) {
			setDefaultMsg();
		}
		this.progressDialog.setCancelable(isCancelable);
	}


	/**
	 * 恢复默认提示
	 *
	 */
	public void setDefaultMsg() {
		this.progressDialog.setMessage(context.getString(R.string.text_loadingdata));
	}

	public boolean isCancelable() {
		return isCancelable;
	}

	public void setIsCancelable(boolean isCancelable) {
		this.isCancelable = isCancelable;
	}

	public static MyLoadingDataDialogManager init(Context context) {
		return new MyLoadingDataDialogManager(context, null);
	}

	public static MyLoadingDataDialogManager init(Context context, String loadingMsg) {
		return new MyLoadingDataDialogManager(context, loadingMsg);
	}

	public static MyLoadingDataDialogManager init(Context context, int resIdLoadingMsg) {
		return new MyLoadingDataDialogManager(context, context.getResources().getString(resIdLoadingMsg));
	}

	public void destroy() {
		if (this.progressDialog != null) {
			this.progressDialog.dismiss();
			this.progressDialog = null;
		}

	}

	public void show() {
		if (this.index == 0) {
			if (this.progressDialog != null) {
				setDefaultMsg();
				this.progressDialog.show();
			} else {
			}
		}

		++this.index;
	}

	public void show(String msg) {
		if (this.progressDialog != null) {
			this.progressDialog.setMessage(msg);
		} else {
		}
	}

	public void show(int resId) {
		if (this.progressDialog != null) {
			this.progressDialog.setMessage(context.getString(resId));
		} else {
		}
	}

	public void update(String msg) {
		if (this.progressDialog != null) {
			this.progressDialog.setMessage(msg);
		} else {
		}
	}

	public void update(int resId) {
		if (this.progressDialog != null) {
			this.progressDialog.setMessage(context.getString(resId));
		} else {
		}
	}

	public void dismiss() {
		if (this.index <= 1) {
			if (this.progressDialog != null) {
				this.progressDialog.hide();
			} else {
			}

			this.index = 0;
		} else {
			--this.index;
		}

	}
}
