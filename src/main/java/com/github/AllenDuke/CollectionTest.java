package com.github.AllenDuke;

import java.util.*;

/**
 * @author 杜科
 * @description 集合用法简单示例
 * @contact AllenDuke@163.com
 * @date 2020/3/19
 */
public class CollectionTest {

    public static void main(String[] args) {
        Queue<Integer> qi=new ArrayDeque<>();//非空
        qi.add(1);
        qi.offer(2);//add与offer其实都是调用了同一个方法addLast
        qi.peek();
        qi.poll();

        Queue<Integer> lqi=new LinkedList<>();
        lqi.add(1);
        lqi.offer(2);//add与offer其实都是调用了同一个方法linkLast
        lqi.peek();
        lqi.poll();

        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(1,2);
        list.get(1);
        list.remove(0);//依据下标删除
        list.remove(Integer.valueOf(2));//for循环遍历，判断equals

        Vector vector;

        /**
         * 二叉堆形式。
         * 无序数组中取第k大，可以创一个大小为k的小顶堆，经堆排序后，堆顶即为第k大，也可利用快排思想
         */
        PriorityQueue<Integer> queue = new PriorityQueue<>();//小顶堆
        queue.add(3);
        queue.add(5);
        queue.add(1);
        queue.add(2);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
