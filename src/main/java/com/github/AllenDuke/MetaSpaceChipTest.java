package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 测试元空间的碎片化而导致的FullGC
 * -XX:MetaspaceSize=50M -XX:MaxMetaspaceSize=100M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+UseConcMarkSweepGC
 * @contact AllenDuke@163.com
 * @date 2020/7/25
 */
public class MetaSpaceChipTest {

    public static void main(String[] args) throws Exception {
        while (true) {
            /**
             * 每个类加载器有单独的存储空间，类型不同，分配的空间也不同。
             * 当加载器过多而每个加载器只加载少量的类时，会造成内存浪费和碎片化，容易触发FullGC且难以回收
             */
            Class clazz0 = new MyClassLoader().loadClass("com.github.AllenDuke.MyClassLoader");
        }
    }
}
