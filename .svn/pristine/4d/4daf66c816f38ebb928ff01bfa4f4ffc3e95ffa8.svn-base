package net.tsz.afinal.toast;

/**
 * Created by Jaelyn on 16/3/4.
 */
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Build.VERSION;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

public class ToastUtil {
    private final String TAG = super.getClass().getSimpleName();
    private static ToastUtil toastUtil;
    private Toast toast = null;
    private Context context;
    private Timer timer;
    private ToastUtil.ToHideTimerTask task;
    private Object obj;
    private final int what_toHide = 0;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            ToastUtil.this.myHide();
        }
    };

    private ToastUtil(Context context) {
        this.context = context.getApplicationContext();
        this.toast = Toast.makeText(context, "Toast", Toast.LENGTH_SHORT);

        try {
            Field e = this.toast.getClass().getDeclaredField("mTN");
            e.setAccessible(true);
            this.obj = e.get(this.toast);
        } catch (Exception var3) {

        }

        this.timer = new Timer();
    }

    public static ToastUtil initToast(Context context) {
        if(toastUtil == null) {
            toastUtil = new ToastUtil(context);
        }

        return toastUtil;
    }

    public Toast getToast() {
        return this.toast;
    }

    public void showToast(String msg) {
        if(this.toast != null) {
            this.toast.setText(msg);
        }

        this.showToast();
    }

    void showToast() {
        if(this.toast != null && VERSION.SDK_INT >= 14) {
            if(this.toast != null) {
                this.toast.setDuration(Toast.LENGTH_SHORT);
                this.toast.show();
            } else {
                this.myShow();
            }
        }

    }

    public void showToast(int resId) {
        this.showToast(this.context.getResources().getString(resId));
    }

    public void hideToast() {
        if(VERSION.SDK_INT >= 14) {
            if(this.toast != null) {
                this.toast.cancel();
            }
        } else {
            this.myHide();
        }

    }

    private void myShow() {
        try {
            Method e = this.obj.getClass().getDeclaredMethod("show", (Class[])null);
            e.invoke(this.obj, (Object[])null);
            if(this.task != null) {
                this.task.cancel();
            }

            this.task = new ToastUtil.ToHideTimerTask();
            this.timer.schedule(this.task, 3000L);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    private void myHide() {
        try {
            Method method = this.obj.getClass().getDeclaredMethod("hide", (Class[])null);
            method.invoke(this.obj, (Object[])null);
        } catch (Exception var2) {
        }

    }

    private class ToHideTimerTask extends TimerTask {
        private ToHideTimerTask() {
        }

        public void run() {
            ToastUtil.this.handler.sendEmptyMessage(0);
        }
    }
}
