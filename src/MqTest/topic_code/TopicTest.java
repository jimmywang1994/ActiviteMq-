package MqTest.topic_code;

public class TopicTest {
    public static void main(String[] args) {
        TopicProducer tProducer=new TopicProducer();
        tProducer.init();
        tProducer.sendMessageToTopic("topic_01","这是主题模式测试数据6");
    }
}
