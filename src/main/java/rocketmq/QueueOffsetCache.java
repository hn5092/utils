package rocketmq;

import com.alibaba.rocketmq.common.message.MessageQueue;

import java.util.HashMap;
import java.util.Map;

public class QueueOffsetCache {

    private static final Map<MessageQueue, Long> offsetCache = new HashMap<MessageQueue, Long>();

    public static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offsetCache.put(mq, offset);
    }

    public static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offsetCache.get(mq);
        if (offset != null)
            return offset;
        return 0;
    }

}