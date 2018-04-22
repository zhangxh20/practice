package io.github.zhangxh.core.waitnotify;

public class AppMain {

    public static void main(String[] args) {
        StackRep stack = new StackRep();
        Producer producer = new Producer(stack);
        Consumer consumer = new Consumer(stack);
        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        Thread t3 = new Thread(consumer);
        t1.start();
        t2.start();
        t3.start();
    }
}
