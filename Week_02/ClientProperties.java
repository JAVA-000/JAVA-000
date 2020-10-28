package com;

public class ClientProperties {
    /**全部地址最大并发数量*/
    private  int maxRequests = 300;
    /**单个地址最大并发数量*/
    private  int maxRequestsPerHost = 30;
    /**连接超时时间（单位毫秒）*/
    private  int connectTimeout = 3000;
    /**读取超时时间（单位毫秒）*/
    private  int readTimeout = 3000;
    /**写入超时时间（单位毫秒）*/
    private  int writeTimeout = 30000;

    public int getMaxRequests() {
        return maxRequests;
    }

    public void setMaxRequests(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    public int getMaxRequestsPerHost() {
        return maxRequestsPerHost;
    }

    public void setMaxRequestsPerHost(int maxRequestsPerHost) {
        this.maxRequestsPerHost = maxRequestsPerHost;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
}
