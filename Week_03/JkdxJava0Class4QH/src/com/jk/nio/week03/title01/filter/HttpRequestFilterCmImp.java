package com.jk.nio.week03.title01.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

public class HttpRequestFilterCmImp implements HttpRequestFilter{

	@Override
	public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		
		 HttpHeaders httpHeaders = fullRequest.headers();
		 httpHeaders.set(MODEL_CODE, "http://localhost:8080/cm");
		
	}

}
