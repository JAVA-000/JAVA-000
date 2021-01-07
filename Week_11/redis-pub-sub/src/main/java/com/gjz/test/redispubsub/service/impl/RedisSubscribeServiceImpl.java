package com.gjz.test.redispubsub.service.impl;

import com.gjz.test.redispubsub.receivers.ReceiverInterface;
import com.gjz.test.redispubsub.service.RedisSubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <pre>
 * Redis订阅服务逻辑处理
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/7 14:58
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@Slf4j
@Service("redisSubscribeService")
public class RedisSubscribeServiceImpl implements RedisSubscribeService {
    private static final String LOGGER_HEAD = "【 Redis订阅服务逻辑处理 】";
    private static final Logger logger = LoggerFactory.getLogger(RedisSubscribeServiceImpl.class);

    @Resource
    RedisMessageListenerContainer container;


/**
     * 订阅
     *
     * @param topic
     * @param receiver
     */

    @Override
    public void subscribe(String topic, ReceiverInterface receiver) {
        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(receiver, "receiveMessage");
        listenerAdapter.afterPropertiesSet();
        container.addMessageListener(listenerAdapter, new PatternTopic(topic));
    }

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}
