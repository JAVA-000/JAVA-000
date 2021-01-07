package com.gjz.test.redispubsub.web;

import com.gjz.test.redispubsub.service.RedisPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 订单Controller
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/7 15:14
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    RedisPublishService<String> redisPublishService;

    @RequestMapping(value = "/place")
    void placeOrder(String message){
        redisPublishService.publish("order", message);
    }
}
