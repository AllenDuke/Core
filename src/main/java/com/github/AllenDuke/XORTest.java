package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 测试异或运算速度 异或运算稍慢与加法运算、减法运算
 * todo 在牛客的在线编程中，减法比加法耗时
 * @contact AllenDuke@163.com
 * @date 2020/7/29
 */
public class XORTest {

    static long count=10000000000L;

    static int c;

    public static void main(String[] args) {
        testAdd(1354321,52153);
        testSub(1354321,52153);
        testXor(1354321,52153);
    }

    static void testAdd(int a,int b){
        long start=System.currentTimeMillis();
        int t;
        for(long i=0;i<count;i++) t=a+b;
        System.out.println(System.currentTimeMillis() - start);
    }

    private static void testSub(int a, int b) {
        long start=System.currentTimeMillis();
        /**
         * todo 单纯的减法或者赋值运算并不耗时 但是内存和写入就很耗时了
         */
        for(long i=0;i<count;i++) c-=a-b;
        System.out.println(System.currentTimeMillis() - start);
    }

    static void testXor(int a,int b){
        long start=System.currentTimeMillis();
        int c;
        for(long i=0;i<count;i++) c=a^b;
        System.out.println(System.currentTimeMillis() - start);
    }


}
