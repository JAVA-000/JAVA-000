import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gjz.test.core.api.RpcfxRequest;
import com.gjz.test.core.api.RpcfxResponse;
import com.gjz.test.rpc.api.entitys.User;
import com.gjz.test.rpc.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <pre>
 * RPC服务类
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2020/12/22 下午11:29
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@SpringBootApplication
@ComponentScan("com.gjz.test.*")
@RestController
@Slf4j
public class RpcServerApplication implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(RpcServerApplication.class, args);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * RPC底层通信
     * @param request
     * @return
     */
    @PostMapping("/request")
    public RpcfxResponse call(@RequestBody RpcfxRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        log.info("rpc http 调用------");
        // 获取rpc调用的类
        String serviceClass = request.getServiceClass();

        // 获取实例
        Object object = this.applicationContext.getBean(serviceClass);

        // 获取方法
        Method method = object.getClass().getMethod(request.getMethod());
        Object result = method.invoke(object, request.getParams());

        RpcfxResponse response = new RpcfxResponse();
        response.setStatus(true);
        response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
        return response;


    }
}

