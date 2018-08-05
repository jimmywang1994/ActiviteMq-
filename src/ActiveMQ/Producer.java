package ActiveMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer {
    private static final String BROKEN_URL= ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String USER_NAME=ActiveMQConnection.DEFAULT_USER;
    private static final String USER_PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;

    AtomicInteger count=new AtomicInteger(0);
    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    ThreadLocal<Producer> threadLocal=new ThreadLocal<>();
}
