package com.gitee.ywj1352.core;

import com.gitee.ywj1352.Server;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpExecutor {

    private static Logger logger = LoggerFactory.getLogger(HttpExecutor.class);

    /**
     * @param httpHost
     * @param httpRequest ,GET/POST/PUT/DELETE
     * @return
     */
    public static HttpResponse doQuest(HttpHost httpHost, HttpRequest httpRequest) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            CloseableHttpResponse execute = httpclient.execute(httpHost, httpRequest);
            return execute;
        } catch (Throwable e) {
            logger.error("do get http call has error and the host is {}", httpHost.getHostName(), e);
        }
        return null;
    }
}
