package com.gjz.test.count.service;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * <pre>
 * Redis计数器
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/6 16:42
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface RedisCountService {

    /**
     * 初始化计数器的初始值
     * @param key
     * @param value
     */
    void initValue(final String key, final Long value);

    /**
     * 对key内容，进行加减计数
     * 正数为加，负数为减
     * @param key
     */
    Long increment(final String key, final Long value);


    /**
     * 获取key的当前值
     * @param key
     * @return
     */
    String getCurrentValue(final String key);
}
