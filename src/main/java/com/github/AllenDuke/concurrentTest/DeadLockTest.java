package com.github.AllenDuke.concurrentTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 杜科
 * @description 死锁测试
 * @contact AllenDuke@163.com
 * @date 2020/8/15
 */
public class DeadLockTest {

    public static void main(String[] args) throws InterruptedException {
        loopWait();
//        spinLock();
//        classLoadLoopWait();
//        suspendWait();
    }

    /* 线程循环等待而造成的死锁 */
    static void loopWait(){
        Lock lock1=new ReentrantLock();
        Lock lock2=new ReentrantLock();

        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(1);

        Thread thread1=new Thread(()->{
            lock1.lock();
            countDownLatch1.countDown();
            try {
                // 比起睡眠更具有确定性
                countDownLatch2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock2.lock();
            lock1.unlock();
            lock2.unlock();
        });

        Thread thread2=new Thread(()->{
            lock2.lock();
            countDownLatch2.countDown();
            try {
                countDownLatch1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock1.lock();
            lock2.unlock();
            lock1.unlock();
        });

        thread1.start();
        thread2.start();
        System.out.println("done");
    }

    static AtomicInteger src=new AtomicInteger(1);

    /* 由自旋锁重入而造成的死锁 */
    static void spinLock(){
        while(src.get()==0);
        while (src.compareAndSet(1,0));
        spinLock(); /* 由自旋锁重入而造成的死锁 */
//        src.set(0);
    }

    /* 类加载不当而造成的死锁 同样是线程循环等待而造成 子类父类的加载造成的死锁问题更为隐蔽 */
    static void classLoadLoopWait(){
        new Thread(()->A.test()).start();
        new Thread(()->B.test()).start();
    }

    /**
     * 类的加载是线程安全的。
     * 当多个线程尝试去加载同一个类时，只有成功持有该类相关的一个锁的线程才会去加载，在加载初始化期间一直持有该锁。
     */
    public static class A {

        static {
            System.out.println("class A init.");
            /* A还没完全初始化的时候尝试去加载B */
            B b = new B();
        }

        public static void test() {
            System.out.println("method test called in class A");
        }
    }

    public static class B {

        static {
            System.out.println("class B init.");
            /* B还没完全初始化的时候尝试去加载A */
            A a = new A();
        }

        public static void test() {
            System.out.println("method test called in class B");
        }
    }

    /* 使用subspend不当而造成的死锁 */
    static void suspendWait() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while(true) System.out.println("running..."); /* out是线程安全的 */
        });
        thread.start();
        Thread.sleep(1000);
        thread.suspend(); /* suspend不释放锁，此时thread持有out相关的锁 但从thread的线程状态上看,居然还是Runnable*/
        /* 主线程请求out相关的锁，而持有这个锁的线程thread已经suspend了，也没有第三线程来将thread resume，故而死锁了 */
        System.out.println("thread suspended");
        Thread.sleep(1000);
        System.out.println("thread resumed");
        thread.resume();
    }


}
