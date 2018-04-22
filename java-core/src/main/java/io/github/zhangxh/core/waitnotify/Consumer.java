package io.github.zhangxh.core.waitnotify;

public class Consumer implements Runnable {
    
    private StackRep stack;
    
    public Consumer(StackRep rep) {
        this.stack = rep;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Product product = stack.pop();
                System.out.println("消费了产品：" + product.getId());
                Thread.sleep((long)(1000 * Math.random()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }

    }

}
