package com.gitee.ywj1352.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

public class RouterHttpRequestFilter implements HttpRequestFilter,HttpEndpointRouter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

    }

    @Override
    public String route(List<String> endpoints) {


        return null;
    }
}
