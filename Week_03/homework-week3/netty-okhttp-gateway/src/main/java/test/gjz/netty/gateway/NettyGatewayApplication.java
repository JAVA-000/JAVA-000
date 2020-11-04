package test.gjz.netty.gateway;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.gjz.netty.gateway.inbound.HttpInboundServer;

/**
 *
 * <pre>
 * Netty网关服务主程序
 * </pre>
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/11/2
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NettyGatewayApplication {
    private final static Logger logger = LoggerFactory.getLogger(NettyGatewayApplication.class);

    private final String LOGGER_HEAD = "Netty网关服务主程序";

    public final static String GATEWAY_NAME = "NettyGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
    
    public static void main(String[] args) {
        // 获取代理服务器地址，参数值，缺省为localhost:8802
        String proxyServer = System.getProperty("proxyServer","http://localhost:8808");
        // 对外提供服务的端口， 缺省为9999
        String proxyPort = System.getProperty("proxyPort","9999");

        //开启日志配置
        PropertyConfigurator.configure("netty-okhttp-gateway/src/log4j.properties");
    
        int port = Integer.parseInt(proxyPort);
        logger.info("GATEWAY_NAME:{}, GATEWAY_VERSION:{}, starting...", GATEWAY_NAME, GATEWAY_VERSION);
        HttpInboundServer server = new HttpInboundServer(port, proxyServer);
        logger.info("GATEWAY_NAME:{}, GATEWAY_VERSION:{}, started at http://localhost:{}, for server:{}", GATEWAY_NAME, GATEWAY_VERSION, port, proxyServer);
        try {
            server.run();
        }catch (Exception ex){
            logger.error("Netty网关服务异常：", ex);
        }
    }
}
