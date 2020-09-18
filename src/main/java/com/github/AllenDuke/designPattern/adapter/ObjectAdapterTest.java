package com.github.AllenDuke.designPattern.adapter;

/**
 * @author 杜科
 * @description 对象适配器模式
 * @contact AllenDuke@163.com
 * @date 2020/9/6
 */
public class ObjectAdapterTest implements Target{

    Adaptee adaptee;

    @Override
    public void function1() {
        adaptee.function1();
        function2();
    }

    @Override
    public void function2() {

    }
}
