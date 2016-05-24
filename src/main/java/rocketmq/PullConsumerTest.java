/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rocketmq;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntryExt;
import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullCallback;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.consumer.store.OffsetStore;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.apache.commons.lang.SystemUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;


/**
 * PullConsumer，订阅消息
 */
public class PullConsumerTest {
    DefaultMQPullConsumer consumer;
    MessageQueue mq;
    OffsetStore offsetStore;

    @BeforeTest
    public void initEnv() {
        consumer = new DefaultMQPullConsumer("PushConsumer");
        consumer.setNamesrvAddr("172.27.101.67:9876");
        mq = new MessageQueue();
        mq.setQueueId(2);
        mq.setTopic("mysql-2");
        mq.setBrokerName("broker-a");
        try {
            consumer.start();
            offsetStore = consumer.getDefaultMQPullConsumerImpl().getOffsetStore();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试尝试更新offerset
     */
    @Test
    public void testModify() {
        try {
            long l = consumer.fetchConsumeOffset(mq, false);
            System.out.println("得到offerset" + l);
            //更新offerset
            consumer.updateConsumeOffset(mq, l + 1);
            OffsetStore offsetStore = consumer.getDefaultMQPullConsumerImpl().getOffsetStore();
            offsetStore.persist(mq);
            l = consumer.fetchConsumeOffset(mq, false);
            System.out.println("更新后的offerset" + l);

        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试获取队列中的数据
     */
    @Test
    public void testGetMessage() {
        long next = 0;
        int count =0;
        try {
            long offset = consumer.fetchConsumeOffset(mq, true)+10;
            PullResult pullResult = null;
            long l = consumer.maxOffset(mq);
            while (true) {
                pullResult = consumer.pullBlockIfNotFound(mq, null, offset, 32);
                next = offset;
                offset = pullResult.getNextBeginOffset();
                List<MessageExt> msgFoundList = pullResult.getMsgFoundList();
                if (msgFoundList == null) {
                    Thread.sleep(1000);
                } else {
                    for (MessageExt ms : msgFoundList) {

                        String keys = ms.getKeys();
//                        if (next != Integer.parseInt(ms.getKeys())) {
//                            throw new Exception("数据丢失");
//                        }
                        CanalEntryExt.RowDataExt rowDataExt = CanalEntryExt.RowDataExt.parseFrom(ms.getBody());
                        System.out.println("操作类型 : " + rowDataExt.getEventType());
                        printColumn(rowDataExt.getRowdata().getAfterColumnsList());
                        Integer.parseInt(rowDataExt.getRowdata().getAfterColumnsList().get(1).getValue());
                        saveOfferSet(ms.getQueueOffset(), offsetStore, mq);
                        next++;
                        System.out.println(ms.getKeys());
                    }
                }
            }
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实时更新offerset
     */
    @Test
    public void testRecodeMessage() {
        long offset = 0;
        try {
            PullResult pullResult = consumer.pull(mq, null, offset, 9);
            List<MessageExt> msgFoundList = pullResult.getMsgFoundList();
            for (MessageExt ms : msgFoundList) {
                long queueOffset = ms.getQueueOffset();
                saveOfferSet(queueOffset, offsetStore, mq);
            }
            //更新offerset
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新offerset
     *
     * @param offerSet    更新值
     * @param offsetStore offsetStore
     * @param mq          需要更新的队列
     * @throws MQClientException
     */
    public void saveOfferSet(long offerSet, OffsetStore offsetStore, MessageQueue mq) throws MQClientException {
        System.out.println("当前offerset" + offerSet);
        consumer.updateConsumeOffset(mq, offerSet);
        offsetStore.persist(mq);
    }

    /**
     * 查看mq的offersets
     *
     * @throws MQClientException
     */
    @Test
    public void lookOfferSet() throws MQClientException {
        System.out.println(consumer.fetchConsumeOffset(mq, true));
    }

    @Test
    public void resetOfferSet() throws MQClientException {
        saveOfferSet(0, offsetStore, mq);
    }


    @Test
    public void testget() throws MQClientException {
        try {

            long l = consumer.fetchConsumeOffset(mq, false);
            System.out.println("-----------" + l);

            PullResult pr = null;


            long beginTime = System.currentTimeMillis();

            consumer.updateConsumeOffset(mq, 9);
            l = consumer.fetchConsumeOffset(mq, false);
            System.out.println("-----------" + l);
            long offset = 0;
            PullResult pullResult = consumer.pull(mq, null, offset, 9);

//            QueryResult topic2 = consumer.queryMessage("Topic2", 25 + "", 1, 2, 3);
            MessageExt ac1B654300002A9F0000000000022E35 = consumer.viewMessage("AC1B654300002A9F0000000000022E35");
            System.out.println("this is the " + new String(ac1B654300002A9F0000000000022E35.getBody())); //Hello RocketMQ 29
            HashMap<String, MessageExt> success = new HashMap<String, MessageExt>();
            System.out.println(System.currentTimeMillis() - beginTime);
            System.out.println(pullResult);
            List<MessageExt> msgFoundList = pullResult.getMsgFoundList();
            List<MessageExt> msgFail = new ArrayList<MessageExt>();
            Random random = new Random();
            for (MessageExt ms : msgFoundList) {
                int reconsumeTimes = ms.getReconsumeTimes();
                System.out.println("this is " + reconsumeTimes);
                ms.setReconsumeTimes(reconsumeTimes + 1);
                int i = random.nextInt();
                if ((i & 2) == 0) {
                    System.out.println("事物成功");
                    success.put(ms.getMsgId(), ms);
                } else {
                    System.out.println("事物失败");
                    msgFail.add(ms);
                }
            }
            System.out.println("第一次执行失败" + msgFail.size() + "个");

            boolean check = check(msgFoundList, success);
            if (!check) {
                while (msgFail.size() != 0) {
                    for (int i = 0; i < msgFail.size(); i++) {
                        int rd = random.nextInt() % 2;
                        if ((rd & 2) == 0) {
                            System.out.println("执行成功");
                            success.put(msgFail.get(i).getMsgId(), msgFail.get(i));
                            msgFail.remove(i);
                            System.out.println("还剩下" + msgFail.size() + "个事件");
                        } else {
                            System.out.println("执行失败");
                            System.out.println("还剩下" + msgFail.size() + "个事件");
                        }
                    }
                    System.out.println("等待1000毫秒后重试");
                    Thread.sleep(1000);
                }
            }
            System.out.println("一共执行" + msgFoundList.size() + "个事件");
            System.out.println("执行成功" + success.size() + "个");
            QueueOffsetCache.putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
            System.out.println(consumer.maxOffset(mq));
        } catch (Exception e) {
            e.printStackTrace();
        }

        consumer.shutdown();
    }

    public boolean check(List<MessageExt> msgFoundList, HashMap<String, MessageExt> success) {
        return success.size() == msgFoundList.size();
    }

    protected void printColumn(List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            StringBuilder builder = new StringBuilder();
            builder.append(column.getName() + " : " + column.getValue());
            builder.append("    type=" + column.getMysqlType());
            if (column.getUpdated()) {
                builder.append("    update=" + column.getUpdated());
            }
            builder.append(SystemUtils.LINE_SEPARATOR);
            System.out.println(builder.toString());
        }
    }
}
