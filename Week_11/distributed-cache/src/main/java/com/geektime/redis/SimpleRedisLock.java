package com.geektime.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SimpleRedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

//    public static final String SCRIPT_UNLOCK = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


    /**
     * @return boolean 是否获取成功
     * @Description 尝试获取锁
     * @Author qifeng.qxf
     * @Param [lockKey-锁, expireTime-超时时间]
     **/
    public Boolean tryLock(String lockKey, long expireTime) {
        String uuid = UUID.randomUUID().toString();
        threadLocal.set(uuid);
        log.info("threadLocal:::{}", uuid);
        return stringRedisTemplate.opsForValue().setIfAbsent(lockKey, uuid, expireTime, TimeUnit.SECONDS);
    }

    /**
     * @return void
     * @Description 释放锁
     * @Author qifeng.qxf
     * @Date 9:33 2021/1/7
     * @Param [lockKey]
     **/
    public void releaseLock(String lockKey) {
        log.info("threadLocal:::{}", threadLocal.get());
        String s = stringRedisTemplate.opsForValue().get(lockKey);
        try {
            if (threadLocal.get().equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                stringRedisTemplate.delete(lockKey);
            }
        } finally {
            threadLocal.remove();
        }
    }
}
