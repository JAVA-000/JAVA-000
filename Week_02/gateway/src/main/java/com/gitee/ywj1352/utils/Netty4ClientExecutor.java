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
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.net.URISyntaxException;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class Netty4ClientExecutor extends HttpExecutor {

    @Override
    protected void doFetchGet(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bossGroup = new Bootstrap();
        bossGroup.group(workerGroup);
        bossGroup.channel(NioSocketChannel.class);
        bossGroup.option(ChannelOption.SO_KEEPALIVE, true);
        bossGroup.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new HttpResponseDecoder());
                ch.pipeline().addLast(new HttpRequestEncoder());
                ch.pipeline().addLast(new HttpObjectAggregator(1024 * 1024));
                ch.pipeline().addLast(new HttpClientInboundHandler(ctx));
            }
        });
        try {
            URI uri = new URI(url);
            ChannelFuture f = bossGroup.connect(uri.getHost(), uri.getPort()).sync();
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HTTP_1_1, HttpMethod.GET,
                    uri.toASCIIString());
            // 构建http请求
            request.setUri(inbound.uri());
            request.headers().set(HttpHeaders.Names.HOST, uri.getHost());
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }



    static class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

        private ChannelHandlerContext outctx;

        public HttpClientInboundHandler(ChannelHandlerContext outctx) {
            this.outctx = outctx;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            byte[] array = null;
            if (msg instanceof FullHttpResponse) {
                FullHttpResponse response = (FullHttpResponse) msg;
                System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
                ByteBuf buf = response.content();
                String string = buf.toString(CharsetUtil.UTF_8);
                array = string.getBytes("UTF-8");
                buf.release();
            }
            FullHttpResponse response = null;
            Response responese = null;
            try{
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(array));
                responese = new Response(response);
                response.headers().setInt("Content-Length", array.length);
            }catch (Exception e){


            }finally {
                outctx.writeAndFlush(responese);
                outctx.flush();
                //关闭 client 连接
                ctx.close();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
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
