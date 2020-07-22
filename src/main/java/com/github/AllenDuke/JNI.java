package com.github.AllenDuke;

/**
 * @author 杜科
 * @description 测试JNI
 * @contact AllenDuke@163.com
 * @date 2020/7/18
 */
public class JNI {

    public native String show();

    static{
        System.loadLibrary("cpp");
    }

    public static void main(String[] args) {
        System.out.println(new JNI().show());
    }
}
