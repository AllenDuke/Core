package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 测试ThreadLocal内存泄漏 -Xmx300m -Xms300m -XX:+PrintGC
 * @contact AllenDuke@163.com
 * @date 2020/7/21
 */
public class ThreadLocalTest {

    static class BigObject{
        private byte[] data = new byte[1024 * 1024 * 50];
    }

    static ThreadLocal<BigObject> threadLocal=new ThreadLocal<>();

    static BigObject bigObject;

    public static void main(String[] args) throws InterruptedException {
        bigObject = new BigObject();
        threadLocal.set(new BigObject());
        Thread.sleep(100);
        threadLocal=null;//对程序来说，已成为垃圾
        bigObject=null;
        for(int i=0;i<10000000;i++){//促使一次GC
            new User();
        }
        //通过GC日志，或者JVisualVM可知，仍然有50MB未得回收，而这50MB则是来自ThreadLocal。内存泄漏

        while (true);
    }

}
