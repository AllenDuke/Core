package example.java;

import sun.reflect.Reflection;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/5/15
 */
public class ReflectionTest {

    public static void main(String[] args) {
        staticTest();
        new ReflectionTest().test();

    }

    public static void staticTest(){
        System.out.println(Reflection.getCallerClass(1));
    }

    public void test(){
        System.out.println(Reflection.getCallerClass(2));
    }
}
