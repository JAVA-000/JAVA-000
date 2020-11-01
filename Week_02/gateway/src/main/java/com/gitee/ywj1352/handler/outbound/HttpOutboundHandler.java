package com.gitee.ywj1352.outbound;

import com.gitee.ywj1352.filter.HttpRequestFilter;
import com.gitee.ywj1352.filter.HttpResponseFilter;
import com.gitee.ywj1352.inbound.HttpInboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

public class HttpOutboundHandler extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private List<HttpResponseFilter> responseFilters;

    public HttpOutboundHandler(List<HttpResponseFilter> responseFilters) {
        this.responseFilters = responseFilters;
    }

    public HttpOutboundHandler() {
    }




    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(msg.getClass());
        ctx.write(msg, promise);
    }



    private void doFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx){
    }


}
