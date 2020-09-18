package com.github.AllenDuke.math;

/**
 * @author 杜科
 * @description 测试异或运算速度 异或运算稍慢与加法运算、减法运算
 * todo 在牛客的在线编程中，减法比加法耗时
 * @contact AllenDuke@163.com
 * @date 2020/7/29
 */
public class XORTest {

    static long count=10000000000L; /* volatile变量会加重内存读写的负担 */

    static long c;

    public static void main(String[] args) {
        testAdd();
        testSub();
        testXor();
    }

    /**
     * c 的运算速度似乎是 java 的1.5倍
     */

    static void testAdd(){
        long start=System.currentTimeMillis();
        int t = 0; /* 意思是，在c层面上，声明一个寄存器变量 */
        /**
         * todo 尽量对c一次过更新，避免对内存频繁的读写
         */
        for(long i=0;i<count;i++) t++;
        c=t;
        System.out.println(System.currentTimeMillis() - start);
    }

    private static void testSub() {
        long start=System.currentTimeMillis();
        /**
         * todo 单纯的减法或者赋值运算并不耗时 但是内存读取和写入就很耗时了
         */
        for(long i=0;i<count;i++) c++;
        System.out.println(System.currentTimeMillis() - start);
    }

    static void testXor(){
        long start=System.currentTimeMillis();
        for(long i=0;i<count;i++) c^=i;
        System.out.println(System.currentTimeMillis() - start);
    }


}
