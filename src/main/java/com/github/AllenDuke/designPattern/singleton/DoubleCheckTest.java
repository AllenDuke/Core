package com.github.AllenDuke.designPattern.singleton;

/**
 * @author 杜科
 * @description 线程安全 懒汉
 * @contact AllenDuke@163.com
 * @date 2020/9/5
 */
public class DoubleCheckTest {

    private static volatile DoubleCheckTest singleton;

    private DoubleCheckTest(){
        System.out.println("new DoubleCheckTest");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                getSingletonInstance();
            }).start();
        }
    }

    public static DoubleCheckTest getSingletonInstance() {
        if (singleton != null) return singleton;
        synchronized (DoubleCheckTest.class) {
            /* 如果 singleton不使用volatile修饰，那么线程可能会获取到半初始化的对象 */
            if (singleton != null) return singleton;
            singleton = new DoubleCheckTest();
        }
        return singleton;
    }
}
