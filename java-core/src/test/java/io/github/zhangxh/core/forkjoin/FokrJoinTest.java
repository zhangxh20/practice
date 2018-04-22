package io.github.zhangxh.core.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.junit.Test;

public class FokrJoinTest {

    @Test
    public void testCount() throws Exception {
        ForkJoinPool pool = new ForkJoinPool(4);
        int [] data = new int[100];
        for (int i = 0; i < 100; i++) {
            data[i] = i+1;
        }
        CountTask task = new CountTask(0, 99, data);
        Future<Integer> result = pool.submit(task);
        System.out.println(result.get());
    }
}
