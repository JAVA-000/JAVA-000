package com;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okhttp3.internal.Util;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpClientPool {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static int maxRequests = 300;
    private static int maxRequestsPerHost = 30;
    private static int connectTimeout = 10000;
    private static int readTimeout = 30000;
    private static int writeTimeout = 30000;
    private static volatile OkHttpClientPool INSTANCE;
    private static volatile OkHttpClient CLIENT;
    private OkHttpClientPool(){}
    public static OkHttpClientPool getInstance() {
        if(INSTANCE == null){
            //构建对象
            INSTANCE = new OkHttpClientPool();
            //构建连接对象
            CLIENT = buildOkHttpClient();
        }
        return INSTANCE;
    }

    public static OkHttpClientPool getInstance(ClientProperties clientProperties){
        if(INSTANCE == null){
            //构建对象
            INSTANCE = new OkHttpClientPool();
            maxRequests = clientProperties.getMaxRequests();
            maxRequestsPerHost = clientProperties.getMaxRequestsPerHost();
            connectTimeout = clientProperties.getConnectTimeout();
            readTimeout = clientProperties.getReadTimeout();
            writeTimeout = clientProperties.getWriteTimeout();
            //构建连接对象
            CLIENT = buildOkHttpClient();
        }
        return INSTANCE;
    }


    private static OkHttpClient buildOkHttpClient() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(maxRequests);
        dispatcher.setMaxRequestsPerHost(maxRequestsPerHost);
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .dispatcher(dispatcher)
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
                .connectionSpecs(Util.immutableList(new ConnectionSpec[]{ConnectionSpec.CLEARTEXT}))
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout,TimeUnit.MILLISECONDS)
                .build();
    }
    public String doPost(String url, Map paramMap) throws Exception {
        Call call = buildCall(url, paramMap);
        if (call == null) {
            return null;
        }
        Response response = call.execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new Exception("调用失败");
        }
    }
    private Call buildCall(String url, Map paramMap) {
        RequestBody requestBody = RequestBody.create(JSON, JSONObject.toJSON(paramMap).toString());
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody);
        Request request = builder.build();
        return CLIENT.newCall(request);
    }
}
