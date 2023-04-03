# Rocket

## 基本概念

- Topic:消息传输和存储的顶层容器，用于标识同一类业务逻辑的消息。主题通过TopicName来做唯一标识和区分
- MessageType:  Apache RocketMQ 支持的消息类型有普通消息、顺序消息、事务消息和定时/延时消息。
- MessageQueue: 是 Apache RocketMQ 中消息存储和传输的实际容器，也是消息的最小存储单元
- Message: Apache RocketMQ 中的最小数据传输单元.产者将业务数据的负载和拓展属性包装成消息发送到服务端，服务端按照相关语义将消息投递到消费端进行消费。
- MessageView: 面向开发者提供的消息只读接口。可以读取消息的多个属性和负载信息。但那时不能对消息进行修改。
- MessageTag： 消息标签，在Topic之下对消息进行细分
- 生产者
- 事务检查器: 生产者用来执行本地事务检查和异常事务恢复的监听器
- 事务状态: Apache RocketMQ 中事务消息发送过程中，事务提交的状态标识，服务端通过事务状态控制事务消息是否应该提交和投递。事务状态包括事务提交、事务回滚和事务未决。
- 消费者分组: 是Apache RocketMQ 系统中承载多个消费行为一致的消费者的负载均衡分组.是为了实现消费性能的水平扩展
- Consumer Result: Apache RocketMQ 中PushConsumer消费监听器处理消息完成后返回的处理结果
- 订阅关系: 订阅关系是Apache RocketMQ 系统中消费者获取消息、处理消息的规则和状态配置