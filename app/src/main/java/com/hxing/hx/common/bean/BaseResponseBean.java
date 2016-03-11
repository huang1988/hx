package com.hxing.hx.common.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Description描述: 基本的jsonbean
 * @Author作者: hx
 */
public class BaseResponseBean {
    @SerializedName("ResultCode")
    int resultCode = 1;
    @SerializedName("ResultMessage")
    String resultMessage;

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

}
