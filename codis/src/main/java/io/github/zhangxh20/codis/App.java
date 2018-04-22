package io.github.zhangxh20.codis;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        JedisResourcePool jedisPool = RoundRobinJedisPool.create().curatorClient("192.168.84.128:2181", 30000)
                .zkProxyDir("/jodis/codis-demo").build();
        Jedis jedis = jedisPool.getResource();
        for (int i = 0; i < 100; i++ ) {
            
            jedis.set("foo", "bar");
            String value = jedis.get("foo");
            System.out.println(value);
        }

    }
}
