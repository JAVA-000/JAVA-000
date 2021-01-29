package com.zenglh.io.gateway.outbound.httpclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class MyHttpOutboundHandler {

    private String backendUrl;

    public MyHttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl;
    }

    private void doPost(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {

        /*HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");*/
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
                .setConnectTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            handleResponse(inbound, ctx, response);
            /*StatusLine statusLine = response.getStatusLine();
            int status = statusLine.getStatusCode();
            System.out.println("请求响应状态: " + status);
            HttpEntity responseEntity = response.getEntity();
            if (null != responseEntity) {
                System.out.println(EntityUtils.toString(responseEntity));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = this.backendUrl + fullRequest.uri();
        doPost(fullRequest, ctx, url);
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx,
                                final HttpResponse endpointResponse) throws Exception {
        FullHttpResponse response = null;
        try {
          byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            System.out.println(new String(body));
            System.out.println(body.length);

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));

//            for (Header e : endpointResponse.getAllHeaders()) {
//                //response.headers().set(e.getName(),e.getValue());
//                System.out.println(e.getName() + " => " + e.getValue());
//            }

        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }

    }

    private void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
