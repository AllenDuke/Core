package com.github.AllenDuke.concurrentTest;

import java.util.concurrent.TimeUnit;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/15
 */
public class VolatileArrayTest {


    //注意没用volatile修饰数组
    public long flag = 0;

    //这里用volatile修饰另一个变量
    public volatile int vol = 0;

    public static void main(String[] args) throws Exception {
        VolatileArrayTest test = new VolatileArrayTest();
        //线程1
        new Thread(new Thread(){
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    test.flag = 2;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //线程2
        new Thread(new Thread(){
            @Override
            public void run() {
                while (true) {
                    //读取vol的值
                    int i = test.vol;
                    /**
                     * todo 为什么这里可以读取到呢
                     */
                    if (test.flag == 2) {
                        break;
                    }
                }
                System.out.println("Jump out of the loop!");
            }
        }).start();
    }

}
