package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 定制化的HashMap
 * @contact AllenDuke@163.com
 * @date 2020/7/16
 */
public class LongHashMap<V> {

    private class LongEntry<V> {

        long key;

        V value;

        LongEntry next;
    }

    private LongEntry[] table;

    private int capacity;//2的n次方

    public LongHashMap(int capacity) {
//        if(!checkCapacity(capacity)) throw new RuntimeException("capacity 不是2的n次方");
        this.capacity = capacity;
        table = new LongEntry[capacity];
    }

    //检查容量是否为2的n次方 按位检查1的个数
    private boolean checkCapacity(int capacity) {
        int oneCount = 0;
        for (int i = 0; i < 32; i++) {
            if ((capacity & 1) == 0) oneCount++;
            capacity <<= 1;
        }
        if (oneCount == 1) return true;
        else return false;
    }

    //todo 更为更优秀的hash函数
    private int indexFor(long key, int length) {
        return (int) (key & (length - 1));
    }

    public void put(long key, V value) {
        LongEntry<V> entry = new LongEntry<>();
        entry.key = key;
        entry.value = value;
        int i = indexFor(key, table.length);
        LongEntry head = table[i];
        if (head == null) {
            table[i] = entry;
            return;
        }
        while (head.next != null) head = head.next;
        head.next = entry;
    }

    public V get(int key) {
        int i = indexFor(key, table.length);
        LongEntry<V> head = table[i];
        while (true) {
            if (head == null) return null;//不做检查使用者保证，减少运算
            if (head.key == key) return head.value;
            head = head.next;
        }
    }
}
