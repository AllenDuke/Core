package com.github.AllenDuke;

import com.github.AllenDuke.dataStructure.QuickList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/7/23
 */
public class QuickListTest {

    public static void main(String[] args) {
        int count=1000000;
        List<Integer> al=new ArrayList<>();
        List<Integer> ql=new QuickList<>();
        long start=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            al.add(i);
        }
        System.out.println(System.currentTimeMillis() - start);
        start=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            ql.add(i);
        }
        System.out.println(System.currentTimeMillis() - start);
//        for(int i=0;i<count;i++){
//            if(!al.get(i).equals(ql.get(i))) throw new RuntimeException("QuickList error");
//        }
    }
}
