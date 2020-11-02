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

    @Override
    public void filter(Request fullRequest, ChannelHandlerContext ctx) {
        String url = fullRequest.getUrl();
        HttpExecutor httpExecutor = new HttpExecutor();
        httpExecutor.fetchGet(fullRequest.getRequest(),ctx, fullRequest.getUrl());
    }

}
