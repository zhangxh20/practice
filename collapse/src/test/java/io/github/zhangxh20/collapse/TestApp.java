package io.github.zhangxh20.collapse;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import io.github.zhangxh20.collapse.service.OrderService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations="classpath:spring-core.xml")
public class TestApp {

    private static final Logger logger = LoggerFactory.getLogger(TestApp.class);
    
    private int count = 3000;
    
    private CountDownLatch countDown = new CountDownLatch(count);
    
    @Resource
    private OrderService orderService;
    @Resource
    private JedisPool pool;
    
    private static final String REDIS_KEY = "amount";
    
    private ReentrantLock lock = new ReentrantLock();
    @Before
    public void before() {
        Jedis jedis = pool.getResource();
        long amount = orderService.getAmount();
        jedis.set(REDIS_KEY, String.valueOf(amount));
        jedis.expire(REDIS_KEY, 1);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test() {
        Thread [] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(new Runnable() {
                
                @Override
                public void run() {
                    try {
                        logger.info("准备就绪");
                        countDown.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showAmount();
                }
            });
            threads[i].start();
            countDown.countDown();
        }
        for (int i = 0; i < count; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                logger.info("InterruptedException",e);
            }
        }
        
    }
    
    private void showAmount() {
        Jedis jedis = pool.getResource();
        long amount = 0;
        String str = jedis.get(REDIS_KEY);
        if (StringUtils.isEmpty(str)) {
            lock.lock();
            if (jedis.get(REDIS_KEY) == null) {
                logger.info("从MySQL获取");
                amount = orderService.getAmount();
                jedis.set(REDIS_KEY, String.valueOf(amount));
                jedis.expire(REDIS_KEY, 10);
            }
            lock.unlock();
        } else {
            logger.info("从redis获取");
            amount = Long.parseLong(str);
        }
        jedis.close();
        logger.info("amount:" + amount);
    }
}
