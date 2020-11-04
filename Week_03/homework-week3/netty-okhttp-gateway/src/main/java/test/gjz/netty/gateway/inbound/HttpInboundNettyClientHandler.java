package test.gjz.netty.gateway.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.gjz.netty.gateway.outbound.netty4.NettyHttpClientOutBoundHandler;
import test.gjz.netty.gateway.outbound.okhttp.OkHttpOutboundHandler;

/**
 *
 * <pre>
 * 使用Netty客户端处理请求
 * </pre>
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/4
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HttpInboundNettyClientHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundNettyClientHandler.class);
    private final String proxyServer;

    private NettyHttpClientOutBoundHandler handler;

    private final String LOGGER_HEAD = "[使用Netty客户端处理请求]";

    public HttpInboundNettyClientHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        handler = new NettyHttpClientOutBoundHandler(this.proxyServer);
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
            handler.handle(ctx, fullRequest.copy());
            return;
    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
