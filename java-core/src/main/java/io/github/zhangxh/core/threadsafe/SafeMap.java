package io.github.zhangxh.core.threadsafe;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class SafeMap {

    static Map<String, String> map = new HashMap<>();
    
    static Map<String, String> table = new Hashtable<>();
    
    static class MyThread implements Runnable {

        @Override
        public void run() {
            for (int i = 10000; i > 0;i--) {
                map.put(String.valueOf(i), String.valueOf(i));
            }
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(new MyThread());
        Thread t2 = new Thread(new MyThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }
}
