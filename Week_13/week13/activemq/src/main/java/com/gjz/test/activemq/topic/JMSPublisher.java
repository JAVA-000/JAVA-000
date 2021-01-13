package com.gjz.test.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * <pre>
 * JMS发布者
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/13 16:11
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class JMSPublisher {

    public static void main(String[] args) {
        //连接工厂
        ConnectionFactory connectionFactory;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session;
        //消息的目的地
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建一个名称为topic-test的topic
            destination = session.createTopic("topic-test");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //发送消息
            sendMessage(session, messageProducer);

            session.commit();

        } catch (Exception e) {
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
    /**
     * 发送消息
     * @param session
     * @param messageProducer  消息生产者
     * @throws Exception
     */
    public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
        for (int i = 0; i < 10; i++) {
            //创建一条文本消息
            TextMessage message = session.createTextMessage("ActiveMQ 发送消息到Topic" +i);
            System.out.println("发送消息：Activemq 发送消息到Topic" + i);
            //通过消息生产者发出消息
            messageProducer.send(message);
        }

    }
}

