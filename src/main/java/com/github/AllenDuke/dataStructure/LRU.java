package com.github.AllenDuke.dataStructure;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/13
 */
public class LRU<K,V> {

    class Node{
        K k;
        V v;
        Node pre;
        Node next;
    }

    Node head;

    Node tail;

    int capacity;

    Map<K,Node> map=new HashMap<>();

    public LRU(){
        this.capacity=10;
    }

    /* capacity>0 */
    public LRU(int capacity){
        this.capacity=capacity;
    }

    public V put(K k,V v){
        Node node = map.get(k);

        if(node!=null){ /* 存在旧值，那么更新值，将node置回链尾 */
            V oldV=node.v;
            node.v=v;
            afterVisited(node);
            return oldV;
        }

        /* 属于新增 */
        node=new Node();
        node.k=k;
        node.v=v;
        map.put(k,node);

        if(head==null){
            head=node;
            tail=node;
            head.next=tail;
            tail.pre=head;
            return null;
        }

        afterVisited(node);

        if(map.size()>capacity) removeOldest();

        return null;
    }

    public V get(K k){
        Node node = map.get(k);
        if(node==null) return null;
        afterVisited(node);
        return node.v;
    }

    public V remove(K k){
        Node node = map.get(k);
        if(node==null) return null;
        map.remove(k);

        if(node==tail&&node==head){
            head=null;
            tail=null;
            return node.v;
        }

        if(node==head){
            head=node.next;
            node.next=null;
            head.pre=null;
            return node.v;
        }

        if(node==tail){
            tail=node.pre;
            node.pre=null;
            tail.next=null;
            return node.v;
        }

        node.pre.next=node.next;
        node.next.pre=node.pre;
        node.pre=null;
        node.next=null;
        return node.v;
    }

    /* 删除最老的链头元素 */
    public void removeOldest(){
        map.remove(head.k);
        Node newHead=head.next;
        newHead.pre=null;
        head.next=null;
        head=newHead;
    }

    /* 某个节点被访问到了，要把它调整到链末 */
    public void afterVisited(Node node){
        if(node==tail) return; /* 当前就位于末尾 */

        if(node==head){ /* 当前是链头 */
            head=node.next;
            node.next=null;
            head.pre=null;
        }

        if(node.pre!=null&&node.next!=null){ /* 当前位于中间 */
            /* 将两边连起来 */
            node.pre.next=node.next;
            node.next.pre=node.pre;
            node.pre=null;
            node.next=null;
        }

        /* 将当前置于链尾 */
        tail.next=node;
        node.pre=tail;
        tail=node;
    }
}
