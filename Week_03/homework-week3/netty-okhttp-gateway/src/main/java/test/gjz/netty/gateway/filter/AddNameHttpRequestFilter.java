package test.gjz.netty.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.ipfilter.IpSubnetFilter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 添加姓名Http请求过滤器
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/4 14:12
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class AddNameHttpRequestFilter extends ChannelInboundHandlerAdapter implements HttpRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AddNameHttpRequestFilter.class);

    private final String LOGGER_HEAD = "[添加姓名Http请求过滤器]";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("{}, channelRead, 进入AddNameHttpRequestFilter过滤器" ,LOGGER_HEAD);
        try {
            FullHttpRequest request = ((FullHttpRequest) msg).copy();
            filter(request, ctx);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.fireChannelRead(msg);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 过滤方法
     *
     * @param fullRequest
     * @param ctx
     */
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().add("nio", getCurrentUserName());
        ctx.fireChannelRead(fullRequest);
    }

    /**
     * 获取当前用户姓名
     * @return
     */
    private String getCurrentUserName() {
        return System.getProperty("userName", "guojiazhen");
    }

}

