package com.gjz.test.count.web;

import com.gjz.test.count.service.RedisCountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * Redis Controller
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/7 8:54
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@RestController
@RequestMapping(value = "/redis/count")
@Slf4j
public class RedisCountDemoController {

    @Resource(name = "redisCountService")
    RedisCountService redisCountService;

    private final String STOCK_KEY = "stock";

    @RequestMapping(value = "test")
    public void testRedisCountService(){
        redisCountService.initValue(STOCK_KEY, 18L);

        String initValue = redisCountService.getCurrentValue(STOCK_KEY);
        log.info("仓库初始容量为:{}", initValue);

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            final int no = i;
            executorService.execute(()->{
                log.info("模拟处理业务，当前线程：{}.......", Thread.currentThread().getName());
                Long afterValue = redisCountService.increment(STOCK_KEY, -1L);
                if (afterValue < 0){
                    throw new RuntimeException("库存不足");
                }
            });
        }
        executorService.shutdown();

        log.info("仓库剩余容量为:{}", redisCountService.getCurrentValue(STOCK_KEY));
    }
}

