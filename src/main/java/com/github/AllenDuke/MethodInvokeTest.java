package com.github.AllenDuke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 杜科
 * @description 测试反射调用
 * @contact AllenDuke@163.com
 * @date 2020/5/4
 */
public class MethodInvokeTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        Class<MethodInvokeTest> methodInvokeTestClass = MethodInvokeTest.class;
        Method test = methodInvokeTestClass.getMethod("test", String.class);
        /**
         * 当invoke次数少于15时，使用的是native方法调用
         * 当invoke次数超过15时，将生成一个类，类似动态代理一样的机制
         */
        for (int i = 0; i < 16; i++) {
            test.invoke(new MethodInvokeTest(), "test");
        }
    }

    public void test(String s) {
        System.out.println(s);
    }
}
//    package sun.reflect;
//
//    public class GeneratedMethodAccessor1 extends MethodAccessorImpl {
//        public GeneratedMethodAccessor1() {
//            super();
//        }
//
//        public Object invoke(Object obj, Object[] args)
//                throws IllegalArgumentException, InvocationTargetException {
//            // prepare the target and parameters
//            if (obj == null) throw new NullPointerException();
//            try {
//                MethodInvokeTest target = (MethodInvokeTest) obj;
//                if (args.length != 1) throw new IllegalArgumentException();
//                String arg0 = (String) args[0];
//            } catch (ClassCastException e) {
//                throw new IllegalArgumentException(e.toString());
//            } catch (NullPointerException e) {
//                throw new IllegalArgumentException(e.toString());
//            }
//            // make the invocation
//            try {
//                target.test(arg0);
//            } catch (Throwable t) {
//                throw new InvocationTargetException(t);
//            }
//        }
//    }

