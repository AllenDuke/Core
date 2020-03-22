package example.java;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author 杜科
 * @description 测试cas的ABA问题
 * @contact AllenDuke@163.com
 * @date 2020/3/18
 */
public class CASAndABATest {

    public static void main(String[] args) {
        AtomicInteger i=new AtomicInteger(10);
        i.compareAndSet(10,11);//ABA问题

        //知道期间变化过，但不知道变化了多少次，其实是原子性地比较两个变量，比较值，比较版本
        AtomicMarkableReference<Integer> mi=new AtomicMarkableReference<>(1,false);
        mi.compareAndSet(1,2,false,true);

        //知道期间变化了多少次
        AtomicStampedReference<Integer> si=new AtomicStampedReference<>(3,0);
        si.compareAndSet(3,4,0,1);
    }
}
