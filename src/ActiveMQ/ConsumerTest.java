package ActiveMQ;

public class ConsumerTest {
    public static void main(String[] args) {
        Consumer consumer=new Consumer();
        consumer.init();
        ConsumerTest consumerTest=new ConsumerTest();
        new Thread(consumerTest.new ConsumerMq(consumer)).start();
        new Thread(consumerTest.new ConsumerMq(consumer)).start();
        new Thread(consumerTest.new ConsumerMq(consumer)).start();
        new Thread(consumerTest.new ConsumerMq(consumer)).start();
        new Thread(consumerTest.new ConsumerMq(consumer)).start();
    }
    private class ConsumerMq implements Runnable{
        Consumer consumer;
        public ConsumerMq(Consumer consumer){
            this.consumer=consumer;
        }
        @Override
        public void run() {
            while (true){
                consumer.getMessage("mq1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
