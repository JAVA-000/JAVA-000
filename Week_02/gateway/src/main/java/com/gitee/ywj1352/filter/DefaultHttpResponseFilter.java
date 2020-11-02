package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Responese;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHttpResponseFilter implements HttpResponseFilter{
    private static Logger logger = LoggerFactory.getLogger(DefaultHttpResponseFilter.class);

    @Override
    public void filter(Responese responese, ChannelHandlerContext ctx, ChannelPromise promise) {
    }

}
