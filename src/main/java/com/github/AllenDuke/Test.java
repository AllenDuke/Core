package com.github.AllenDuke;

import java.lang.reflect.Field;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/13
 */
public class Test {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Class<?> cache = Integer.class.getDeclaredClasses()[0];
        Field c = cache.getDeclaredField("cache");
        c.setAccessible(true);
        Integer[] arr = (Integer[]) c.get(cache);
        arr[130] = arr[129];
        arr[131] = arr[129];
        Integer a = 1;
        if (a == (Integer) 1 && a == (Integer) 2 && a == (Integer) 3) {
            System.out.println("success");
        }
    }
}