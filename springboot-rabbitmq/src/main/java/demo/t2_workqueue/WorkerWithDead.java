package demo.t2_workqueue;

/**
 * Q:当消费者挂掉之后，应该怎么处理 TODO
 *
 * A: 如果RabbitMQ 检测到连接关闭了或者丢失（宕机或者抛出异常后）
 * MQ认为当前消息并未被完全处理，会将其重新入队，分配给其他消费者
 */
public class WorkerWithDead {
}
