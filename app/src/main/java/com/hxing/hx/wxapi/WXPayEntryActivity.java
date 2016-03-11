package com.hxing.hx.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.hxing.hx.R;
import com.hxing.hx.common.base.BaseActivity;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 支付页面
 */
public class WXPayEntryActivity extends BaseActivity implements
        IWXAPIEventHandler {

    private IWXAPI mWexinPay;
    public static final String WEIXIN_APP_ID = "wx97ba7bd0900a72e4";//微信key

    @Override
    public int getLayoutRes() {
        return R.layout.pay_activity;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MyPayFragment fragment = new MyPayFragment();
        ft.add(R.id.content, fragment);
        ft.commit();
        mWexinPay = WXAPIFactory.createWXAPI(WXPayEntryActivity.this, null);
        mWexinPay.registerApp(WEIXIN_APP_ID);
    }

    @Override
    public void afterInitView() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mWexinPay.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq arg0) {// 微信支付请求

    }

    @Override
    public void onResp(BaseResp resp) {// 微信支付结果
        System.out.println("微信支付结果--->" + resp.errCode + "," + resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int result = resp.errCode;
            switch (result) {
                case 0:
                    Toast.makeText(WXPayEntryActivity.this, "支付成功",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("flag", true);
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case -1:
                    Toast.makeText(WXPayEntryActivity.this, "支付失败",
                            Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(WXPayEntryActivity.this, "用户取消支付",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
}
