package com.github.AllenDuke;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author 杜科
 * @description Javaagent。从名字上看，似乎是个 Java 代理之类的，而实际上，他的功能更像是一个Class类型的转换器，
 * 他可以在运行时接受重新外部请求，对Class类型进行修改。
 * @contact AllenDuke@163.com
 * @date 2020/7/20
 */
public class PreMainTraceAgent {

    /**
     * 异步执行 premain 方法，大部分类加载都会通过该方法，注意：是大部分，不是所有。当然，遗漏的主要是系统类，
     * 因为很多系统类先于 agent 执行，而用户类的加载肯定是会被拦截的。也就是说，这个方法是在 main 方法启动前拦截大部分类的加载活动。
     * 当加载了主类时，主线程开始执行，但加载线程仍在运行，当主线程在运行中，要加载类时，仍会被拦截。
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs : " + agentArgs);
        inst.addTransformer(new DefineTransformer(), true);
    }

    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("premain load Class:" + className);
            return classfileBuffer;
        }
    }
}
