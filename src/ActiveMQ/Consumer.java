package ActiveMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {
    private static final String BROKEN_URL= ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String USER_NAME=ActiveMQConnection.DEFAULT_USER;
    private static final String USER_PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;

    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    ThreadLocal<Consumer> threadLocal=new ThreadLocal<>();
    AtomicInteger count = new AtomicInteger();

    public void init(){
        try {
            connectionFactory = new ActiveMQConnectionFactory(USER_NAME,USER_PASSWORD,BROKEN_URL);
            connection  = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
