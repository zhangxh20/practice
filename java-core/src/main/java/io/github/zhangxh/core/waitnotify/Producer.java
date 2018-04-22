package io.github.zhangxh.core.waitnotify;

public class Producer implements Runnable {
    
    private StackRep stack;
    
    public Producer(StackRep rep) {
        this.stack = rep;
    }

    public void run() {
        int id = 1;
        Product product;
        while (true) {
            product = new Product();
            product.setId(id);
            try {
                stack.push(product);
                System.out.println("生产了产品：" + id);
                Thread.sleep((long)(500 * Math.random()));
                id ++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
