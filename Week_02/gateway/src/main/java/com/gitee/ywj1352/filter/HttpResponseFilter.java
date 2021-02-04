package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface HttpResponseFilter {
    void filter(Response response, ChannelHandlerContext ctx, ChannelPromise promise);
}
