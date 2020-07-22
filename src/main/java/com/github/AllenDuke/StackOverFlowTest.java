package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 测试 StackOverFlowTest与栈大小，栈帧大小，递归层数的关系
 * 虚拟机栈默认1024KB -Xss160k 最少160KB
 * @contact AllenDuke@163.com
 * @date 2020/4/18
 */
public class StackOverFlowTest {

    public static void main(String[] args) {
        try {
            trace1(1);
        }catch (Throwable throwable){
            System.out.println(throwable.toString());
        }
        for (int i = 0; i < 1000; i++) {
            new User();
        }
        System.out.println("因为在寻找异常表的过程中，会将没有捕捉该异常的栈帧弹出。如果在最后也没有找到的话，那么" +
                "打印异常信息，JVM停止；如果在某一帧找到了，便又有了一定的栈空间来支撑继续执行，栈帧释放后，发生GC后可以挥手");
    }

    //递归11406次后溢出
    public static void trace1(int n){
        System.out.println(n);
        trace1(n+1);
    }

    //递归9736次后溢出
    public static void trace2(int n,int pending){
        System.out.println(n);
        trace2(n+1,pending+1);
    }
}
