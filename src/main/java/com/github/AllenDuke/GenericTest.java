package com.github.AllenDuke;

import java.util.ArrayList;

/**
 * @author 杜科
 * @description 泛型测试
 * @contact AllenDuke@163.com
 * @date 2020/8/31
 */
public class GenericTest {

    public static void main(String[] args) {
        ArrayList<?> list = new ArrayList<>(); /* 在使用时才知道类型， 那个时候编译器会要求你把？替换成对应的类型*/
        //list.add(1);
    }
}
