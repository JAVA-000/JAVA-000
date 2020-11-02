package com.gitee.ywj1352.handler.outbound;

import com.gitee.ywj1352.core.pojo.Responese;
import com.gitee.ywj1352.filter.HttpResponseFilter;
import com.gitee.ywj1352.handler.inbound.HttpInboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpOutboundHandler extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private List<HttpResponseFilter> responseFilters;

    public HttpOutboundHandler(List<HttpResponseFilter> responseFilters) {
        this.responseFilters = responseFilters;
    }

    public HttpOutboundHandler() {
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){
        Responese responese = (Responese) msg;
        doFilter(responese, ctx, promise);
    }

    private void doFilter(Responese responese, ChannelHandlerContext ctx, ChannelPromise promise) {
        if (responseFilters != null && responseFilters.size() > 0) {
            for (HttpResponseFilter requestFilter : responseFilters) {
                requestFilter.filter(responese, ctx, promise);
            }
        }
    }

}
