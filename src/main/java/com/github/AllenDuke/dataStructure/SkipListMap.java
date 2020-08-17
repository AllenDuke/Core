package com.github.AllenDuke.dataStructure;

import java.util.Comparator;

/**
 * @author 杜科
 * @description 以跳表为基础的Map 比起TreeMap 支持范围搜索
 * @contact AllenDuke@163.com
 * @date 2020/8/13
 */
public class SkipListMap<K, V> {

    /* 元素个数 */
    private int size;

    /* 虚拟头部 null */
    private Node<K, V> head;

    /* 默认最大为32层 */
    private static final int MAX_LEVEL = 32;

    /* 当前位于第i层，进入第i+1层的概率 */
    private static final double P = 0.25;

    /* 当前头部有效层数，头部的有效层数总是最高的*/
    private int level;

    private Comparator<K> comparator;

    public SkipListMap() {
        this(null);
    }

    public SkipListMap(Comparator<K> comparator) {
        this.comparator = comparator;
        head = new Node<K, V>(null, null, MAX_LEVEL);
    }

    public int size() {
        return size;
    }

    /* 返回旧值 */
    public V put(K k, V v) {
        checkKey(k);
        Node<K, V> cur = this.head;

        /* 暂存新节点的每一层前驱节点，如果不存在旧元素，那么在插入新节点时，需要用到 */
        Node<K,V>[] pres=new Node[level];
        int comp = -1;

        /* 从头部节点，最高一层开始遍历 */
        for (int i = level - 1; i >= 0; i--) {
            while (cur.nexts[i] != null && (comp = compare(k, cur.nexts[i].key)) > 0) {
                cur = cur.nexts[i];
            }

            if (comp == 0) {
                V oldValue = cur.nexts[i].value;
                cur.nexts[i].value = v;
                return oldValue;
            }

            /**
             * 如果不存在旧元素，那么cur为新节点的第i层前驱节点。
             * 为什么不直接直接在这里设置呢？是因为可能在往下的某一层中存在旧元素
             */
            pres[i]=cur;
        }

        /* 新节点层数 */
        int randomLevel = randomLevel();

        /* 新节点 */
        Node<K, V> newNode = new Node<>(k, v, randomLevel);
        for (int i = 0; i <randomLevel ; i++) {
            if(i>=level){
                head.nexts[i]=newNode;
            }else{
                newNode.nexts[i] = pres[i].nexts[i];
                pres[i].nexts[i]=newNode;
            }
        }

        size++;

        /* 更新头部有效层数 */
        level=Math.max(level,randomLevel);
        return null;
    }

    private int randomLevel() {
        int level = 1;

        /* 以概率P往上一层 */
        while ((Math.random() <= P) && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public V get(K k) {
        checkKey(k);
        Node<K, V> node = this.head;
        for (int i = level - 1; i >= 0; i--) {
            int comp = -1;
            while (node.nexts[i] != null && (comp = compare(k, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            if (comp == 0) return node.nexts[i].value;
        }
        return null;
    }

    public V remove(K k) {
        checkKey(k);
        V oldValue =null;
        Node<K, V> node = this.head;
        boolean exist = false;
        int comp = -1;
        for (int i = level - 1; i >= 0; i--) {
            while (node.nexts[i] != null && (comp = compare(k, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }

            if (comp == 0) {
                exist=true;
                oldValue = node.nexts[i].value;
                node.nexts[i] = node.nexts[i].nexts[i];
            }
        }
        if(exist){
            size--;
            //更新跳表层数
            int newLevel=level;
            while (--newLevel>=0 && head.nexts[newLevel]==null){
                level=newLevel;
            }
        }
        return oldValue;
    }

    public int compare(K k1, K k2) {
        return comparator != null ? comparator.compare(k1, k2)
                : ((Comparable<K>) k1).compareTo(k2);
    }

    private void checkKey(K k) {
        if (k == null) throw new IllegalArgumentException("k must not be null.");
    }

    private static class Node<K, V> {
        K key;
        V value;

        /* 存储每一层的下一个节点 */
        Node<K, V>[] nexts;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            nexts = new Node[level];
        }

        @Override
        public String toString() {
            return "{"+key+":"+value+"}"+nexts.length;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共" + level + "层").append("\n");
        for (int i = level - 1; i >= 0; i--) {
            Node<K, V> node = head;
            while (node.nexts[i] != null) {
                sb.append(node.nexts[i]);
                sb.append(" ");
                node = node.nexts[i];
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}