package ActiveMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

//消息生产者
public class Producter {
    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String USER_NAME = ActiveMQConnection.DEFAULT_USER;
    private static final String USER_PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;

    AtomicInteger count = new AtomicInteger(0);
    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();

    /**
     * 初始化操作
     */
    public void init() {
        connectionFactory = new ActiveMQConnectionFactory(USER_NAME, USER_PASSWORD, BROKEN_URL);
        try {
            //从工厂中创建连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建一个事务
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String destName) {
        try {
            Queue queue = session.createQueue(destName);
            MessageProducer producer = null;
            if (threadLocal.get() != null) {
                producer = threadLocal.get();
            } else {
                producer = session.createProducer(queue);
                threadLocal.set(producer);
            }
            while (true) {
                Thread.sleep(1000);
                int num = count.getAndIncrement();
                TextMessage msg = session.createTextMessage(Thread.currentThread().getName() + "producter:我在生产东西,count:" + num);
                System.out.println(Thread.currentThread().getName() + "producter:我在生产东西,count:" + num);
                producer.send(msg);
                session.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
