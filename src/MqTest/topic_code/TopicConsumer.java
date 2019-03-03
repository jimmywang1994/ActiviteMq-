package MqTest.topic_code;

import MqTest.util.MqUtil;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicConsumer {
    /**
     * 处理消息.
     */
    public void consumMessage() {

        ConnectionFactory factory = null;
        Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageConsumer consumer = null;
        try {

            factory = new ActiveMQConnectionFactory(MqUtil.USER_NAME, MqUtil.USER_PASSWORD,
                    MqUtil.BROKEN_URL);

            connection = factory.createConnection();

            connection.start();

            session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            destination = session.createTopic("topic_01");

            consumer = session.createConsumer(destination);
            // 超时，连接超时。不是确认超时，是等待多久后，没有消息可处理，超时。
            // consumer.receive(timeout);
            // 注册监听器。 注册成功后，队列中的消息变化会自动触发监听器代码。 接收消息并处理。
            consumer.setMessageListener(new MessageListener() {

                /*
                 * 监听器一旦注册, 永久有效.
                 * 永久 - consumer线程不关闭.
                 * 处理消息的方式: 只要有消息未处理,自动调用onMessage方法,处理消息.
                 * 监听器可以注册若干. 注册多个监听器,相当于集群.
                 * ActiveMQ自动的循环调用多个监听器, 处理队列中的消息.实现并行处理.
                 *
                 * 处理消息的方法. 就是监听方法.
                 * 监听的事件是: 消息, 消息未处理.
                 * 要处理的具体内容: 消息处理.
                 * @param message - 未处理的消息.
                 */
                @Override
                public void onMessage(Message message) {
                    try {
                        // acknowledge方法，就是确认方法。代表consumer已经收到消息。 确定后，MQ删除对应的消息。
                        // message.acknowledge();
                        TextMessage om = (TextMessage) message;
                        String data = om.getText();
                        System.out.println(data);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }

            });

            // 阻塞当前代码。 保证listener代码未结束。 如果代码结束了，监听器自动关闭。
            System.in.read();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        TopicConsumer tConsumer=new TopicConsumer();
        tConsumer.consumMessage();
    }
}
