package com.github.AllenDuke;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/4/5
 */
public class NewTest {

    //按声明顺序执行
    static {
        a=6;
    }

    private static int a=3;

    public static void main(String[] args) {
        System.out.println(a);
    }
}
