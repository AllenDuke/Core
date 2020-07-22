package com.github.AllenDuke;

import java.util.ArrayList;

/**
 * @author 杜科
 * @description -Xmx5m -Xms5m
 * @contact AllenDuke@163.com
 * @date 2020/5/22
 */
public class OutOfMemoryTest {

    static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        try{
//            while(true){
//                users.add(new User());//为什么发生GC时，会被回收？
//            }
            allocate();
        }catch (Throwable throwable){
            System.out.println(throwable.toString());
            users.clear();
            System.out.println("释放后，gc时可以回收内存，继续执行不会OOM");
        }
        for (int i = 0; i < 1000; i++) {
            users.add(new User());
        }
        print();
    }

    public static void print(){
        System.out.println(users.size());
    }

    public static void allocate(){
        while(true){
            users.add(new User());//为什么发生GC时，会被回收？
        }
    }
}
