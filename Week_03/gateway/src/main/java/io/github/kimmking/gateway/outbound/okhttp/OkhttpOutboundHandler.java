package io.github.kimmking.gateway.outbound.okhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @Description myOutboundHandler
 * @Author qifeng.qxf
 * @Date 0:55 2020/11/5
 **/
public class OkhttpOutboundHandler {

    private static Logger logger = LoggerFactory.getLogger(OkhttpOutboundHandler.class);
    private OkHttpClient httpclient;
    private String backendUrl;

    public OkhttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;
        //异步,缓存,多线程,超时等参数配置暂不设置,采用默认配置仅实现最小可行功能
        httpclient = new OkHttpClient().newBuilder().build();
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = this.backendUrl + fullRequest.uri();
        fetchGet(fullRequest, ctx, url);
    }

    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {

        Request request = new Request.Builder()
                .get()
                .url("http://localhost:8088/api/hello")
                .build();
        try {
            Response response = httpclient.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            handleResponse(inbound, ctx, response);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Response endpointResponse) {
        FullHttpResponse response = null;
        try {
            String myResponseBody = Objects.requireNonNull(endpointResponse.body()).string().replace("hello world", "hello qingyi.qxf");
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(myResponseBody.getBytes()));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Long.valueOf(myResponseBody.length()).intValue());
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(endpointResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
