package com.github.AllenDuke.concurrentTest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/9/3
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 3000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        executor.isShutdown(); /* 当调用shutdown()或shutdownNow()方法后返回为true */
        executor.isTerminating();
        executor.isTerminated(); /* 调用shutDown后，任务完成时为true | 当调用shutdownNow()方法后，成功停止后返回为true;*/
        executor.awaitTermination(1000,TimeUnit.MILLISECONDS);
    }
}
