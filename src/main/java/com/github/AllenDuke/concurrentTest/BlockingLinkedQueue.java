package com.github.AllenDuke.concurrentTest;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 杜科
 * @description 阻塞队列，优化LinkedBlockingQueue todo 用synchronized来实现
 * @contact AllenDuke@163.com
 * @date 2020/7/24
 */
public class BlockingLinkedQueue<E> {

    private final LinkedList<E> list;

    private final ReentrantLock putLock=new ReentrantLock();
    private final Condition notFull=putLock.newCondition();

    private final ReentrantLock takeLock=new ReentrantLock();
    private final Condition notEmpty=takeLock.newCondition();

    private final int capacity;

    private final AtomicInteger count = new AtomicInteger();

    public BlockingLinkedQueue(int capacity){
        this.capacity=capacity;
        list=new LinkedList<>();
    }

    private void signalNotEmpty() {
        final Lock takeLock = this.takeLock;
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    /**
     * Signals a waiting put. Called only from take/poll.
     */
    private void signalNotFull() {
        final Lock putLock = this.putLock;
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }

    public void add(E e) throws InterruptedException {
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        int oldCount=-1;
        putLock.lockInterruptibly();
        try {
            /**
             * 虽然在这里if也是可行的，但是一般wait在循环保护当中，防止被意外唤醒
             */
            while (count.get()==capacity) notFull.await();
            list.addLast(e);
            System.out.println(Thread.currentThread().getId() + " add " + e);
            oldCount=count.getAndIncrement(); /* 这里单纯用volatile 去修饰一个int count是不足够的 因为可能消费者去修改 */
            if (oldCount+1 < capacity) notFull.signal(); /* 生产者告诉后续的生产者，可以继续生产 */
        }finally {
            putLock.unlock();
        }
        if(oldCount==0) signalNotEmpty(); /* 由空变非空 唤醒消费者 todo 是否可以在putLock解锁前去调用呢 */
    }

    public E take() throws InterruptedException {
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        int oldCount=-1;
        E e;
        takeLock.lockInterruptibly();
        try {
            while (count.get()==0) notEmpty.await();
            e=list.removeFirst();
            System.out.println(Thread.currentThread().getId() + " get " + e);
            oldCount=count.getAndDecrement();
            if(oldCount>1) notEmpty.signal(); /* 消费者告诉后续的消费者，可以继续消费 */
        }finally {
            takeLock.unlock();
        }
        if(oldCount==capacity) signalNotFull(); /* 由满变非满 唤醒消费者 */
        return e;
    }
}
