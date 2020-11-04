package test.gjz.netty.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.gjz.netty.gateway.outbound.okhttp.OkHttpOutboundHandler;

public class HttpInboundOkhttpHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundOkhttpHandler.class);
    private final String proxyServer;

    private OkHttpOutboundHandler handler;

    private final String LOGGER_HEAD = "HttpInboundHandler";

    public HttpInboundOkhttpHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        handler = new OkHttpOutboundHandler(this.proxyServer);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        logger.info("{}, channelReadComplete, " ,LOGGER_HEAD);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("{}, channelRead, ------->请求参数：{}" ,LOGGER_HEAD, msg);
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            handler.handle(ctx, fullRequest);
            return;
    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
