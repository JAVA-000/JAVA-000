package test.gjz.netty.gateway.http.client.netty.server;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;

public class NettyServerApplication {

    public static void main(String[] args) {
        HttpServer server = new HttpServer(false,8808);

        //开启日志
        BasicConfigurator.configure();

        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
