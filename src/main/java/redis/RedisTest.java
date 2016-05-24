package redis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hadoop on 2016/5/19.
 */
public class RedisTest {

    @Test
    public void redisSimpleTest() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        Jedis jedis = new Jedis("172.27.102.125", 6379);
        jedis.set("xym", "1");
        System.out.println(jedis.get("xym"));
        jedis.set("xym", "2");
        System.out.println(jedis.get("xym11"));

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

    }
}
