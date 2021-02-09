package com.qingyi.geektime.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JmsConsumer {

    @JmsListener(destination = "inbound.queue")
    public void processMessage(String content) {
        // ...
        log.info("listening queue:{} | message:{}", "inbound.queue", content);
    }

    @JmsListener(destination = "inbound.topic")
    @SendTo("outbound.topic")
    public String processMessageFromTopic(String content) {
        log.info("listening topic:{} | message:{}", "inbound.topic", content);
        return content;
    }

}
