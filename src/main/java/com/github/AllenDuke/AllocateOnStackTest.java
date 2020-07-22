package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 测试栈上分配
 * -server -Xmx15m -Xms15m -XX:-DoEscapeAnalysis -XX:+PrintGC -XX:-UseTLAB -XX:-EliminateAllocations
 * DoEscapeAnalysis 逃逸分析 默认开启，
 * EliminateAllocations 标量替换 建立在逃逸分析的基础上，默认开启
 * 不开启的话将发生大量GC
 * @contact AllenDuke@163.com
 * @date 2020/3/16
 */
public class AllocateOnStackTest {

    public static void main(String[] args) {
        for(int i=0;i<1000000000;i++){
            new User();
        }
    }
}
