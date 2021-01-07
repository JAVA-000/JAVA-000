package com.gjz.test.redispubsub.service.impl;

import com.gjz.test.redispubsub.service.RedisPublishService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 发布String类型消息
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/7 14:29
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@Slf4j
@Service("redisPublishStringService")
public class RedisPublishStringServiceImpl implements RedisPublishService<String> {
    private static final String LOGGER_HEAD = "【 发布String类型消息 】";
    private static final Logger logger = LoggerFactory.getLogger(RedisPublishStringServiceImpl.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 发布消息
     *
     * @param message
     */

    @Override
    public void publish(final String topic, final String message) {
        log.info("{}, 发布消息....topic:{}, ", LOGGER_HEAD, topic);
        stringRedisTemplate.convertAndSend(topic, message);
    }
}


