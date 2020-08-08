package com.github.AllenDuke;

public class MyThread {

    public native void run0();

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " say: " + i);
        }
    }

}
