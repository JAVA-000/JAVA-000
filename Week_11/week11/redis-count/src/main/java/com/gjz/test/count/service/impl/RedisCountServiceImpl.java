package com.gjz.test.count.service.impl;

import com.gjz.test.count.service.RedisCountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;

/**
 * <pre>
 * Redis计数器
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/6 16:44
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Slf4j
@Service(value = "redisCountService")
public class RedisCountServiceImpl implements RedisCountService {
    private static final String LOGGER_HEAD = "【 Redis计数器 】";
    private static final Logger logger = LoggerFactory.getLogger(RedisCountServiceImpl.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 初始化计数器的初始值
     *
     * @param key
     * @param value
     */
    @Override
    public void initValue(String key, Long value) {
        stringRedisTemplate.boundValueOps(key).setIfAbsent(String.valueOf(value));
    }

    /**
     * 对key内容，进行加减计数
     * 正数为加，负数为减
     *
     * @param key
     */
    @Override
    public Long increment(final String key, final Long value) {
        return  stringRedisTemplate.boundValueOps(key).increment(value);
    }

    /**
     * 获取key的当前值
     *
     * @param key
     * @return
     */
    @Override
    public String getCurrentValue(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }

}

