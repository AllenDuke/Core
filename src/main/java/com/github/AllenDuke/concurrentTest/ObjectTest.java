package com.github.AllenDuke.concurrentTest;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/29
 */
public class ObjectTest {

    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        /* wait在同步区域内调用，释放锁 */
        synchronized (o) {
            o.wait();
        }
    }
}
