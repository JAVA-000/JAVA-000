package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.outbound.okhttp.OkhttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;
    /**
     * myOutboundHandler
     */
    private OkhttpOutboundHandler handler;

    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        handler = new OkhttpOutboundHandler(this.proxyServer);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            handler.handle(fullRequest, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }


}
