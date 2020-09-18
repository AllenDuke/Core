package com.github.AllenDuke.dataStructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 杜科
 * @description 测试大数量，ArrayList(顺序存储，得益于cpu缓存行等等) LinkedList遍历耗时
 * @contact AllenDuke@163.com
 * @date 2020/9/14
 */
public class ListTest {

    static int sum=1000*10000;

    static List<Object> arrayList=new ArrayList<>(sum);

    static List<Object> linkedList=new LinkedList<>();

    public static void main(String[] args) {
        insert();
        arrayTrace();
        linkedTrace();
    }

    static void insert(){
        for (int i = 0; i < sum; i++) {
            Object o = new Object();
            arrayList.add(o);
            linkedList.add(o);
        }
    }

    static void arrayTrace(){
        long start=System.currentTimeMillis();
        for (int i = 0; i < sum; i++) {
            arrayList.get(i);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    static void linkedTrace(){
        long start=System.currentTimeMillis();
        for (Object o : linkedList) {

        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
