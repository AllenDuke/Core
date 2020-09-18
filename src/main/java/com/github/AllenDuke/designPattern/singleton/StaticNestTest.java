package com.github.AllenDuke.designPattern.singleton;

/**
 * @author 杜科
 * @description 线程安全 懒汉
 * @contact AllenDuke@163.com
 * @date 2020/9/5
 */
public class StaticNestTest {

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                getSingletonInstance();
            }).start();
        }
    }

    private StaticNestTest(){
        System.out.println("new DoubleCheckTest");
    }

    public static StaticNestTest getSingletonInstance(){
        /**
         * 多个线程尝试加载Nest，但类的加载是线程安全，只有成功竞争到锁的线程才会去加载并初始化Nest。
         * 可结合因类的初始化而造成的死锁问题考虑。
         */
        return Nest.singleton;
    }

    private static class Nest{
        private static StaticNestTest singleton=new StaticNestTest();
    }
}
