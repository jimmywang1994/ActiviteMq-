package MqTest.first_code;

import MqTest.util.MqUtil;

public class MqTest {
    public static void main(String[] args) {
        MqProducer producer=new MqProducer();
        producer.init();
        producer.sendMessage("dt1","这是一条测试消息2");
    }
}
