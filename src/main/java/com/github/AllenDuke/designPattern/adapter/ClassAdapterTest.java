package com.github.AllenDuke.designPattern.adapter;

/**
 * @author 杜科
 * @description 类适配器模式
 * @contact AllenDuke@163.com
 * @date 2020/9/6
 */
public class ClassAdapterTest extends Adaptee implements Target{

    @Override
    public void function1() {
        super.function1();
        function2();
    }


    @Override
    public void function2() {

    }
}
