package com.github.AllenDuke;

public class Application {

    public static void main(String[] args) {
        System.loadLibrary("hello");

        new MyThread().run0();
    }

}
