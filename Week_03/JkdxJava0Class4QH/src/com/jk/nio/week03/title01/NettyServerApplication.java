package com.jk.nio.week03.title01;

import com.jk.nio.week03.title01.inbound.HttpInboundServer;

public class NettyServerApplication {


    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
	  
    public static void main(String[] args) {
        String proxyPort = System.getProperty("proxyPort","8888");
        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        HttpInboundServer server = new HttpInboundServer(port);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:");
        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
	
	
}
