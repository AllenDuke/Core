package com.github.AllenDuke.concurrentTest;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/9/2
 */
public class LongAdderTest {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();

        for (int j = 0; j < 2; j++) {
            Thread thread = new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    longAdder.increment();
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(longAdder);
    }
}
