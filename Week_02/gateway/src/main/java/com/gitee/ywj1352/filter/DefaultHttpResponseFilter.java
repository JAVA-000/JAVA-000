package com.gitee.ywj1352.filter;

import com.gitee.ywj1352.core.pojo.Request;
import com.gitee.ywj1352.utils.HttpExecutor;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class DefaultHttpResponseFilter implements HttpResponseFilter{
    private static Logger logger = LoggerFactory.getLogger(DefaultHttpResponseFilter.class);

    @Override
    public void filter(Request fullRequest, ChannelHandlerContext ctx, ChannelPromise promise) {
        HttpHost httpHost = new HttpHost("127.0.0.1",8088);
        HttpGet httpGet = new HttpGet("/api/hello");
        HttpResponse httpResponse = HttpExecutor.doQuest(httpHost, httpGet);
        FullHttpResponse response = null;
        try {
            InputStream content = httpResponse.getEntity().getContent();
            byte[] bytes = IOUtils.toByteArray(content);
            content.close();
            logger.info("远端 response : {} , and header", new String(bytes));
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytes));
            logger.info("创建 response : {} , and header", response);
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        } catch (Exception e) {
            logger.error("处理测试接口出错", e);
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                logger.info("返回值为 : {} , and header", response);
                response.headers().set(CONNECTION, KEEP_ALIVE);
                ctx.writeAndFlush(response);
            }
        }
    }

}
