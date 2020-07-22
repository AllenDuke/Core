package com.github.AllenDuke;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/3/20
 */
public class SynchronizedTest {

    public static void main(String[] args) {
        SynchronizedTest o=new SynchronizedTest();
        int a=0;
        try {
            synchronized (o){
                a=1;
                /**
                 * 异常前的指令已经执行
                 */
                int i=a/0;
                a=2;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(a);
        }


    }

    public synchronized static void synchronizedStatic(){
        int i=0;
    }

    public synchronized void synchronizedMember(){
        int i=1;
    }
}
