package com.github.AllenDuke.dataStructure;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/26
 */
public class LRUTest {

    public static void main(String[] args) {
        LRU<Integer, Integer> lru = new LRU<>();
        lru.put(6,14);
        lru.put(10,11);
        lru.put(11,4);
        lru.put(12,14);
        lru.put(5,18);
        lru.put(7,23);
        lru.put(3,27);
        lru.put(2,9);
        lru.put(13,4);
        lru.put(8,18);
        lru.put(1,7);
        System.out.println(lru.get(6));
    }
}
