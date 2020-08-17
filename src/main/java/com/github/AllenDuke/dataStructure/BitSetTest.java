package com.github.AllenDuke.dataStructure;

import java.util.BitSet;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/7/31
 */
public class BitSetTest {

    public static void main(String[] args) {
        BitSet bitSet = new BitSet(300);
        bitSet.set(299);
        bitSet.set(15);
        System.out.println(bitSet.get(299));
        System.out.println(bitSet.get(14));
        BitMap bitMap = new BitMap();
        bitMap.set(35);
        bitMap.set(15);
        System.out.println(bitMap.get(35) + " " + bitMap.get(14) + " " + bitMap.get(15) + " " + bitMap.get(16));

    }
}
