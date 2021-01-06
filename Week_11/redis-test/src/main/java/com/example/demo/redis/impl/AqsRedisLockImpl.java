package com.example.demo.redis.impl;

import com.example.demo.redis.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式自旋锁实现
 */
public class AqsRedisLockImpl implements RedisLock {

    @Autowired
    private StringRedisTemplate template;

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();

    @Override
    public boolean tryLock(String key, long timeOut, TimeUnit timeUnit) {
        Boolean isLocked = false;
        if (null == threadLocal.get()) {
            String uuid = UUID.randomUUID().toString();
            threadLocal.set(uuid);
            isLocked = template.opsForValue().setIfAbsent(key, uuid, timeOut, timeUnit);
            if (!isLocked) {
                for (;;) {
                    isLocked = template.opsForValue().setIfAbsent(key, uuid, timeOut, timeUnit);
                    if (isLocked) {
                        break;
                    }
                }
            }
        } else {
            isLocked = true;
        }
        if (isLocked) {
            Integer count = integerThreadLocal.get() == null ? 0 : integerThreadLocal.get();
            integerThreadLocal.set(++count);
        }
        return isLocked;
    }

    @Override
    public void releaseLock(String key) {

        if (threadLocal.get().equals(template.opsForValue().get(key))) {
            Integer count = integerThreadLocal.get();
            if (null == count || --count <= 0) {
                template.delete(key);
            }
        }

    }






}
