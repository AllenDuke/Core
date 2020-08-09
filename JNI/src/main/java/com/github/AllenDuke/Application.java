package com.github.AllenDuke;

public class Application {

    public static void main(String[] args) {
        System.loadLibrary("JNITest");

        CThread cThread = new CThread();
        cThread.target=new Runnable() {
            @Override
            public void run() {
                System.out.println("test succeed");
            }
        };
        cThread.start();
    }

}
