package com.gjz.test.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.*;
import java.io.IOException;

/**
 * <pre>
 * JMS订阅者
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/13 16:13
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class JMSSubscriber {
    public static void main(String[] args) {

        Session session;

        Connection connection = null;
        try {
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = factory.createConnection();
            connection.start();
            //创建一个Topic
            Topic topic = new ActiveMQTopic("topic-test");
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //注册消费者1
            MessageConsumer consumer1 = session.createConsumer(topic);
            consumer1.setMessageListener(m -> {
                try {
                    System.out.println("Consumer1 get: " + ((TextMessage) m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
            //注册消费者2
            MessageConsumer consumer2 = session.createConsumer(topic);
            consumer2.setMessageListener(m -> {
                try {
                    System.out.println("Consumer2 get: " + ((TextMessage) m).getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });

            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch (JMSException e){
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

