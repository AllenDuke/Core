package com.github.AllenDuke;

import java.util.*;

/**
 * @author 杜科
 * @description 利用LinkedHashMap实现LRU
 * @contact AllenDuke@163.com
 * @date 2020/3/19
 */
public class LruTest<K,V> extends LinkedHashMap<K,V> {

    int cacheCeiling=6;//缓存上限

    /**
     * @description: 调用父类的无参构造
     * @param initialCapacity 初始容量
     * @param loadFactor 加载因子
     * @param accessOrder 访问顺序，true为开启，false为关闭（使用插入顺序）
     * @return:
     * @author: 杜科
     * @date: 2020/3/19
     */
    public LruTest(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    /**
     * @description: 当大小达到缓存上限时，删除最旧的节点
     * @param eldest 最旧的节点
     * @return: boolean true为要删除，false为不删除
     * @author: 杜科
     * @date: 2020/3/19
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        if(size()>cacheCeiling) return true;
        else return false;
    }

}
