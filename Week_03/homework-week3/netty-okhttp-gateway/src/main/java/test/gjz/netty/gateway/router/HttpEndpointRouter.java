package test.gjz.netty.gateway.router;

import java.util.List;

/**
 *
 * <pre>
 * 路由
 * </pre>
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/4
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface HttpEndpointRouter {

    /**
     * 路由方法
     * @param endpoints
     * @return
     */
    String route(List<String> endpoints);
    
}
