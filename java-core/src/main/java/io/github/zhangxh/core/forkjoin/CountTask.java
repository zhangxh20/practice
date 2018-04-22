package io.github.zhangxh.core.forkjoin;

import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer>{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int start;
    
    private int end;
    
    private int [] data;
    
    public CountTask(int start,int end,int [] data) {
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    protected Integer compute() {
        int gap = end - start;
        if (gap == 1) {
            return data[start] + data[end];
        } else if (gap == 0) {
            return data[start];
        } else if (gap > 1) {
            int mid = (start + end)/2;
            CountTask leftTask = new CountTask(start, mid, data);
            CountTask rightTask = new CountTask(mid + 1 , end, data);
            leftTask.fork();
            rightTask.fork();
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            return leftResult + rightResult;
        }
        return null;
    }

}
