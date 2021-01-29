package com.zenglh.io.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class MyHttpRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        fullRequest.setUri("?nio=zenglh");
        System.out.println("修改后的请求地址uri: " + fullRequest.uri());

    }
}
