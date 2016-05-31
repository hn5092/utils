package redis;

import org.apache.storm.shade.com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.*;

/**
 * Created by hadoop on 2016/5/19.
 */
public class RedisTest {
    Jedis jedis;

    @Before
    public void init() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedis = new Jedis("172.27.102.125", 6379);

    }

    @Test
    public void redisSimpleTest() {
        jedis = new Jedis("172.27.102.125", 6379);
        jedis.set("xym", "1");
        System.out.println(jedis.get("xym"));
        jedis.set("xym", "2");
        System.out.println(jedis.get("xym11"));


        List<String> xym = jedis.sort("xym");
    }

    @Test
    public void redisSimpleTest_2() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        jedisClusterNodes.add(new HostAndPort("172.27.102.125", 6379));
        JedisCluster jedis = new JedisCluster(jedisClusterNodes);
        jedis.set("xym", "1");
        System.out.println(jedis.get("xym"));
        jedis.set("xym", "2");
        System.out.println(jedis.get("xym11"));
        List<String> xym = jedis.sort("xym");
        for (String s : xym
                ) {
            System.out.println(s);

        }

    }

    @Test
    public void testListSort() {


        jedis.lpush("mylist", "baidu");
        jedis.lpush("mylist","hello");
        jedis.lpush("mylist", "xhan");
        jedis.lpush("mylist", "soso");
        List<String> mylist = jedis.sort("mylist");
        for (String s : mylist
                ) {
            System.out.println(s);

        }

    }

    @Test
    public void testList() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("baidu");
        strings.add("hello");
        strings.add("xhan");
        strings.add("soso");
        strings.sort(new Comparator<String>() {
            @Override
            public int compare(String value, String anotherString) {

                if (value.length() == anotherString.length()) {
                    return value.getBytes()[0] - value.getBytes()[0];
                } else {
                    return value.getBytes().length - anotherString.getBytes().length;
                }
            }
        });
        for (byte b : "xhan".getBytes()) {
            System.out.println(b);
        }
        System.out.println("-----------");
        for (byte b : "soso".getBytes()) {
            System.out.println(b);
        }
        for (String s : strings
                ) {
            System.out.println(s);

        }
    }

}
