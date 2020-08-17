package com.github.AllenDuke;

import com.github.AllenDuke.dataStructure.SDS;

/**
 * @author 杜科
 * @description 测试SDS可重用的性能
 * @contact AllenDuke@163.com
 * @date 2020/6/17
 */
public class SDSTest {

    private static int count=100000000;

    public static void main(String[] args) {
        long start=System.currentTimeMillis()/1000;
        testStringBuilder();
        testSDS();
    }

    public static void testStringBuilder(){
        StringBuilder builder = new StringBuilder();
        long time = System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
            builder = new StringBuilder(40);
            builder.append("aa");
            builder.append("bb");
            builder.append("cc");
            builder.append("dd");
            builder.append("ee");
            builder.toString();
        }
        System.out.println("StringBuilder new 耗时：" + (System.currentTimeMillis() - time));
        long time1 = System.currentTimeMillis();
        StringBuilder builder1 = new StringBuilder(40);
        for(int i=0;i<10000000;i++){
            builder1.delete(0, builder.length());
            builder1.append("aa");
            builder1.append("bb");
            builder1.append("cc");
            builder1.append("dd");
            builder1.append("ee");
            builder1.toString();
        }
        System.out.println("StringBuilder delete 耗时：" + (System.currentTimeMillis() - time1));
        long time2 = System.currentTimeMillis();
        StringBuilder builder2 = new StringBuilder(40);
        for(int i=0;i<10000000;i++){
            builder2.setLength(0);
            builder2.append("aa");
            builder2.append("bb");
            builder2.append("cc");
            builder2.append("dd");
            builder2.append("ee");
            builder2.toString();
        }
        System.out.println("StringBuilder setLenth=0 耗时：" + (System.currentTimeMillis() - time2));
    }

    public static void testSDS(){
        SDS sds;
        long time = System.currentTimeMillis();
        for(int i=0;i<10000000;i++){
            sds = new SDS();
            sds.append("aa");
            sds.append("bb");
            sds.append("cc");
            sds.append("dd");
            sds.append("ee");
            sds.toString();
        }
        System.out.println("SDS new 耗时：" + (System.currentTimeMillis() - time));
        long time2 = System.currentTimeMillis();
        sds=new SDS();
        for(int i=0;i<10000000;i++){
            sds.clear();
            sds.append("aa");
            sds.append("bb");
            sds.append("cc");
            sds.append("dd");
            sds.append("ee");
            sds.toString();
        }
        System.out.println("SDS clear 耗时：" + (System.currentTimeMillis() - time2));
    }
}
