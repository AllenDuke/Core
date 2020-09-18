package com.github.AllenDuke.concurrentTest;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/9/1
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.tryLock();
    }
}
