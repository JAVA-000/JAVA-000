package test.gjz.netty.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 *
 * <pre>
 * Http请求过滤器
 * </pre>
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/4
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface HttpRequestFilter {

    /**
     * 过滤方法
     * @param fullRequest
     * @param ctx
     */
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);
    
}
