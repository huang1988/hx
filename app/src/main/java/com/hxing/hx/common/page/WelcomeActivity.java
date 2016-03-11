package com.hxing.hx.common.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.hxing.hx.R;
import com.hxing.hx.common.base.BaseActivity;
import com.hxing.hx.common.page.home.HomeActivity;

/**
 * @Description描述:
 * @Author作者: hx
 */
public class WelcomeActivity extends BaseActivity{
    //欢迎界面至少停留2秒
    private boolean twoSecond = false;
    private boolean doCheckFinish = false;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_welcome;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void afterInitView() {
        /* 2秒计时 */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twoSecond = true;
                enterApp();
            }
        }, 2000);
    }

    private void enterApp() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {

    }
}
