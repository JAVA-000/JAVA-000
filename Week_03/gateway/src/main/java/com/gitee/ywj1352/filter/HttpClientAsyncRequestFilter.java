package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Request;
import com.gitee.ywj1352.utils.HttpExecutor;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author: yangwenjie.a
 * @date: 2020/11/2 09:27
 * @description:
 */
public class HttpClientAsyncRequestFilter implements HttpRequestFilter{

    private final HttpExecutor httpExecutor;

    public HttpClientAsyncRequestFilter(HttpExecutor httpExecutor) {
        this.httpExecutor = httpExecutor;
    }

    @Override
    public void filter(Request fullRequest, ChannelHandlerContext ctx) {
        String url = fullRequest.getUrl();
        httpExecutor.fetchRequest(fullRequest.getRequest(),ctx, fullRequest.getUrl());
    }

}
