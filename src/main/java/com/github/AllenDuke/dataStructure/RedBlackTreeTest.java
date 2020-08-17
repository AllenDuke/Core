package com.github.AllenDuke.dataStructure;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/8/16
 */
public class RedBlackTreeTest {

    public static void main(String[] args) {
        RedBlackTree<Integer, String> tree = new RedBlackTree<>();
        tree.put(2,"duke");
        tree.put(4,"lisi");
        tree.put(3,"zhangsan");
        tree.put(1,"allen");
        tree.show();
    }
}
