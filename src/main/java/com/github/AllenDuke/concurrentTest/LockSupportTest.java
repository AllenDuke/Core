package com.github.AllenDuke.concurrentTest;

import lombok.SneakyThrows;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

/**
 * @author 杜科
 * @description 测试LockSupport.park无故返回，似乎测不出来
 * @contact AllenDuke@163.com
 * @date 2020/8/6
 */
public class LockSupportTest {

    static Map<Integer,Object> map=new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            Task1 task1=new Task1();
            Task2 task2=new Task2();

            task1.round=i;
            task2.round=i;

            Thread thread1 = new Thread(task1);
            Thread thread2=new Thread(task2);

            task1.target=thread2;
            task2.target=thread1;

            thread1.start();
            Thread.sleep(100);
            thread2.start();
        }

    }

    static class Task1 implements Runnable{

        int round;

        Thread target;

        @Override
        public void run() {
            while (true){
                LockSupport.park();
                Object object=map.get(round);
                if(object==null){
                    System.out.println("park 失败");
                    break;
                }
                System.out.println("next");
                map.remove(round);
                LockSupport.unpark(target);
            }
        }
    }

    static class Task2 implements Runnable{

        int round;

        Thread target;

        @SneakyThrows
        @Override
        public void run() {
            while (true){
                while (true){
                    map.put(round,new Object());
                    LockSupport.unpark(target);
                    LockSupport.park();
                    Thread.sleep(100);
                }
            }
        }
    }


}
