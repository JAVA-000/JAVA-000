package test.gjz.netty.gateway.outbound.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.string.StringDecoder;
import test.gjz.netty.gateway.filter.AddNameHttpRequestFilter;

/**
 * <pre>
 * Netty实现Http客户端通道初始化
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
public class NettyHttpClientInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 访问服务器地址
     */
    private String serverUrl;
    private ChannelHandlerContext sourceContext;

    public NettyHttpClientInitializer(String serverUrl, ChannelHandlerContext sourceContext) {
        this.serverUrl = serverUrl;
        this.sourceContext = sourceContext;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpResponseDecoder())
                .addLast(new HttpRequestEncoder())
                .addLast(new HttpObjectAggregator(1024 * 1024))
                .addLast(new NettyHttpClientInboundHandler(serverUrl, sourceContext));
    }
}

