package com.example.demo.redis;

import java.util.concurrent.TimeUnit;

/**
 * 声明redis分布式锁接口
 */
public interface RedisLock {

    /**
     * 获取锁
     * @param key
     * @param timeOut
     * @param timeUnit
     * @return
     */
    boolean tryLock(String key, long timeOut, TimeUnit timeUnit);

    /**
     * 释放锁
     * @param key
     */
    void releaseLock(String key);

}
