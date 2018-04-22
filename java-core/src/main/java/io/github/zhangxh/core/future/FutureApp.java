package io.github.zhangxh.core.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureApp {

    public static void main(String[] args) throws Exception, ExecutionException {
        futureTask();
    }
    
    public static void future() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<Long>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MonthTask task = new MonthTask();
            Future<Long> future =  executor.submit(task);
            futureList.add(future);
        }
        Long amount = 0l;
        for (Future<Long> f : futureList) {
            amount += f.get();
        }
        System.out.println("amount:" + amount);
    }
    
    public static void futureTask() throws Exception {
        List<FutureTask<Long>> tasks = new ArrayList<>();
        MonthTask task = new MonthTask();
        for (int i = 0; i < 10; i++) {
            FutureTask<Long> futureTask = new FutureTask<>(task);
            tasks.add(futureTask);
            Thread t = new Thread(futureTask);
            t.start();
        }
        Long amount = 0l;
        for (FutureTask<Long> f : tasks) {
            amount += f.get();
        }
        System.out.println("amount:" + amount);
    }
}
