package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Responese;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public interface HttpResponseFilter {
    void filter(Responese responese, ChannelHandlerContext ctx, ChannelPromise promise);
}
