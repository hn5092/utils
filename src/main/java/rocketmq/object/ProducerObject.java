package rocketmq.object;

import base.protocolbuffer.TestPeople;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class ProducerObject {
	public static void main(String[] args){
		DefaultMQProducer producer = new DefaultMQProducer("Producer2");
		producer.setNamesrvAddr("172.27.101.67:9876");
		try {
			producer.start();
			TestPeople.People.Builder peBuilder = TestPeople.People.newBuilder().
					//设置各种属性
							setEmail("100650920").
							setName("xym").
							setId(10).
					//添加一个关联的子类
							addPhone(TestPeople.People.PhoneNumber.newBuilder().setNumber("1860").setType(1));
			TestPeople.People people = peBuilder.build();
			byte[] buf = people.toByteArray();

			Message msg = new Message("PushTopic", 
					"push", 
					"1",
					buf);
			
			SendResult result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() +
					" result:" + result.getSendStatus());

			peBuilder = TestPeople.People.newBuilder().
					//设置各种属性
							setEmail("100650920").
							setName("xym2").
							setId(10).
					//添加一个关联的子类
							addPhone(TestPeople.People.PhoneNumber.newBuilder().setNumber("1860").setType(1));
			TestPeople.People people2 = peBuilder.build();
			byte[] buf2 = people2.toByteArray();
			msg = new Message("PushTopic", 
					"push", 
					"2",
					buf2);
			
			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() +
					" result:" + result.getSendStatus());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			producer.shutdown();
		}
	}
}
