package com.hxing.hx.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @Description描述: Toast工具类
 * @Author作者: hx
 */
public class ToastUtil {

    /**
     * @param context
     * @param msg
     * @param duration
     * @return void
     * @Description描述: 通用的toast提示信息
     */
    public static void show(Context context, String msg, int duration) {
        Toast.makeText(context.getApplicationContext(),msg,duration).show();
    }

    /**
     * @param context
     * @param msgResID
     * @param duration
     * @return void
     * @Description描述: 通用的toast提示信息
     */
    public static void show(Context context, int msgResID, int duration) {
        String msg = context.getResources().getString(msgResID);
        Toast.makeText(context.getApplicationContext(),msg,duration).show();
    }

    /**
     * @param context
     * @param msg
     * @return void
     * @Description描述: Toast.LENGTH_SHORT
     */
    public static void show(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * @param context
     * @param msgResID
     * @return void
     * @Description描述: Toast.LENGTH_SHORT
     */
    public static void show(Context context, int msgResID) {
        show(context, msgResID, Toast.LENGTH_SHORT);
    }

    /**
     * @param context
     * @param msg
     * @return void
     * @Description描述: Toast.LENGTH_LONG
     */
    public static void showLong(Context context, String msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }

    /**
     * @param context
     * @param msgResID
     * @return void
     * @Description描述: Toast.LENGTH_LONG
     */
    public static void showLong(Context context, int msgResID) {
        show(context, msgResID, Toast.LENGTH_LONG);
    }
}
