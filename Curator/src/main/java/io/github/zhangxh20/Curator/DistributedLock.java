package io.github.zhangxh20.Curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DistributedLock {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        String connectString = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
        InterProcessMutex lock = new InterProcessMutex(client, "/lock");
        try {
            logger.info("准备获取锁");
            boolean flag = true;
            while (flag) {
                if ( lock.acquire(2, TimeUnit.SECONDS) ) {
                    logger.info("获取锁成功");
                    Thread.sleep(10000);
                    flag = false;
                }
                if (flag) {
                    logger.info("获取锁失败，进入重试");
                }
            }
            
        } catch (Exception e) {
            logger.info("exception", e);
        } finally {
            try {
                if (lock.isAcquiredInThisProcess()) {
                    lock.release();
                    logger.info("释放锁");
                }
            } catch (Exception e) {
                logger.info("释放锁异常",e);
            }
            logger.info("结束");
        }
    }
}
