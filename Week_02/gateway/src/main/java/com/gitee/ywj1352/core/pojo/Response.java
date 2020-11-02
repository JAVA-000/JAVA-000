package com.gitee.ywj1352.core.pojo;

import io.netty.handler.codec.http.*;

/**
 * @author: yangwenjie.a
 * @date: 2020/11/2 09:22
 * @description:
 */
public class Response {
    private FullHttpResponse fullHttpResponse;

    public Response(FullHttpResponse fullHttpResponse) {
        this.fullHttpResponse = fullHttpResponse;
    }

    public FullHttpResponse getFullHttpResponse() {
        return fullHttpResponse;
    }

    public void setFullHttpResponse(FullHttpResponse fullHttpResponse) {
        this.fullHttpResponse = fullHttpResponse;
    }
}
