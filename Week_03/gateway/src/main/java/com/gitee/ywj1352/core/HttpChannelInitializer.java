package com.gitee.ywj1352.core;

import com.gitee.ywj1352.filter.DefaultHttpResponseFilter;
import com.gitee.ywj1352.filter.HttpClientAsyncRequestFilter;
import com.gitee.ywj1352.filter.NioHeaderFilter;
import com.gitee.ywj1352.filter.RouterHttpRequestFilter;
import com.gitee.ywj1352.handler.inbound.HttpInboundHandler;
import com.gitee.ywj1352.handler.outbound.HttpOutboundHandler;
import com.gitee.ywj1352.router.RandomRouter;
import com.gitee.ywj1352.utils.HttpClientExecutor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Arrays;

public class HttpChannelInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 业务 handler 设置
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast(new HttpOutboundHandler(Arrays.asList(new DefaultHttpResponseFilter())));
        pipeline.addLast(new HttpInboundHandler(Arrays.asList(new RouterHttpRequestFilter(new RandomRouter()), new NioHeaderFilter(), new HttpClientAsyncRequestFilter(new HttpClientExecutor()))));
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
    }
}
