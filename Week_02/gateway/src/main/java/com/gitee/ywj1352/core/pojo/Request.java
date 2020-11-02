package com.gitee.ywj1352.core.pojo;


import io.netty.handler.codec.http.FullHttpRequest;

public class Request {
    private String serverId;
    private String url;
    private FullHttpRequest request;

    public Request(FullHttpRequest request) {
        this.request = request;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }
}
