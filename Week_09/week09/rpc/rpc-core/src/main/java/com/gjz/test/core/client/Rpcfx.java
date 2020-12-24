package com.gjz.test.core.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.gjz.test.core.api.RpcfxRequest;
import com.gjz.test.core.api.RpcfxResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * <pre>
 * rpc服务
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/23 下午9:18
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Slf4j
public class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("com.gjz.test.rpc");
    }

    /**
     *
     * @param tClass
     * @param serverUrl
     * @return
     */
    public static <T> T create(Class<T> tClass, String serverUrl) {
        return (T)Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{tClass}, new RpcfxInvocationHandler(tClass, serverUrl));
    }

    /**
     * rpc客户端代理
     */
    public static class RpcfxInvocationHandler implements InvocationHandler{


        public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

        private final Class tClass;

        private final String serverUrl;

        public <T> RpcfxInvocationHandler(Class<T> tClass, String serverUrl) {
            this.tClass = tClass;
            this.serverUrl = serverUrl;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("rpc客户端代理，invoke, method:{}, args:{}", method, args);
            RpcfxRequest rpcfxRequest = new RpcfxRequest();
            rpcfxRequest.setMethod(method.getName());
            rpcfxRequest.setParams(args);
            rpcfxRequest.setServiceClass(tClass.getName());

            RpcfxResponse rpcfxResponse = post(rpcfxRequest, serverUrl);

            return JSON.parse(Objects.toString(rpcfxResponse.getResult()));
        }

        /**
         * 底层发送请求
         * @param rpcfxRequest
         * @param serverUrl
         * @return
         */
        private RpcfxResponse post(RpcfxRequest rpcfxRequest, String serverUrl) throws IOException {
            String requestJson = JSON.toJSONString(rpcfxRequest);

            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(serverUrl)
                    .post(RequestBody.create(JSONTYPE, requestJson))
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            String responseJson = response.body().string();

            return JSON.parseObject(responseJson, RpcfxResponse.class);
        }
    }
}

