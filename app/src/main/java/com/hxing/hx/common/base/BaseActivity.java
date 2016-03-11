package com.hxing.hx.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxing.hx.R;

/**
 * @Description描述:基Activity
 * @Author作者: hx
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected static String TAG = "BaseActivity";
    protected Context mContext;
    protected ImageView iv_titleBar_left;
    protected ImageView iv_titleBar_right;
    protected TextView tv_titleBar_title;
    protected LinearLayout toolBar;

    @LayoutRes
    public abstract int getLayoutRes();//获取Activity布局文件
    public abstract Activity getActivity();//获取Activity对象
    public abstract void initView(Bundle savedInstanceState);//初始化控件
    public abstract void afterInitView();//完成控件初始化后执行的任务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        mContext = this;
        TAG = getActivity().getClass().getSimpleName();//设置TAG为当前Acitivity
        initToolBar();
        initView(savedInstanceState);
        afterInitView();
    }

    protected void initToolBar() {
        LinearLayout toolBar = (LinearLayout) findViewById(R.id.toolBar);
        if(toolBar!=null){//判断当前Activity是否使用统一的自定义顶部bar
            iv_titleBar_left = (ImageView) findViewById(R.id.iv_titleBar_left);
            iv_titleBar_right = (ImageView) findViewById(R.id.iv_titleBar_right);
            tv_titleBar_title = (TextView) findViewById(R.id.tv_titleBar_title);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
