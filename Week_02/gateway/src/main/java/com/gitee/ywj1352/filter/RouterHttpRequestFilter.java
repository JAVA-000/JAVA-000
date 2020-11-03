package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Request;
import com.gitee.ywj1352.router.HttpEndpointRouter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RouterHttpRequestFilter implements HttpRequestFilter {
    private final HttpEndpointRouter router;

    public RouterHttpRequestFilter(HttpEndpointRouter router) {
        this.router = router;
    }

    @Override
    public void filter(Request fullRequest, ChannelHandlerContext ctx) {
        String uri = fullRequest.getRequest().uri();
        List<String> endpoints = findEndpoints(uri);
        assert endpoints != null;
        String url = router.route(endpoints);
        fullRequest.setUrl(url);
    }


    //
    private List<String> findEndpoints(String uri){
          return Arrays.asList("http://127.0.0.1:8088");
    }
}
