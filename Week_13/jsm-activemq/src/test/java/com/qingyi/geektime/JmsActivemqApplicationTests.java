package com.qingyi.geektime;

import com.qingyi.geektime.jms.JmsProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JmsActivemqApplicationTests {

    @Autowired
    private JmsProducer producer;

    @Test
    void contextLoads() {
    }

    @Test
    public void activemqJmsQueueTest() {
        producer.sendMessage("inbound.queue", "inbound.queue.message");
        //2021-01-12 14:04:52.170  INFO 21796 --- [enerContainer-1] com.qingyi.geektime.jms.JmsConsumer      : listening queue:::inbound.queue | message:::inbound.queue.message
        //2021-01-12 14:04:52.228  INFO 21796 --- [           main] com.qingyi.geektime.jms.JmsProducer      : Sending message to queue/topic-inbound.queue,message:::inbound.queue.message
    }

    @Test
    public void activemqJmsTopicTest() {
        producer.sendMessage("inbound.topic", "inbound.topic.message");
        //2021-01-12 14:06:58.744  INFO 1108 --- [           main] com.qingyi.geektime.jms.JmsProducer      : Sending message to queue/topic-inbound.topic,message:::inbound.topic.message
        //2021-01-12 14:06:58.746  INFO 1108 --- [enerContainer-1] com.qingyi.geektime.jms.JmsConsumer      : listening topic:inbound.topic | message:::inbound.topic.message
    }

}
