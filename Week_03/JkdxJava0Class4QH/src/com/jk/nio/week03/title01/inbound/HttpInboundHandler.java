package com.jk.nio.week03.title01.inbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jk.nio.week03.title01.filter.FilterFactor;
import com.jk.nio.week03.title01.filter.HttpRequestFilter;
import com.jk.nio.week03.title01.filter.HttpRequestFilterDefImp;
import com.jk.nio.week03.title01.filter.HttpRequestFilterDocImp;
import com.jk.nio.week03.title01.outbound.httpclient4.HttpOutboundHandler;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
//    private final String proxyServer;
    private HttpOutboundHandler handler;
    
   
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
        	
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String ttt= fullRequest.getUri();
            System.out.println(ttt);
            if(ttt.split("/").length>2) {
            	ttt = ttt.substring(1, ttt.length());
            	ttt = ttt.substring(0, ttt.indexOf("/"));
            }
            else {
            	ttt = ttt.substring(1, ttt.length());
            }
            
            
            HttpRequestFilter filter_ =   FilterFactor.filterFactor(ttt);
           
            
            filter_.filter(fullRequest, ctx);
            
            io.netty.handler.codec.http.HttpHeaders httpHeaders = fullRequest.headers();
            
            String requestUrl = httpHeaders.get(HttpRequestFilter.MODEL_CODE);
            
            handler = new HttpOutboundHandler(requestUrl);
            
            handler.handle(fullRequest, ctx);
    
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
