package test.gjz.netty.gateway.outbound.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Netty实现Http客户端处理
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/3
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NettyHttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NettyHttpClientInboundHandler.class);

    private static final String LOGGER_HEAD = "[Netty实现Http客户端处理]";

    private NettyHttpClientOutBoundHandler handler;

    /**
     * 服务端地址
     */
    private String serverUrl;

    private ChannelHandlerContext sourceContext;

    public NettyHttpClientInboundHandler(String serverUrl, ChannelHandlerContext sourceContext) {
        this.serverUrl = serverUrl;
        handler = new NettyHttpClientOutBoundHandler(serverUrl);
        this.sourceContext = sourceContext;
    }

    /**
     * 响应时回调
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("{}, channelRead, 服务器{}, 已回应, 转发回去。。。。。" ,LOGGER_HEAD, serverUrl);

        FullHttpResponse fullHttpResponse = ((FullHttpResponse)msg).copy();
        try {
            handler.handleResponse(sourceContext, fullHttpResponse);
        }catch (Exception e){
            logger.error("处理响应数据，异常:", e);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}

