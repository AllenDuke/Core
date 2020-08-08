package com.github.AllenDuke;


/**
 * @author 杜科
 * @description 测试intern方法
 * @contact AllenDuke@163.com
 * @date 2020/8/2
 */
public class StringTest {

    public static void main(String[] args) {
        String s1="aa";
        String s2=new String("aa");
        System.out.println(s1 == s2);
        /**
         * intern方法，
         * 如果常量池中有“aa"，那么直接返回常量池"aa"的引用。
         * 如果没有，那么复制一份“aa"放到常量池中，再返回常量池中”aa"的引用。
         * 1.8后，字符串常量池也在堆中。
         */
        String s3 = s2.intern();
        System.out.println(s3 == s2);
        System.out.println(s1 == s3);

        System.out.println("----");

        String s4 = new String("bb");
        String s5 = s4.intern();
        System.out.println(s4 == s5);
        /**
         * 如果常量池中已经有了，那么直接返回常量池中的引用
         */
        String s6="bb";
        System.out.println(s4 == s6);
        System.out.println(s5 == s6);

    }
}
