package demo.t2_workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            // 消息持久化
            boolean durable = true;
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);

//            String message = String.join(" ", argv);
            String message = "{\"test\":\"测试中文json233\"}";

            channel.basicPublish("", TASK_QUEUE_NAME,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

            // 生产者能否得到消费者的反馈？// 第六节RPC解决了这个问题

        }
    }

}
