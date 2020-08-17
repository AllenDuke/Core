package com.github.AllenDuke.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 杜科
 * @description 代理调用处理器 测试jdk动态代理 理解springaop方法内部调用时拦截失败的原因
 * @contact AllenDuke@163.com
 * @since 2020/1/8
 */
public class MyInvokeHandler implements InvocationHandler {

    Subject target;

    public MyInvokeHandler(Subject target){this.target=target;}

    /**
     * @description:
     * @param proxy 生成的代理类
     * @param method 代理类实现了的接口中的方法,invoke里面的参数要为接口的instance
     * @param args method方法的参数
     * @return: java.lang.Object
     * @author: 杜科
     * @date: 2020/1/8
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        System.out.println(method.getDeclaringClass().getName());
        Object result = method.invoke(target, args);
        System.out.println("after");
        return null;
    }
    public static void main(String[] args) {
        // 保存生成的代理类的字节码文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Sayable proxy=(Sayable) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{Sayable.class},new MyInvokeHandler(new Subject()));
        proxy.say1();
        proxy.say2();
        //System.out.println(proxy.toString());
    }
}
