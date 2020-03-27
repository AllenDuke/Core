package example.java;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/3/21
 */
public class ConcurrentContainerTest {

    volatile static int i=0;//i=0为空，i=10为满

    public static void reentrantLock(){
        ReentrantLock lock=new ReentrantLock();
        Condition notEmpty=lock.newCondition();
        Condition notFull=lock.newCondition();
        new Thread(()->{
            lock.lock();
            for(;;){
                if(i==10){
                    System.out.println("已满，生产者暂停工作");
                    notEmpty.signalAll();
                    try {
                        notFull.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else i++;
            }
        }).start();
        new Thread(()->{
            lock.lock();
            for(;;){
                if(i==0){
                    System.out.println("已空，消费者暂停工作");
                    notFull.signalAll();
                    try {
                        notEmpty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else i--;
            }
        }).start();
    }

    public static void countdownlatch(){
        CountDownLatch countDownLatch=new CountDownLatch(5);//相当于倒计时，不可重用
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                countDownLatch.countDown();//最低减到0，因为内部发现当前是0后直接返回
            }).start();
        }
        try {
            countDownLatch.await();//调用后等待减0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void cyclicbarrier(){
        CyclicBarrier cyclicBarrier=new CyclicBarrier(5);//相当于栅栏，可重用
        for (int i = 0; i < 4; i++) {
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + " is ready");
                    /**
                     * 先原子性-1，再判断是否为0，是则5个人去cas地把state设回5，接着往下执行，不是就自旋，
                     * 当最后一个人到达时唤醒全部，CyclicBarrier进入下一代。
                     */
                    cyclicBarrier.await();
                    Random random =new Random();
                    double time = random.nextDouble() + 9;
                    System.out.println(Thread.currentThread().getName() + ": "+ time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public static void reentrantreadwritelock() throws InterruptedException {
        /**
         * 读写锁
         * 读读共享
         * 读写互斥
         * 写写互斥
         */
        ReentrantReadWriteLock lock=new ReentrantReadWriteLock();

//        lock.readLock().lock();//在要去获得写锁时，要写释放已获得的读锁，否则死锁
//        lock.writeLock().lock();

//        lock.writeLock().lock();//在已获得写锁时，可以继续去获得读锁
//        lock.readLock().lock();

        //当获得写锁后，其他线程不可读
        new Thread(()->{
            lock.writeLock().lock();
            System.out.println("写锁获得");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.writeLock().unlock();
            System.out.println("写锁释放");
        }).start();
        Thread.sleep(1000);//先让写锁获得
        new Thread(()->{
            lock.readLock().lock();
            System.out.println("读锁获得");
            lock.readLock().unlock();
            System.out.println("读锁释放");
        }).start();

        //当获得读锁后，其他线程不可写
        new Thread(()->{
            lock.readLock().lock();
            System.out.println("读锁获得");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("读锁释放");
            lock.readLock().unlock();
        }).start();
        Thread.sleep(1000);//先让读锁获得
        new Thread(()->{
            lock.writeLock().lock();
            System.out.println("写锁获得");
            System.out.println("写锁释放");
            lock.writeLock().unlock();
        }).start();
    }

    public static void main(String[] args) {
        reentrantLock();
    }

}
