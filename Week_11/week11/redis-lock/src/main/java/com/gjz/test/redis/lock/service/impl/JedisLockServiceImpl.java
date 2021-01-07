package com.gjz.test.redis.lock.service.impl;

import com.gjz.test.redis.lock.service.RedisLockService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * <pre>
 * 使用Jedis连接Redis实现分布式锁
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/6 14:30
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Slf4j
@Service("jedisLockService")
public class JedisLockServiceImpl implements RedisLockService {
    private static final String LOGGER_HEAD = "【 使用Jedis连接Redis实现分布式锁 】";
    private static final Logger logger = LoggerFactory.getLogger(JedisLockServiceImpl.class);

    @Value("${cache.redis.host}")
    private String host;
    @Value("${cache.redis.port}")
    private Integer port;
    @Value("${cache.redis.auth}")
    private String auth;


    @Autowired
    Jedis jedis;


    @Bean("jedis")
    public Jedis initJedis(){
        Jedis jedisTemp = new Jedis(host, port);
        jedisTemp.auth(auth);
        return jedisTemp;
    }

    /**
     * 上锁
     *
     * @param key
     * @param sec
     * @return
     */
    @Override
    public String lock(String key, Integer sec) {
        return jedis.set(key, "lock", "NX", "EX", sec);
    }

    /**
     * 解锁
     *
     * @param key
     * @return
     */
    @Override
    public void unlock(String key) {
        jedis.del(key);
    }
}

