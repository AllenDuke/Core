package example.java;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/4/20
 */
public class ThreadSynchronizeTest {

    volatile static int activeThreadId=1;

    static Lock lock=new ReentrantLock();

    public static void main(String[] args) {

    }

    public static void test1(){
        new Thread(()->{
            while (true){
                while(activeThreadId!=1);
                System.out.println(1);
                activeThreadId=2;
            }
        }).start();

        new Thread(()->{
            while (true){
                while(activeThreadId!=2);
                System.out.println(2);
                activeThreadId=3;
            }
        }).start();

        new Thread(()->{
            while (true){
                while(activeThreadId!=3);
                System.out.println(3);
                activeThreadId=1;
            }
        }).start();
    }

    static Thread thread1;

    static Thread thread2;

    static Thread thread3;

    //unpark可以先授予许可
    public static void test2(){
        thread1 = new Thread(() -> {
            while (true) {
                System.out.println(1);
                LockSupport.unpark(thread2);
                LockSupport.park();
            }
        });

        thread2 = new Thread(() -> {
            while (true) {
                LockSupport.park();
                System.out.println(2);
                LockSupport.unpark(thread3);
            }
        });

        thread3 = new Thread(() -> {
            while (true) {
                LockSupport.park();
                System.out.println(3);
                LockSupport.unpark(thread1);
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
