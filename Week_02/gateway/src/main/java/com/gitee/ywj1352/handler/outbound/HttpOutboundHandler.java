package com.gitee.ywj1352.handler.outbound;

import com.gitee.ywj1352.core.pojo.Request;
import com.gitee.ywj1352.filter.HttpRequestFilter;
import com.gitee.ywj1352.filter.HttpResponseFilter;
import com.gitee.ywj1352.handler.inbound.HttpInboundHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpOutboundHandler extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private List<HttpResponseFilter> responseFilters;

    public HttpOutboundHandler(List<HttpResponseFilter> responseFilters) {
        this.responseFilters = responseFilters;
    }

    public HttpOutboundHandler() {
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(msg.getClass());
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("wo de ".getBytes("UTF-8")));
        logger.info("创建 response : {} , and header", response);
        response.headers().set("Content-Type", "application/json");
        response.headers().setInt("Content-Length", response.content().readableBytes());
        logger.info("返回值为 : {} , and header", response);
        response.headers().set(CONNECTION, KEEP_ALIVE);
        super.write(ctx, response, promise);
    }

    private void doFilter(Request fullRequest, ChannelHandlerContext ctx, ChannelPromise promise){
        if (responseFilters!=null &&  responseFilters.size() > 0){
            for (HttpResponseFilter requestFilter:responseFilters) {
                requestFilter.filter(fullRequest,ctx, promise);
            }
        }
    }

}
