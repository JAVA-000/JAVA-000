package com.gjz.test.redis.lock.service;

/**
 * <pre>
 * 锁服务
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/6 10:54
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface RedisLockService {

    /**
     * 上锁
     * @param key
     * @param sec
     * @return
     */
    String lock(final String key, final Integer sec);

    /**
     * 解锁
     * @param key
     * @return
     */
    void unlock(final String key);
}
