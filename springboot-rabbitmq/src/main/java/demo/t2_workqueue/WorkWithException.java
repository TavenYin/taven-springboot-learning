package demo.t2_workqueue;

import com.rabbitmq.client.*;

/**
 * 发生异常时处理 TODO
 */
public class WorkWithException {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUri("amqp://admin:123456@127.0.0.1:5672");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // accept only one unack-ed message at a time
        channel.basicQos(2);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" [x] Received '" + message + "'");
//            try {
                doWork(message);
//            } catch (Exception e) {
//                System.out.println(" catch Exception");
//                // todo 消息发生异常时如何处理 可能应该将错误的消息记录一下，具体场景具体分析
//                channel.basicReject(delivery.getEnvelope().getDeliveryTag(), true);
//                return;
//            }
            System.out.println(" [x] Done");
            // 向mq发送ack (确认请求)，表示当前消息成功处理
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) {
        // 据我观察，当消费者客户端抛出异常后，MQ会切断当前客户端，
        // 因为消息并没有删除，MQ会将该消息转发给其他的消费者节点
        // 抛出异常的客户端，不会再接收到数据
        throw new RuntimeException();
    }

}
