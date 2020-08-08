package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 测试异或运算速度 异或运算稍慢与加法运算
 * @contact AllenDuke@163.com
 * @date 2020/7/29
 */
public class XORTest {

    static int count=1000000;


    public static void main(String[] args) {
        testAdd(1354321,52153);
        testXor(1354321,52153);
    }

    static void testAdd(int a,int b){
        long start=System.currentTimeMillis();
        int c;
        for(int i=0;i<count;i++) c=a+b;
        System.out.println(System.currentTimeMillis() - start);
    }

    static void testXor(int a,int b){
        long start=System.currentTimeMillis();
        int c;
        for(int i=0;i<count;i++) c=a^b;
        System.out.println(System.currentTimeMillis() - start);
    }
}
