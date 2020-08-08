package com.github.AllenDuke.concurrentTest;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 杜科
 * @description 阻塞队列，优化LinkedBlockingQueue
 * @contact AllenDuke@163.com
 * @date 2020/7/24
 */
public class BlockingLinkedQueue<E> {

    LinkedList<E> list;

    private Lock putLock=new ReentrantLock();
    private Condition notFull=putLock.newCondition();

    private Lock takeLock=new ReentrantLock();
    private Condition notEmpty=takeLock.newCondition();

    private final int capacity;

    private volatile int size;

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
        putLock.lock();
        /**
         * 虽然在这里if也是可行的，但是一半wait在循环保护当中，防止被意外唤醒
         */
        while (size==capacity) notFull.await();
        list.addLast(e);
        System.out.println(Thread.currentThread().getId() + " add " + e);
        size++;
        putLock.unlock();
        signalNotEmpty();
    }

    public E take() throws InterruptedException {
        takeLock.lock();
        while (size==0) notEmpty.await();
        E e=list.removeFirst();
        System.out.println(Thread.currentThread().getId() + " get " + e);
        size--;
        takeLock.unlock();
        signalNotFull();
        return e;
    }
}
