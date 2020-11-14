package com.jk.nio.week03.title01.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HttpRequestFilter {
    
	public static String  MODEL_CODE="mcode";
	
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);
    
}
