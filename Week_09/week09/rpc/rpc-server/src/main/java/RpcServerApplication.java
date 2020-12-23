import com.alibaba.fastjson.JSON;
import com.gjz.test.rpc.api.entitys.RpcfxRequest;
import com.gjz.test.rpc.api.entitys.RpcfxResponse;
import com.gjz.test.rpc.api.entitys.User;
import com.gjz.test.rpc.api.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @PostMapping("/")
    public RpcfxResponse call(@RequestBody RpcfxRequest request){

        // 获取rpc调用的类
        String serviceClass = request.getServiceClass();

        // 获取实例
        UserService userService = (UserService) this.applicationContext.getBean(serviceClass);

        Integer id = Integer.valueOf(request.getParams()[0].toString());

        User user = userService.getUser(Long.valueOf(id));

        RpcfxResponse response = new RpcfxResponse();
        response.setStatus(true);
        response.setResult(JSON.);

        return response;


    }
}

