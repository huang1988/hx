package com.hxing.hx.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.hxing.hx.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @Description描述:APP常用方法
 * @Author作者: hx
 */
public class AppUtils {
    private static boolean isExitClicked;


    /**
     * 关闭app
     */
    public static void closeApp(Activity activity){
        if (isExitClicked) {
            isExitClicked = false;
            activity.finish();
        } else {
            Toast.makeText(activity, R.string.click_more_exit, Toast.LENGTH_SHORT).show();
            isExitClicked = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExitClicked = false;
                }
            }, 10000);
        }
    }

    /**
     * 切换全屏状态。
     *
     * @param activity
     *            Activity
     * @param isFull
     *            设置为true则全屏，否则非全屏
     */
    public static void toggleFullScreen(Activity activity, boolean isFull) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (isFull) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }

    /**
     * 获取屏幕宽度，单位像素
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 获取屏幕高度，单位像素
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int getDensity(Context context) {
        DisplayMetrics displayMetric = new DisplayMetrics();
        displayMetric = context.getResources().getDisplayMetrics();
        return (int) displayMetric.density;
    }

    /**
     * 隐藏Activity的系统默认标题栏
     */
    public static void hideTitleBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 将该Activity设置为竖直
     */
    public static void setScreenVertical(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 将该Activity设置为横向
     */
    public static void setScreenHorizontal(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    // 获取包信息
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    // 版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    // 版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    // 文件大小格式化
    public static String getFileSizeDescription(String size) {
        String value = "";
        long bytes = 0;
        try {
            bytes = Long.valueOf(size).longValue();
        } catch (NumberFormatException e) {
            return size;
        }
        if (bytes < 1024) {
            value = (int) bytes + "B";
        } else if (bytes < 1048576) {
            DecimalFormat df = new DecimalFormat("#0.00");
            value = df.format((float) bytes / 1024.0) + "K";
        } else if (bytes < 1073741824) {
            DecimalFormat df = new DecimalFormat("#0.00");
            value = df.format((float) bytes / 1048576.0) + "M";
        } else {
            DecimalFormat df = new DecimalFormat("#0.00");
            value = df.format((float) bytes / 1073741824.0) + "G";
        }
        return value;
    }

    // 程序是否在后台运行
    public static final boolean isAppOnBackground(Context context) {
        if (null == context) {
            return false;
        }

        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);

        if (!pm.isScreenOn()) {
            return true;
        }

        // Returns a list of application that are running on the device
        ActivityManager activityManager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        // List<RunningTaskInfo> rti = activityManager.getRunningTasks(1);
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            // Logger.e(appProcess.processName+":"+packageName+":"+appProcess.importance+":"+RunningAppProcessInfo.IMPORTANCE_FOREGROUND+":"+RunningAppProcessInfo.IMPORTANCE_BACKGROUND+":"+RunningAppProcessInfo.IMPORTANCE_VISIBLE);
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                // if (rti.get(0).topActivity != null
                // && rti.get(0).topActivity.getPackageName().equals(
                // packageName)) {
                // return true;
                // }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className
     *            某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            System.err.println(className+"....."+cpn.getClassName());
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取验证码倒计时计数器
     *
     * @param btn               获取验证码按钮
     * @param millisInFuture    计数时长
     * @param countDownInterval 计数间隔时间
     * @return 计时器
     */
    public static CountDownTimer getTimer(final Button btn, long millisInFuture, long countDownInterval) {
        CountDownTimer timer = new CountDownTimer(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
                btn.setText((millisUntilFinished / 1000) + "秒后\n重新获取");
            }

            @Override
            public void onFinish() {
                btn.setText("获取验证码");
                btn.setEnabled(true);
            }
        };
        return timer;
    }
}
