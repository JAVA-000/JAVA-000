package com.gitee.ywj1352.handler.inbound;

import com.gitee.ywj1352.core.pojo.Request;
import com.gitee.ywj1352.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private List<HttpRequestFilter> requestFilters;

    public HttpInboundHandler(List<HttpRequestFilter> requestFilters) {
        this.requestFilters = requestFilters;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            Request request = new Request(fullRequest);
            doFilter(request, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void doFilter(Request fullRequest, ChannelHandlerContext ctx) {
        if (requestFilters != null && requestFilters.size() > 0) {
            for (HttpRequestFilter requestFilter : requestFilters) {
                requestFilter.filter(fullRequest, ctx);
            }
        }
    }

}
