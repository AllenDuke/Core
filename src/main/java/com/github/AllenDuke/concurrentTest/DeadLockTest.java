package com.github.AllenDuke.concurrentTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 杜科
 * @description 死锁测试
 * @contact AllenDuke@163.com
 * @date 2020/8/15
 */
public class DeadLockTest {

    public static void main(String[] args) {
        Lock lock1=new ReentrantLock();
        Lock lock2=new ReentrantLock();

        Thread thread1=new Thread(()->{
           lock1.lock();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock2.lock();
            lock1.unlock();
            lock2.unlock();
        });

        Thread thread2=new Thread(()->{
            lock2.lock();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock1.lock();
            lock2.unlock();
            lock1.unlock();
        });

        thread1.start();
        thread2.start();
    }
}
