package io.github.zhangxh20.thumbnail;

public class AppMain {
    
    public static void main(String[] args) {
        Master master = Master.create("config.xml");
        master.generate();
    }
}
