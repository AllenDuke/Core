package com.github.AllenDuke.dynamicProxy;

/**
 * @author 杜科
 * @description 被代理人
 * @contact AllenDuke@163.com
 * @since 2020/1/8
 */
public class Subject implements Sayable{

    @Override
    public void say1(){
        System.out.println("say something 1");
        say2();
    }

    @Override
    public void say2() {
        System.out.println("say something 2");
    }
}
