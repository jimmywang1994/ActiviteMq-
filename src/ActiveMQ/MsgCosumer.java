package ActiveMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

//基于mq的监听器形式
public class MsgCosumer implements MessageListener{
    private static final String BROKEN_URL= ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String USER_NAME=ActiveMQConnection.DEFAULT_USER;
    private static final String USER_PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(USER_NAME,USER_PASSWORD,BROKEN_URL);
        Connection connection=connectionFactory.createConnection();
        connection.start();
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //Queue queue=session.createQueue("mq1");
        Topic topic=session.createTopic("mq1");
        MessageConsumer consumer=session.createConsumer(topic);
        MsgCosumer msgCosumer=new MsgCosumer();
        consumer.setMessageListener(msgCosumer);
    }
    @Override
    public void onMessage(Message message) {
        TextMessage msg=(TextMessage)message;
        try {
            System.out.println("我通过主题模式收到了一条消息，消费一条消息"+msg.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
