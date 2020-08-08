package com.github.AllenDuke.concurrentTest;

/**
 * @author 杜科
 * @description 测试
 * @contact AllenDuke@163.com
 * @date 2020/7/25
 */
public class BlockingLinkedQueueTest {

    public static void main(String[] args) throws InterruptedException {
        BlockingLinkedQueue queue = new BlockingLinkedQueue(20);
        System.out.println("start adding...");
        for(int i=0;i<5;i++){
            int finalI = i;
            new Thread(()->{
                for(int j=1;j<=5;j++){
                    try {
                        queue.add(finalI *5+j);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(100);
        System.out.println("start taking...");
        for(int i=0;i<5;i++){
            int finalI = i;
            new Thread(()->{
                for(int j=1;j<=5;j++){
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
