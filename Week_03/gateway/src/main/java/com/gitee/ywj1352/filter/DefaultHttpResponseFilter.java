package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHttpResponseFilter implements HttpResponseFilter {
    private static Logger logger = LoggerFactory.getLogger(DefaultHttpResponseFilter.class);

    @Override
    public void filter(Response responese, ChannelHandlerContext ctx, ChannelPromise promise) {
        FullHttpResponse response = responese.getFullHttpResponse();
        response.headers().set("Content-Type", "application/json");
        ctx.writeAndFlush(response, promise);
    }

}
