package io.github.zhangxh.core.waitnotify;

public class StackRep {

    private Product [] products = new Product[6];
    
    private int index = -1;
    
    public synchronized void push(Product product) throws Exception {
        while (index  == (products.length - 1)) {
            System.out.println("仓库已满，等待中");
            this.wait();
        }
        index ++;
        products[index] = product;
        this.notifyAll();
    }
    
    public synchronized Product pop() throws Exception {
        while (index < 0) {
            System.out.println("仓库为空，等待中");
            this.wait();
        }
        Product product = products[index];
        index --;
        this.notifyAll();
        return product;
    }
            
}
