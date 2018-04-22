package io.github.zhangxh20.Curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;

public class WatcherExample {

    private static CuratorFramework client;
    public static void main(String[] args) throws Exception {
        connect();
        watch1();
        System.in.read();
    }
    
    public static void connect() {
        String connectString = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        client.start();
    }
    
    /**
     * 只能监听一次
     */
    public static void watch1() {
        try {
            byte [] data = client.getData().usingWatcher(new CuratorWatcher() {
                
                @Override
                public void process(WatchedEvent event) throws Exception {
                    System.out.println(event);
                }
            }).forPath("/curator");
            System.out.println(new String(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
