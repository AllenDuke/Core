package com.github.AllenDuke.concurrentTest;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 杜科
 * @description 测试
 * @contact AllenDuke@163.com
 * @date 2020/7/25
 */
public class BlockingLinkedQueueTest {

    public static void main(String[] args) throws InterruptedException {
        multiThreadTest();
    }

    static void singleThreadTest() throws InterruptedException {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(20);
        queue.put(new Object());
    }

    static void multiThreadTest() throws InterruptedException {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>(20);
//        BlockingLinkedQueue queue = new BlockingLinkedQueue(20);
//        LinkedBlockingQueueWithSynchronized<Object> queue = new LinkedBlockingQueueWithSynchronized<>(20);
        System.out.println("start adding...");
        for(int i=0;i<50;i++){
            int finalI = i;
            new Thread(()->{
                for(int j=1;j<=50;j++){
                    try {
                        queue.add(finalI *5+j);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(100);
        System.out.println("start taking...");
        for(int i=0;i<50;i++){
            int finalI = i;
            new Thread(()->{
                for(int j=1;j<=50;j++){
                    try {
                        queue.take();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
