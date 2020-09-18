package com.github.AllenDuke.math;

/**
 * @author 杜科
 * @description
 * @contact AllenDuke@163.com
 * @date 2020/9/13
 */
public class CombinationTest {

    public static void main(String[] args) {
        System.out.println(combination(7, 3));
    }

    static int combination(int m,int n){
        int min=Math.min(n,m-n);
        int max=Math.max(n,m-n);
        int low=1;
        for(int i=1;i<=min;i++) low*=i;
        int up=1;
        for (int i=max+1;i<=m;i++) up*=i;
        return up/low;
    }
}
