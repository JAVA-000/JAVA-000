package com.gitee.ywj1352.utils;

import com.gitee.ywj1352.core.pojo.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class Netty4ClientExecutor extends HttpExecutor {

    private EventLoopGroup workerGroup;
    private Bootstrap bossGroup;
    private ChannelFuture f;

    private ConcurrentHashMap<String,ChannelHandlerContext> clientLinkServer = new ConcurrentHashMap();

    public Netty4ClientExecutor() {
        this.workerGroup = new NioEventLoopGroup(4);
        this.bossGroup = new Bootstrap();
        bossGroup.group(workerGroup);
        bossGroup.channel(NioSocketChannel.class);
        bossGroup.option(ChannelOption.SO_KEEPALIVE, true);
        bossGroup.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                ch.pipeline().addLast(new HttpResponseDecoder());
                // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                ch.pipeline().addLast(new HttpRequestEncoder());
                ch.pipeline().addLast(new HttpClientInboundHandler());
            }
        });
    }


    public void fun1() throws Exception {
    }

    @Override
    protected void doFetchGet(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {
        try {
            URI uri = new URI(url);
            f = bossGroup.connect(uri.getHost(), uri.getPort()).sync();

            String msg = "Are you ok?";
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));

            // 构建http请求
            request.headers().set(HttpHeaders.Names.HOST, uri.getHost());
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            Channel channel = f.channel();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        workerGroup.shutdownGracefully();
    }


    static class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

        public HttpClientInboundHandler() {
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpResponse) {
                HttpResponse response = (HttpResponse) msg;
                System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
            }
            if (msg instanceof HttpContent) {
                HttpContent content = (HttpContent) msg;
                ByteBuf buf = content.content();
                System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
                buf.release();
            }
        }
    }

    @Override
    protected void doFetchDeleted(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {

    }

    @Override
    protected void doFetchPost(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {

    }

    @Override
    protected void doFetchPut(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {

    }
}
