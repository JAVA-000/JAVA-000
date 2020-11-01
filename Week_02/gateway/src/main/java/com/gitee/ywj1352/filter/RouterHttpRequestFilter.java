package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.HashMap;
import java.util.List;

public class RouterHttpRequestFilter implements HttpRequestFilter,HttpEndpointRouter {

    @Override
    public void filter(Request fullRequest, ChannelHandlerContext ctx) {
        String uri = fullRequest.getUrl();
        HashMap<String, String> map = new HashMap<>();
        map.put("host","127.0.0.1");
        map.put("port","8088");
        ctx.writeAndFlush(fullRequest);

    }
    @Override
    public String route(List<String> endpoints) {
        return null;
    }
}
