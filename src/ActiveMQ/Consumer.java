package ActiveMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer {
    private static final String BROKEN_URL= ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String USER_NAME=ActiveMQConnection.DEFAULT_USER;
    private static final String USER_PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD;

    ConnectionFactory connectionFactory;
    Connection connection;
    Session session;
    ThreadLocal<MessageConsumer> threadLocal=new ThreadLocal<>();
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
    public void getMessage(String destName){
        try {
            Queue queue=session.createQueue(destName);
            MessageConsumer mConsumer=null;
            if(threadLocal.get()!=null){
                mConsumer=threadLocal.get();
            }else {
                mConsumer=session.createConsumer(queue);
            }
            while (true){
                Thread.sleep(1000);
                TextMessage msg=(TextMessage)mConsumer.receive();
                if(msg!=null){
                    msg.acknowledge();
                    System.out.println(Thread.currentThread().getName()+"我是consumer:我收到了一条消息,消费一条消息"+msg.getText()+"       "+count.getAndIncrement());
                }else{
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
