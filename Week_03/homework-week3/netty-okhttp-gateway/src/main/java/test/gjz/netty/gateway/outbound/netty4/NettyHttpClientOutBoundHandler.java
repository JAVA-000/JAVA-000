package test.gjz.netty.gateway.outbound.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import test.gjz.netty.gateway.factory.NamedThreadFactory;

import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static java.util.regex.Pattern.compile;

/**
 * <pre>
 * 客户端处理
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
public class NettyHttpClientOutBoundHandler {


    private String serverUrl;
    private int port;
    private ExecutorService proxyService;

    private NettyHttpClient nettyHttpClient;

    public NettyHttpClientOutBoundHandler(String serverUrl) {
        this.port = getPort(serverUrl);
        this.serverUrl = serverUrl;


        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        //.DiscardPolicy();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);

        nettyHttpClient = new NettyHttpClient(serverUrl, port);
    }

    /**
     * 从地地中取出端口号
     * @param url
     * @return
     */
    private int getPort(String url){

        Pattern p = compile("(http|https):\\/\\/([a-zA-Z1-9]?[a-zA-Z0-9\\.]+)(:(\\d+))?");
        Matcher m = p.matcher(url);

        String value = "80";
        if (m.find()) {
            value = m.group(4);
        }

        return Integer.valueOf(value).intValue();
    }



    /**
     * 处理请求
     * @param ctx
     * @param fullRequest
     */
    public void handle(final ChannelHandlerContext ctx, final FullHttpRequest fullRequest) {
        proxyService.submit(()->nettyHttpClient.clientHandle(ctx, fullRequest));
    }


    /**
     * 处理服务端返回
     * @throws Exception
     */
    public void handleResponse(final ChannelHandlerContext ctx, final FullHttpResponse fullHttpResponse){

        try {
            ctx.writeAndFlush(fullHttpResponse);
        } catch (Exception e) {
            e.printStackTrace();
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ctx.writeAndFlush(response);
        }

    }
}

