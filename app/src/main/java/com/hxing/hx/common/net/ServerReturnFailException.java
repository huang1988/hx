package com.hxing.hx.common.net;

/**
 * @Description描述:
 * @Author作者: hx
 */
public class ServerReturnFailException extends RuntimeException {
    public ServerReturnFailException(String detailMessage) {
        super(detailMessage);
    }
}
