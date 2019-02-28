package MqTest.first_code;

import MqTest.util.MqUtil;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MqProducer {
    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    Message textMessage;
    MessageProducer producer;
    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(MqUtil.USER_NAME, MqUtil.USER_PASSWORD, MqUtil.BROKEN_URL);
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }


    public void sendMessage(String destName,String message){
        try {
            Queue queue=session.createQueue(destName);
            producer=session.createProducer(queue);
            textMessage=session.createTextMessage(message);
            producer.send(textMessage);
            System.out.println("消息发送成功！");
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
