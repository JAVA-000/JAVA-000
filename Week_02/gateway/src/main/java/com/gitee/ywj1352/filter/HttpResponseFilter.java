package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpResponseFilter {
    void filter(Request fullRequest, ChannelHandlerContext ctx, ChannelPromise promise);
}
