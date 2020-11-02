package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Request;
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

    }

    /**
     * @param fullRequest
     * @param ctx
     */
    private void doRequest(Request fullRequest ,ChannelHandlerContext ctx){


    }
}
