package rocketmq.object;

import base.protocolbuffer.TestPeople;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

public class ConsumerObject {
	public static void main(String[] args){
		DefaultMQPushConsumer consumer = 
				new DefaultMQPushConsumer("PushConsumer3");
		consumer.setNamesrvAddr("172.27.101.67:9876");
		try {
			//订阅PushTopic下Tag为push的消息
			consumer.subscribe("PushTopic", "push");
			//程序第一次启动从消息队列头取数据
			consumer.setConsumeFromWhere(
					ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener(
				new MessageListenerConcurrently() {
					public ConsumeConcurrentlyStatus consumeMessage(
							List<MessageExt> list,
							ConsumeConcurrentlyContext Context) {
						MessageExt msg = list.get(0);
						System.out.println(msg.toString());
						try {
							System.out.println(msg.getReconsumeTimes());
							System.out.println(TestPeople.People.parseFrom(msg.getBody()).getName());
						} catch (InvalidProtocolBufferException e) {
							e.printStackTrace();
						}
						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					}
				}
			);
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
