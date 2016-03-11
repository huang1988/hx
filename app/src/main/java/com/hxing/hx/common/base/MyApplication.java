package com.hxing.hx.common.base;

import android.app.Application;

/**
 * @Description描述:
 * @Author作者: hx
 */
public class MyApplication extends Application{
    private static MyApplication instance;
    private String cookieStr = "";

    public String getCookieStr() {
        return cookieStr;
    }

    public void setCookieStr(String cookieStr) {
        this.cookieStr = cookieStr;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
