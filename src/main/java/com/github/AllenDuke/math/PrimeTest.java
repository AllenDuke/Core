package com.github.AllenDuke.math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 杜科
 * @description 多线程找出1亿以内的所有素数
 * @contact AllenDuke@163.com
 * @date 2020/9/25
 */
public class PrimeTest {

    static int count=100000000; /* 1亿 */

    static boolean[] prime1=new boolean[count+1]; /* true为素数 */

    static boolean[] normal3=new boolean[count+1]; /* false为素数 */

    public static void main(String[] args) throws InterruptedException {
        test1();
        test3();
        System.out.println(isTheSame(prime1, normal3));
    }

    public static boolean isTheSame(boolean[] arr1,boolean[] arr2){
        if(arr1.length!=arr2.length) return false;
        for (int i = 0; i < arr1.length; i++) {
            if(arr1[i]==arr2[i]) { /* 注意着两个数组的性质 */
                System.out.println(i+" "+arr1[i]+" "+arr2[i]);
                return false;
            }
        }
        return true;
    }

    /* cpu密集型任务，最优线程数为cpu核数*2 */

    /* cpu核数*2个线程数切割并行读 count=1亿时，需要47秒*/
    public static void test1() throws InterruptedException {
        long start=System.currentTimeMillis();
        int threadCount = Runtime.getRuntime().availableProcessors() * 2;
        int len=count/threadCount; /* 每个线程大概处理长度 */
        List<Thread> threads=new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            int begin=i*len;
            int end=begin+len;
            if(i==threadCount-1) end=count; /* 最后一个线程负责多一点 */
            int finalEnd = end;
            Thread thread = new Thread(() -> {
                for (int l = begin; l < finalEnd; l++) {
                    if (isPrime(l)) prime1[l] = true;
                }
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long end=System.currentTimeMillis();
        System.out.println(end - start);
    }

    public static boolean isPrime(int n){
        if(n==2) return true;
        /* 如果n存在一对因子，(int)Math.sqrt(n)+1会是它最大的小因子 */
        for(int i=2;i<=(int)Math.sqrt(n)+1;i++)
            if(n%i==0) return false;
        return true;
    }

    /* forkjoin框架 */
    public static void test2(){

    }

    /* 生产者消费者模型，单线程生产，多线程消费 */
    public static void test3() throws InterruptedException {
        long start=System.currentTimeMillis();
        int threadCount = Runtime.getRuntime().availableProcessors() * 2-1;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threadCount, threadCount, 10,
                TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        Thread thread = new Thread(() -> {
            normal3[0]=false;
            normal3[1]=false;
            for (int i = 2; i <= Math.sqrt(count) + 1; i++) {
                /**
                 * 类似埃氏筛法的思想，当前线程生产素数，然后将这些素数交给线程池，线程池将这些素数的倍数全部设置为正常数。
                 * 为什么生产者只需要生产到 Math.sqrt(count)+1 ?
                 * 逆向思维，我们假定所有数都是素数的，找出所有的正常数。
                 * 同样的，如果 大于 Math.sqrt(count) + 1的某个 n 存在一对因子，(int)Math.sqrt(n)+1会是它最大的小因子，即 n 必定已被标记为正常。
                 * 当然可以利用jvisualvm来观察线程运行状况，从而来调整生产者和消费者的比值。
                 */
                if (isPrime(i)) {
                    normal3[i] = false;
                    final int base = i;
                    executor.execute(() -> {
                        for (int time = 2; base * time <= count; time++) {
                            int cur = base * time;
                            normal3[cur] = true;
                        }
                    });
                }
            }
        });
        thread.start();
        thread.join();
        executor.shutdown();
        executor.awaitTermination(60,TimeUnit.SECONDS);
        long end=System.currentTimeMillis();
        System.out.println(end - start);
    }

}
