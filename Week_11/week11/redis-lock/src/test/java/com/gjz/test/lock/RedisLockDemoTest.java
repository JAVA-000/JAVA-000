package com.gjz.test.lock;

import com.gjz.test.redis.lock.service.RedisLockService;
import com.gjz.test.redis.lock.service.impl.JedisLockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * Redis实现分布式锁测试
 * </pre>
 *
 * @author
 * @version 1.00.00
 * @createDate 2021/1/6 15:30
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Slf4j
@SpringBootTest(classes = JedisLockServiceImpl.class)
@RunWith(SpringRunner.class)
public class RedisLockDemoTest {

    @Resource(name = "jedisLockService")
    RedisLockService redisLockService;

    /**
     * 测试redis分布式锁
     */
    @Test
    public void testJedisLock() {

        String key = "demo";

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int no = i;
            executorService.execute(()->{
                log.info("模拟处理业务，当前线程：{}.......", Thread.currentThread().getName());
                if (redisLockService.lock(key, 10) != null) {
                    log.info("模拟处理业务, 取得锁，可以执行业务， 当前线程：{}", Thread.currentThread().getName());
                    try {
                        for (int j = 0; j < 5; j++) {
                            Thread.sleep(1000);
                            log.info("模拟处理业务, 当前线程：{}, 正在执行任务......", Thread.currentThread().getName());
                        }
                    }catch (Exception e){
                        log.error("模拟处理业务, 业务异常，");
                    }finally {
                        redisLockService.unlock(key);
                    }
                }else {
                    log.info("模拟处理业务, 未取得锁，不能执行业务， 当前线程：{}", Thread.currentThread().getName());
                }
            });

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}

