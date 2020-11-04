package test.gjz.netty.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.gjz.netty.gateway.filter.AddNameHttpRequestFilter;

/**
 *
 * <pre>
 * 通道初始化器
 * </pre>
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/2
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

	Logger LOGGER = LoggerFactory.getLogger(HttpInboundInitializer.class);
	private final String LOGGER_HEAD = "HttpInboundInitializer";
	
	private String proxyServer;
	
	public HttpInboundInitializer(String proxyServer) {
		this.proxyServer = proxyServer;
	}
	
	@Override
	public void initChannel(SocketChannel ch) {
		LOGGER.info("{}, initChannel, " ,LOGGER_HEAD);
		ChannelPipeline p = ch.pipeline();

		//http编解码处理
		p.addLast(new HttpServerCodec());
		//http请求body编解码处理
		p.addLast(new HttpObjectAggregator(1024 * 1024));

		//增加添加名字到请求头的过滤器
		p.addLast(new AddNameHttpRequestFilter());
		// http业务处理
		// 使用Okhttp进行转发请求
		//p.addLast(new HttpInboundOkhttpHandler(this.proxyServer));

		//使用Netty客户端进行转发请求
		p.addLast(new HttpInboundNettyClientHandler(this.proxyServer));

	}
}
