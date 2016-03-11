package com.hxing.hx.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.hxing.hx.R;

import java.lang.reflect.Method;

/**
 * @Description描述:常用对话框
 * @Author作者: hx
 */
public class DialogUtils {

    private static DialogUtils instance;//对话框单列对象

    public static DialogUtils getInstance() {
        if (instance == null)
            instance = new DialogUtils();
        return instance;
    }

    private IDialogConfirmListener dialogConfirmListener;
    private IDialogCancelListener dialogCancelListener;

    /**
     * 设置对话框确认按钮点击监听器
     *
     * @param listener
     */
    public void setDialogConfirmListener(IDialogConfirmListener listener) {
        dialogConfirmListener = listener;
    }

    /**
     * 设置对话框取消按钮点击监听器
     *
     * @param listener
     */
    public void setDialogCancelListener(IDialogCancelListener listener) {
        dialogCancelListener = listener;
    }

    /**
     * 显示对话框
     * @param ctx
     * @param hint //提示信息
     */
    public void ShowDialog(Context ctx, String hint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(hint);
        builder.setTitle(ctx.getString(R.string.str_dialog_title));

        builder.setPositiveButton(ctx.getString(R.string.str_dialog_sure), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (dialogConfirmListener != null)
                    dialogConfirmListener.onDialogConfirmListener();
            }
        });
        builder.setNegativeButton(ctx.getString(R.string.str_dialog_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(dialogCancelListener !=null){
                    dialogCancelListener.onDialogCancelListener();
                }
            }
        });
        builder.create().show();
    }

    /**
     * 对话框确认事件回调接口
     */
    public interface IDialogConfirmListener {
        public void onDialogConfirmListener();
    }

    /**
     * 对话框取消事件回调接口
     */
    public interface IDialogCancelListener {
        public void onDialogCancelListener();
    }

    /***
     * 退出登录的提示对话框
     */
    public static void exitDialog(final Context ctx) {
        DialogUtils dialogUtils = DialogUtils.getInstance();
        dialogUtils.ShowDialog(ctx,ctx.getString(R.string.is_logout));
        dialogUtils.setDialogConfirmListener(new IDialogConfirmListener() {
            @Override
            public void onDialogConfirmListener() {
//                clearLoginMessage();//清空登录信息
//                Intent intent = new Intent(ctx, LoginActivity.class);//跳转到登录页面
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(intent);
            }
        });

    }

    /**
     * 重新加载数据的提示对话框
     * @param ClassName  //类名
     * @param MethodName //加载数据的方法名
     * @param mActivity  //Acitivity对象
     */
    public static void getAgain( final String ClassName, final String MethodName, final Activity mActivity,String hint) {
        DialogUtils dialogUtils = DialogUtils.getInstance();
        dialogUtils.ShowDialog(mActivity,hint);
        dialogUtils.setDialogConfirmListener(new IDialogConfirmListener() {
            @Override
            public void onDialogConfirmListener() {
                try {
                    Class c = Class.forName(ClassName);
                    Object objecto = c.newInstance();
                    Method method = c.getDeclaredMethod(MethodName, c, null);
                    method.invoke(objecto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
       dialogUtils.setDialogCancelListener(new IDialogCancelListener() {
           @Override
           public void onDialogCancelListener() {
               mActivity.finish();
           }
       });
    }
}
