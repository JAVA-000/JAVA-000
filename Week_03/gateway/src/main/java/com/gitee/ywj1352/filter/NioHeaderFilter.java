package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class NioHeaderFilter implements HttpRequestFilter{

    @Override
    public void filter(Request fullRequest, ChannelHandlerContext ctx) {
        FullHttpRequest request = fullRequest.getRequest();
        request.headers().add("nio","yangwenjie");
    }
}
