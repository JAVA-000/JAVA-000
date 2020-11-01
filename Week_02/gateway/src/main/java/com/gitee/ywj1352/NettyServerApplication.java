package com.gitee.ywj1352;

import com.gitee.ywj1352.core.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerApplication {

    private static Logger logger = LoggerFactory.getLogger(NettyServerApplication.class);

    public static void main(String[] args) {
        HttpServer httpInboundServer = new HttpServer(9090);
        httpInboundServer.run();
        logger.info("start success!!");
    }

}