package com.github.AllenDuke.concurrentTest;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com 使用sychronized实现的LinkedBlockingQueue
 * @date 2020/9/15
 */
public class LinkedBlockingQueueWithSynchronized<E> {
    LinkedList<E> list;

    private Object notFull=new Object();

    private Object notEmpty=new Object();

    private final int capacity;

    private final AtomicInteger count = new AtomicInteger();

    public LinkedBlockingQueueWithSynchronized(int capacity){
        this.capacity=capacity;
        list=new LinkedList<>();
    }

    private void signalNotEmpty() {
        synchronized (notEmpty){
            notEmpty.notify();
        }
    }

    private void signalNotFull() {
        synchronized (notFull){
            notFull.notify();
        }
    }

    public void add(E e) throws InterruptedException {
        int oldCount=-1;
        synchronized (notFull){
            while (count.get()==capacity) notFull.wait();
            list.addLast(e);
            System.out.println(Thread.currentThread().getId() + " add " + e);
            oldCount=count.getAndIncrement(); /* 这里单纯用volatile 去修饰一个int count是不足够的 因为可能消费者去修改 */
            if (oldCount+1 < capacity) notFull.notify(); /* 生产者告诉后续的生产者，可以继续生产 */
        }
        if(oldCount==0) signalNotEmpty(); /* 由空变非空 唤醒消费者 todo 是否可以在putLock解锁前去调用呢 */
    }

    public E take() throws InterruptedException {
        int oldCount=-1;
        E e;
        synchronized (notEmpty){
            while (count.get()==0) notEmpty.wait();
            e=list.removeFirst();
            System.out.println(Thread.currentThread().getId() + " get " + e);
            oldCount=count.getAndDecrement();
            if(oldCount>1) notEmpty.notify(); /* 消费者告诉后续的消费者，可以继续消费 */
        }
        if(oldCount==capacity) signalNotFull(); /* 由满变非满 唤醒消费者 */
        return e;
    }
}
