package com.qingyi.geektime.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JmsProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String queue, String content) {
        jmsTemplate.send(queue, session -> session.createTextMessage(content));
        log.info("Sending message to queue/topic:{} | message:{}", queue, content);
    }
}
