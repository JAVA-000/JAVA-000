package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

import static io.kimmking.rpcfx.client.Rpcfx.RpcfxInvocationHandler.JSONTYPE;

@Aspect
@Component
public class RpcfxAspect {

    @Pointcut("execution(* io.kimmking.rpcfx.client.Rpcfx.create())")
    public void createMethodPoint() {
    }

    @Around("createMethodPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Method method = joinPointObject.getMethod();
        String methodName = method.getName();
        String className = method.getDeclaringClass().getInterfaces()[0].getName();

        Object[] args = joinPoint.getArgs();
//        return this.invoke(className, methodName, args, url);
        return null;
    }

    public Object invoke(String className, String methodName, Object[] args, String url) throws Throwable {
        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(className);
        request.setMethod(methodName);
        request.setParams(args);

        RpcfxResponse response = post(request, url);

        // 这里判断response.status，处理异常
        // 考虑封装一个全局的RpcfxException
        if (!response.isStatus()) {
            response.getException();
        }
        return JSON.parse(response.getResult().toString());
    }

    private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: " + reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();
        System.out.println("resp json: " + respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }
}
