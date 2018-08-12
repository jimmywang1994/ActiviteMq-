package ActiveMQ;

public class MqTest {
    public static void main(String[] args) {
        Producter producter =new Producter();
        producter.init();
        MqTest mqTest=new MqTest();
        try {
            Thread.sleep(1000);
            new Thread(mqTest.new ProductorMq(producter)).start();
            new Thread(mqTest.new ProductorMq(producter)).start();
            new Thread(mqTest.new ProductorMq(producter)).start();
            new Thread(mqTest.new ProductorMq(producter)).start();
            new Thread(mqTest.new ProductorMq(producter)).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private class ProductorMq implements Runnable{
        Producter producter;
        public ProductorMq(Producter producter){
            this.producter = producter;
        }
        @Override
        public void run() {
            while (true){
                producter.sendMessage("mq1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
