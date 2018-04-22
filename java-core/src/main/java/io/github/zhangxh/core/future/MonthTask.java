package io.github.zhangxh.core.future;

import java.util.concurrent.Callable;

public class MonthTask implements Callable<Long> {

    @Override
    public Long call() throws Exception {
        System.out.println("开始计算当月销售额");
        Long value = 1000 + (long)(5000 * Math.random());
        Thread.sleep(value);
        return value;
    }

}
