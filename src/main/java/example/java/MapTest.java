package example.java;

import java.util.*;

/**
 * @author 杜科
 * @description Map用法简单示例
 * @contact AllenDuke@163.com
 * @date 2020/3/19
 */
public class MapTest {

    public static void main(String[] args) {

        Map<Character, Integer> lruTest = new LruTest(16,0.75f,true);
        String s = "abcdefghijkl";
        for (int i = 0; i < s.length(); i++) {
            lruTest.put(s.charAt(i), i);
        }
        System.out.println("LRU中key为i的的值为： " + lruTest.get('i'));//当使用accessOrder后，每次访问到的元素就放回链尾
        System.out.println("LRU的大小 ：" + lruTest.size());
        System.out.println("LRU ：" + lruTest);

        Map<Student, String> treeMap=new TreeMap((Comparator<Student>) (o1, o2) -> o1.name.compareTo(o2.name));
        treeMap.put(new Student(22,"lisi"),"lisi");
        treeMap.put(new Student(21,"zhangsan"),"zhangsan");
        treeMap.put(new Student(23,"wangwu"),"wangwu");
        System.out.println("TreeMap："+treeMap);
    }

    static class Student {
        int age;
        String name;
        Student(int age,String name){
            this.age=age;
            this.name=name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}