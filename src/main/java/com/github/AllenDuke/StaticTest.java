package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 测试静态域
 * @contact AllenDuke@163.com
 * @date 2020/4/20
 */
public class StaticTest {

    //存放在元空间的字节码静态区域。
    static int a=1;

    int b=2;

    public static void main(String[] args) {
        StaticTest staticTest = new StaticTest();
        Class<? extends StaticTest> staticTestClass = staticTest.getClass();

    }

    void test(){
        a=2;
    }
}
