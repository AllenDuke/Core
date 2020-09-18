package com.github.AllenDuke.concurrentTest;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/9/9
 */
public class SuspendAndResumeTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while(true) System.out.println("running...");
        });
        thread.start();
        Thread.sleep(1000);
        thread.suspend();
        System.out.println("thread suspended");
        Thread.sleep(1000);
        System.out.println("thread resumed");
        thread.resume();
    }
}
