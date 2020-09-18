package com.github.AllenDuke.interfaceTest;

/**
 * @author 杜科
 * @description 测试实现多个接口，这些接口中，函数同名
 * @contact AllenDuke@163.com
 * @date 2020/9/6
 */
public class InterfaceTest implements Interface1, Interface2{

    public static void main(String[] args) {
        new InterfaceTest().fun();
    }
}
