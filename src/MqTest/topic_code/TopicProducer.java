package MqTest.topic_code;

import MqTest.util.MqUtil;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducer {
    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    Message textMessage;
    MessageProducer producer;
    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(MqUtil.USER_NAME, MqUtil.USER_PASSWORD, MqUtil.BROKEN_URL);
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
    public void sendMessageToTopic(String destName,String message){
        try {
            Topic topic=session.createTopic(destName);
            producer=session.createProducer(topic);
            textMessage=session.createTextMessage(message);
            producer.send(textMessage);
            System.out.println("主题模式消息已发送");
        }catch (JMSException ex){
            ex.printStackTrace();
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if(session!=null){
                try {
                    session.close();
                }catch (JMSException ex){
                    ex.printStackTrace();
                }
            }
            if(producer!=null){
                try {
                    producer.close();
                }catch (JMSException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
