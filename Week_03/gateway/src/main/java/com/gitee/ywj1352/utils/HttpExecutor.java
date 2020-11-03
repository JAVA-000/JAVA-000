package com.gitee.ywj1352.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

public abstract class HttpExecutor {

    public void fetchRequest(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url){
        HttpMethod method = inbound.method();
        switch (method.name()){
            case "GET" :
                doFetchGet(inbound, ctx, url);
                break;
            case "PUT" :
                doFetchPut(inbound, ctx, url);
                break;
            case "POST" :
                doFetchPost(inbound, ctx, url);
                break;
            case "DELETED" :
                doFetchDeleted(inbound, ctx, url);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + method.name());
        }

    }

    protected abstract void doFetchDeleted(FullHttpRequest inbound, ChannelHandlerContext ctx, String url);
    protected abstract void doFetchPost(FullHttpRequest inbound, ChannelHandlerContext ctx, String url);
    protected abstract void doFetchPut(FullHttpRequest inbound, ChannelHandlerContext ctx, String url);
    protected abstract void doFetchGet(FullHttpRequest inbound, ChannelHandlerContext ctx, String url);
}
