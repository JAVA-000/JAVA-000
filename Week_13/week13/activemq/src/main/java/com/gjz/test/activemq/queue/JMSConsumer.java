package com.gjz.test.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * <pre>
 * JMS消费者
 * </pre>
 *
 * @author guojz
 * @version 1.00.00
 * @createDate 2021/1/13 15:38
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class JMSConsumer {

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
        MessageConsumer messageConsumer;
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //创建一个名称为test的消息队列
            destination = session.createQueue("test");
            //创建消费者
            messageConsumer = session.createConsumer(destination);
            //消费消息
            receiveMessage(session, messageConsumer);
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
     * 消费消息
     * @param session
     * @param messageConsumer
     */
    private static void receiveMessage(Session session, MessageConsumer messageConsumer) throws JMSException{
        while (true) {
            TextMessage textMessage = (TextMessage) messageConsumer.receive();
            if(textMessage != null){
                System.out.println("收到的消息:" + textMessage.getText());
            }else {
                break;
            }
        }
    }
}

