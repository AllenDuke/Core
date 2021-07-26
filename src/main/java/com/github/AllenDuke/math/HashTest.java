package com.github.AllenDuke.math;

import com.github.AllenDuke.User;

import java.util.Random;

/**
 * @author 杜科
 * @description 测试hash冲突时，优化的线性探测——2倍法
 * 在java 10万级测试种显示，此种算法比+1要稳定。因为涉及cpu，应该量级低更为有利一些。
 * @contact AllenDuke@163.com
 * @date 2021/1/19
 */
public class HashTest {

    private static final int dataCount = 1024 * 128;

    private static final int dataCount2 = 1024 * 128 * 2;

    private static final int testNum = 100_000;

    private static final User[] data = new User[dataCount];

    private static final User[] table1 = new User[dataCount];

    private static final User[] table1_2 = new User[dataCount2];

    private static final User[] table2 = new User[dataCount2];

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        for (int i = 0; i < dataCount; i++) {
            data[i] = new User();
        }

//        test1();
        test1_2();
        test2();
    }

    public static void test1() {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < dataCount; i++) {
            put1(table1, data[i]);
        }

        for (int i = 0; i < testNum; i++) {
            int index = (RANDOM.nextInt() & (dataCount - 1));
            get1(table1, data[index]);
        }

        System.out.println("test1 cost:" + (System.currentTimeMillis() - begin));
    }

    public static void test1_2() {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < dataCount; i++) {
            put1_2(table1_2, data[i]);
        }

        for (int i = 0; i < testNum; i++) {
            int index = (RANDOM.nextInt() & (dataCount - 1));
            get1_2(table1_2, data[index]);
        }

        System.out.println("test1_2 cost:" + (System.currentTimeMillis() - begin));
    }

    public static void test2() {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < dataCount; i++) {
            put2(table2, data[i]);
        }

        for (int i = 0; i < testNum; i++) {
            int index = (RANDOM.nextInt() & (dataCount - 1));
            get2(table2, data[index]);
        }

        System.out.println("test2 cost:" + (System.currentTimeMillis() - begin));
    }

    private static void put1(User[] table, User entry) {
        int hash = entry.hashCode() ^ (entry.hashCode() >> 16);
        int index = hash & (dataCount - 1);
        for (int i = 0; ; i++) {
            if (table[index] == null) {
                table[index] = entry;
                return;
            }
            index++;
            index &= (dataCount - 1);
        }
    }

    private static void get1(User[] table, User key) {
        int hash = key.hashCode() ^ (key.hashCode() >> 16);
        int index = hash & (dataCount - 1);
        for (int i = 0; i < table.length; i++) {
            if (table[index].hashCode() == key.hashCode()) {
                if (table[index].equals(key)) return;
            }
            index++;
            index &= (dataCount - 1);
        }
    }

    private static void put1_2(User[] table, User entry) {
        int hash = entry.hashCode() ^ (entry.hashCode() >> 16);
        int index = hash & (dataCount2 - 1);
        for (int i = 0; ; i++) {
            if (table[index] == null) {
                table[index] = entry;
                return;
            }
            index++;
            index &= (dataCount2 - 1);
        }
    }

    private static void get1_2(User[] table, User key) {
        int hash = key.hashCode() ^ (key.hashCode() >> 16);
        int index = hash & (dataCount2 - 1);
        for (int i = 0; i < table.length; i++) {
            if (table[index] == null) return;
            if (table[index].hashCode() == key.hashCode()) {
                if (table[index].equals(key)) return;
            }
            index++;
            index &= (dataCount2 - 1);
        }
    }

    private static void put2(User[] table, User entry) {
        int hash = entry.hashCode() ^ (entry.hashCode() >> 16);
        int index = hash & (dataCount2 - 1);
        for (int i = 0; ; i++) {
            if (table[index] == null) {
                table[index] = entry;
                return;
            }
            index += dataCount + (i & 1);
            index &= (dataCount2 - 1);

//            index+=i<<1;
//            index &= (dataCount2 - 1);
        }
    }

    private static void get2(User[] table, User key) {
        int hash = key.hashCode() ^ (key.hashCode() >> 16);
        int index = hash & (dataCount2 - 1);
        for (int i = 0; i < table.length; i++) {
            if (table[index] == null) return;
            if (table[index].hashCode() == key.hashCode()) {
                if (table[index].equals(key)) return;
            }
            index += dataCount + (i & 1); // todo 不利于cpu缓存
            index &= (dataCount2 - 1);
//            index+=i<<1;
//            index &= (dataCount2 - 1);
        }
    }
}
