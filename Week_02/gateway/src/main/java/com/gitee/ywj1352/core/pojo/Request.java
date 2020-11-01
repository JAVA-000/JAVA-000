package com.gitee.ywj1352.core.pojo;

import io.netty.handler.codec.http.HttpMethod;

public class Request {
    private String serverId;
    private String url;
    private HttpMethod httpMethod;


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

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }
}
