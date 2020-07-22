package com.github.AllenDuke;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/7/20
 */
public class PremainTest {

    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("main start");
        Class.forName("com.github.AllenDuke.SDS");
        System.out.println("main end");
    }


}
