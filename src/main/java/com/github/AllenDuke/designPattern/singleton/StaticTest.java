package com.github.AllenDuke.designPattern.singleton;

/**
 * @author 杜科
 * @description 线程安全 饿汉
 * @contact AllenDuke@163.com
 * @date 2020/9/5
 */
public class StaticTest {

    private static StaticTest singleton=new StaticTest();

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                getSingletonInstance();
            }).start();
        }
    }

    private StaticTest(){
        System.out.println("new StaticTest");
    }

    public static StaticTest getSingletonInstance() {
        return singleton;
    }
}
