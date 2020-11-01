package com.gitee.ywj1352.inbound;

import com.gitee.ywj1352.filter.HttpRequestFilter;
import com.gitee.ywj1352.outbound.HttpOutboundHandler;
import com.google.common.collect.Collections2;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
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
            //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            //使用 filter 执行
            doFilter(fullRequest,ctx);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void doFilter(FullHttpRequest fullRequest,ChannelHandlerContext ctx){
        if (requestFilters!=null &&  requestFilters.size() > 0){
            for (HttpRequestFilter requestFilter:requestFilters) {
                requestFilter.filter(fullRequest,ctx);
            }
        }
    }

}
