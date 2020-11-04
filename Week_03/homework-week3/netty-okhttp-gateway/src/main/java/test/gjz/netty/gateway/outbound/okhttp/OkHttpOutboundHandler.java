package test.gjz.netty.gateway.outbound.okhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.gjz.netty.gateway.factory.NamedThreadFactory;

import java.io.IOException;
import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 *
 * <pre>
 * 使用OkHttp对Http请求进行转发
 * </pre>
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/2
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class OkHttpOutboundHandler{
    private static final Logger logger = LoggerFactory.getLogger(OkHttpOutboundHandler.class);

    private final String LOGGER_HEAD = "[OkHttp转发请求]";

    private ExecutorService proxyService;
    private String backendUrl;


    public OkHttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl.endsWith("/")?backendUrl.substring(0,backendUrl.length()-1):backendUrl;

        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        //.DiscardPolicy();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);
    }

    /**
     * 处理请求
     * @param ctx
     * @param fullRequest
     */
    public void handle(final ChannelHandlerContext ctx, final FullHttpRequest fullRequest) {
        final String url = this.backendUrl + fullRequest.uri();
        proxyService.submit(()->handleGetRequest(fullRequest, ctx, url));
    }

    /**
     * 处理Get请求
     * @param fullHttpRequest
     * @param ctx
     * @param url
     */
    private void handleGetRequest(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx, final String url) {

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.error("转发http请求，异常：", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(fullHttpRequest, ctx, response);
            }
        });
    }

    /**
     * 处理响应信息
     * @param fullRequest
     * @param ctx
     * @param response
     */
    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final Response response){
        FullHttpResponse fullHttpResponse = null;
        try {
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(response.body().bytes()));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().set("Content-Length", String.valueOf(response.body().contentLength()));

        } catch (Exception e) {
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            logger.error("", e);
            ctx.close();
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.writeAndFlush(fullHttpResponse);
                }
            }
        }

    }
}

